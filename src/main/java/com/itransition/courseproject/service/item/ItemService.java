package com.itransition.courseproject.service.item;

import com.itransition.courseproject.dto.request.field.FieldValueRequest;
import com.itransition.courseproject.dto.request.item.ItemRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.comment.CommentResponse;
import com.itransition.courseproject.dto.response.field.FieldResponse;
import com.itransition.courseproject.dto.response.field.FieldValueListResponse;
import com.itransition.courseproject.dto.response.field.FieldValueResponse;
import com.itransition.courseproject.dto.response.item.ItemResponse;
import com.itransition.courseproject.dto.response.item.ItemResponseByTag;
import com.itransition.courseproject.dto.response.tag.TagResponse;
import com.itransition.courseproject.exception.auth.AuthorizationRequiredException;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.collection.Field;
import com.itransition.courseproject.model.entity.item.Comment;
import com.itransition.courseproject.model.entity.item.FieldValue;
import com.itransition.courseproject.model.entity.item.Item;
import com.itransition.courseproject.model.entity.tag.Tag;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.repository.collection.CollectionRepository;
import com.itransition.courseproject.repository.collection.FieldRepository;
import com.itransition.courseproject.repository.item.CommentRepository;
import com.itransition.courseproject.repository.item.FieldValueRepository;
import com.itransition.courseproject.repository.item.ItemRepository;
import com.itransition.courseproject.repository.tag.TagRepository;
import com.itransition.courseproject.service.CRUDService;
import com.itransition.courseproject.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.itransition.courseproject.util.constant.ResourceConstants.*;

@Service
@RequiredArgsConstructor
public class ItemService implements CRUDService<Long, ItemRequest> {

    private final ModelMapper modelMapper;
    private final ItemRepository itemRepository;
    private final FieldRepository fieldRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final FieldValueRepository fieldValueRepository;
    private final CollectionRepository collectionRepository;

    @Override
    public APIResponse create(ItemRequest itemRequest) {
        final List<Long> tagIdList = itemRequest.getTagIdList();
        final List<Tag> tagList = tagRepository.findAllById(tagIdList);
        final Collection collection = collectionRepository
                .findById(itemRequest.getCollectionId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(itemRequest.getCollectionId()));
                });
        final Item item = itemRepository.save(new Item(itemRequest.getName(), collection, tagList));
        //// TODO: 06.07.2022 return value changed
        fieldValueRepository.saveAll(getFieldValues(item, itemRequest.getFieldValues()));
        return APIResponse.success(true);
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(
                itemRepository.findById(id).orElseThrow(() -> {
                    throw new ResourceNotFoundException(ITEM_ENG, ITEM_RUS, String.valueOf(id));
                })
        );
    }


    public APIResponse getItemResponse(Long id, Long userId) {
        final Item item = itemRepository.findById(id).orElseThrow(() -> {
                    throw new ResourceNotFoundException(ITEM_ENG, ITEM_RUS, String.valueOf(id));
                }
        );

        List<Comment> comments = commentRepository.findAllByItemIdOrderByCreationDateDesc(id);
        return APIResponse.success(new ItemResponse(
                id,
                item.getName(),
                item.getLikes(),
                List.of(modelMapper.map(item.getTags(), TagResponse[].class)),
                getFieldValueListResponse(item.getCollection().getId()),
                List.of(modelMapper.map(comments, CommentResponse[].class)),
                itemRepository.existsByIdAndLikedUsers_Id(id, userId))
        );
    }

    @Transactional
    @Override
    public APIResponse update(Long id, ItemRequest itemRequest) {
        if (authorizeItemOwner(id)) {
            throw new AuthorizationRequiredException();
        }
        final Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ITEM_ENG, ITEM_RUS, String.valueOf(id)));
        final List<Tag> tags = tagRepository.findAllById(itemRequest.getTagIdList());
        final List<Long> filedIds = itemRequest
                .getFieldValues()
                .stream()
                .map(FieldValueRequest::getFieldId)
                .collect(Collectors.toList());
        final List<Field> fields = fieldRepository.findAllById(filedIds);
        final List<FieldValue> fieldValues =
                itemRequest.getFieldValues().stream().map(fieldValue -> new FieldValue(fieldValue.getValue(), item,
                        fields.stream().filter(f -> f.getId() == fieldValue.getFieldId()).findFirst().orElseThrow(
                                () -> new ResourceNotFoundException(FIELD_ENG, FIELD_RUS, String.valueOf(fieldValue.getFieldId())))
                )).collect(Collectors.toList());

        item.setName(itemRequest.getName());
        item.setTags(tags);
        itemRepository.save(item);
        fieldValueRepository.saveAll(fieldValues);

        return getItemResponse(item.getId(), item.getCollection().getUser().getId());
    }

    @Transactional
    @Override
    public APIResponse delete(Long id) {
        if (authorizeItemOwner(id)) {
            throw new AuthorizationRequiredException();
        }
        commentRepository.deleteAllByItemId(id);
        fieldValueRepository.deleteAllByItemId(id);
        itemRepository.deleteById(id);
        return APIResponse.success(HttpStatus.OK);
    }


    @Override
    public APIResponse getAll() {
        return null;
    }

    public APIResponse getItemsByTagId(Long tagId) {
        Map<Long, List<ItemResponseByTag>> collect = fieldValueRepository.findAllByItem_Tags_Id(tagId).stream()
                .map(fieldValue -> {
                    return new ItemResponseByTag(fieldValue.getItem().getId(), fieldValue.getField().getName(), fieldValue.getValue());
                })
                .collect(Collectors.groupingBy(ItemResponseByTag::getItemId));
        return APIResponse.success(collect);
    }

    public APIResponse getLatestItems() {
        return APIResponse.success(itemRepository.findLatest10ItemIds()
                .stream()
                .map(this::getFieldValueListResponse)
                .collect(Collectors.toList()));
    }

    public APIResponse getItemsByCollectionId(Long collectionId) {
        return APIResponse.success(getFieldValueListResponse(collectionId));
    }

    private List<FieldValue> getFieldValues(Item item, List<FieldValueRequest> fieldValues) {
        return fieldValues
                .stream()
                .map((fvr) -> new FieldValue(fvr.getValue(),
                        item,
                        fieldRepository
                                .findById(fvr.getFieldId())
                                .orElseThrow(() -> new ResourceNotFoundException(FIELD_ENG, FIELD_RUS, String.valueOf(fvr.getFieldId())))))
                .collect(Collectors.toList());
    }

    public APIResponse updateItemLike(Long userId, Long itemId, boolean isLiked) {
        if (isLiked) {
            itemRepository.insertItemLike(itemId, userId);
        } else {
            itemRepository.deleteItemLike(itemId, userId);
        }
        return APIResponse.success(true);
    }

    private FieldValueListResponse getFieldValueListResponse(Long collectionId) {
        final FieldValueListResponse response = new FieldValueListResponse();
        response.setOwnerId(collectionRepository.findById(collectionId)
                .orElseThrow(() -> new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, collectionId))
                .getUser().getId());
        response.setTypes(fieldRepository.findAllByCollection_Id(collectionId)
                .stream().map(elm -> modelMapper.map(elm, FieldResponse.class)).collect(Collectors.toList()));
        response.setValues(fieldValueRepository
                .findAllByItem_CollectionId(collectionId)
                .stream()
                .map(item -> new FieldValueResponse(item.getValue(), item.getItem().getId(), item.getField().getId()))
                .collect(Collectors.groupingBy(FieldValueResponse::getItemId)));
        return response;
    }

    private boolean authorizeItemOwner(long itemId) {
        if (AuthenticationUtil.isAdmin()) {
            return false;
        }
        final String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return !itemRepository.existsByIdAndCollection_UserEmailAndCollection_UserStatus(itemId, email, Status.ACTIVE);
    }
}
