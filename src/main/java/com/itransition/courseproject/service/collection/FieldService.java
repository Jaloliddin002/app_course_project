package com.itransition.courseproject.service.collection;

import com.itransition.courseproject.dto.request.field.FieldRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.model.entity.collection.Collection;
import com.itransition.courseproject.model.entity.collection.Field;
import com.itransition.courseproject.repository.collection.CollectionRepository;
import com.itransition.courseproject.repository.collection.FieldRepository;
import com.itransition.courseproject.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.itransition.courseproject.util.constant.ResourceConstants.*;

@Service
@RequiredArgsConstructor
public class FieldService implements CRUDService<Long, FieldRequest> {

    private final CollectionRepository collectionRepository;
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse create(FieldRequest fieldRequest) {
        Collection collection = collectionRepository
                .findById(fieldRequest.getId())
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(fieldRequest.getId()));
                });
        Field field = modelMapper.map(fieldRequest, Field.class);
        field.setCollection(collection);
        fieldRepository.save(field);
        return APIResponse.success(true);
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(
                fieldRepository
                        .findById(id)
                        .orElseThrow(() -> {
                            throw new ResourceNotFoundException(FIELD_ENG, FIELD_RUS, String.valueOf(id));
                        })
        );
    }

    public APIResponse getAllFieldsByCollectionId(Long id) {
        Collection collection = collectionRepository
                .findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(COLLECTION_ENG, COLLECTION_RUS, String.valueOf(id));
                });
        return APIResponse.success(fieldRepository.findAllByCollection(collection));
    }

    @Override
    public APIResponse update(Long id, FieldRequest fieldRequest) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(FIELD_ENG, FIELD_RUS, String.valueOf(id));
                });
        modelMapper.map(fieldRequest, field);
        return APIResponse.success(field);
    }

    @Override
    public APIResponse delete(Long id) {
        fieldRepository.deleteById(id);
        return APIResponse.success(true);
    }

    @Override
    public APIResponse getAll() {
        return null;
    }
}
