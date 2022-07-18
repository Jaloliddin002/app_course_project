package com.itransition.courseproject.service.collection;

import com.itransition.courseproject.dto.request.collection.CollectionRequest;
import com.itransition.courseproject.dto.request.field.FieldRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.collection.CollectionResponse;
import com.itransition.courseproject.dto.response.field.FieldResponse;
import com.itransition.courseproject.exception.auth.AuthorizationRequiredException;
import com.itransition.courseproject.exception.resource.FileProcessingException;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.exception.user.UserNotFoundException;
import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.collection.Field;
import com.itransition.courseproject.model.entity.collection.Topic;
import com.itransition.courseproject.model.entity.item.FieldValue;
import com.itransition.courseproject.model.entity.user.User;
import com.itransition.courseproject.model.enums.Status;
import com.itransition.courseproject.repository.collection.*;
import com.itransition.courseproject.repository.item.CommentRepository;
import com.itransition.courseproject.repository.item.FieldValueRepository;
import com.itransition.courseproject.repository.item.ItemRepository;
import com.itransition.courseproject.repository.user.UserRepository;
import com.itransition.courseproject.service.CRUDService;
import com.itransition.courseproject.util.AuthenticationUtil;
import com.itransition.courseproject.util.CSVUtil;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.itransition.courseproject.util.constant.ResourceConstants.*;


@Service
@RequiredArgsConstructor
public class CollectionService implements CRUDService<Long, CollectionRequest> {

    private final CollectionRepository collectionRepository;
    private final TopicRepository topicRepository;
    private final FieldRepository fieldRepository;
    private final FieldValueRepository fieldValueRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse create(CollectionRequest collectionRequest) {
        User user = userRepository.findById(collectionRequest.getUserId()).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(collectionRequest.getUserId()));
        });
        Topic topic = topicRepository.findById(collectionRequest.getTopicId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(TOPIC_ENG, TOPIC_RUS, String.valueOf(collectionRequest.getTopicId()));
        });
        Collection collection = collectionRepository.save(
                new Collection(
                        collectionRequest.getName(),
                        collectionRequest.getDescription(),
                        collectionRequest.getImageUrl(),
                        topic,
                        user
                )
        );
        return APIResponse.success(getCollectionResponse(collection,
                collectionRequest.getCustomFields()));
    }

    @Override
    public APIResponse get(Long id) {
        final Collection collection = collectionRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(id));
        });
        final FieldResponse[] fieldList = modelMapper.map(fieldRepository.findAllByCollection(collection), FieldResponse[].class);
        final CollectionResponse response = modelMapper.map(collection, CollectionResponse.class);

        response.setFields(fieldList);
        return APIResponse.success(response);
    }

    //    public APIResponse  (int page, int size, String order, String[] categories) {
//        categories = (categories == null || categories.length == 0) ? new String[]{DEFAULT_SORT_FIELD} : categories;
//        Page<Collection> list = collectionRepository
//                .findAll(PageRequest.of(page, size, Sort.Direction.valueOf(order), categories));
//        return APIResponse.success(getCollectionResponseList(list.getContent()));
//    }
    @Transactional
    @Override
    public APIResponse update(Long id, CollectionRequest collectionRequest) {
        if (authorizeCollectionOwner(id)) {
            throw new AuthorizationRequiredException();
        }
        User user = userRepository.findById(collectionRequest.getUserId()).orElseThrow(() -> {
            throw new UserNotFoundException(String.valueOf(collectionRequest.getUserId()));
        });
        Topic topic = topicRepository.findById(collectionRequest.getTopicId()).orElseThrow(() -> {
            throw new ResourceNotFoundException(TOPIC_ENG, TOPIC_RUS, String.valueOf(collectionRequest.getTopicId()));
        });
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(id));
        });
        collection.setImageUrl(collectionRequest.getImageUrl());
        collection.setDescription(collection.getDescription());
        collection.setName(collectionRequest.getName());
        collection.setTopic(topic);
        collection.setUser(user);
        collection = collectionRepository.save(collection);
        return APIResponse.success(getCollectionResponse(collection,
                collectionRequest.getCustomFields()));
    }

    @Transactional
    @Override
    public APIResponse delete(Long id) {
        if (authorizeCollectionOwner(id)) {
            throw new AuthorizationRequiredException();
        }
        fieldValueRepository.deleteFieldValuesByField_CollectionId(id);
        fieldRepository.deleteFieldsByCollectionId(id);
        commentRepository.deleteCommentsByItem_Collection_Id(id);
        itemRepository.deleteAllByCollectionId(id);
        collectionRepository.deleteById(id);
        return APIResponse.success(true);
    }

    @Override
    public APIResponse getAll() {
        return APIResponse.success(getCollectionResponseList(collectionRepository.findAll()));
    }

    public APIResponse getTopCollections() {
        final List<Long> collectionIds = itemRepository.findTopCollectionIds();
        return APIResponse.success(getCollectionResponseList(collectionRepository.findAllById(collectionIds)));
    }

    public APIResponse getUserCollections(Long userId) {
        final List<Collection> collectionList = AuthenticationUtil.isAdmin() ?
                collectionRepository.findAll() : collectionRepository.findAllByUser_Id(userId);
        return APIResponse.success(getCollectionResponseList(collectionList));
    }

    private CollectionResponse getCollectionResponse(Collection collection, FieldRequest[] fieldRequests) {
        final CollectionResponse response = modelMapper.map(collection, CollectionResponse.class);
        response.setFields(saveCollectionFields(collection, fieldRequests));
        return response;
    }

    private FieldResponse[] saveCollectionFields(Collection collection, FieldRequest[] fieldRequests) {
        final List<Field> fields = List.of(modelMapper.map(fieldRequests, Field[].class));
        fields.forEach(field -> field.setCollection(collection));
        return modelMapper.map(fieldRepository.saveAll(fields), FieldResponse[].class);
    }

    private List<CollectionResponse> getCollectionResponseList(List<Collection> collectionList) {
        return collectionList
                .stream()
                .map(collection -> modelMapper.map(collection, CollectionResponse.class)).collect(Collectors.toList());
    }

    public void loadCSVFile(HttpServletResponse response, long collectionId, String lang) {
        final Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> {
            throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(collectionId));
        });
        try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
            List<Field> fieldList = fieldRepository.findAllByCollection_IdOrderByIdAsc(collectionId);
            List<FieldValue> fieldValueList = fieldValueRepository.findAllByItem_CollectionIdOrderByItem_IdAscField_IdAsc(collectionId);
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + collection.getName() + ".csv");
            List<String[]> csvContent = CSVUtil.ofCollection(lang, collection, fieldList, fieldValueList);
            for (String[] strings : csvContent) {
                csvWriter.writeNext(strings);
            }
        } catch (IOException e) {
            throw new FileProcessingException("csv file creation error", "ошибка создания файла csv");
        }

    }

    private boolean authorizeCollectionOwner(Long collectionId) {
        if (AuthenticationUtil.isAdmin()) {
            return false;
        }
        final String email = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return !collectionRepository.existsByIdAndUserEmailAndUserStatus(collectionId, email, Status.ACTIVE);
    }

}
