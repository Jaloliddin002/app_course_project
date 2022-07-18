package com.itransition.courseproject.service.tag;

import com.itransition.courseproject.dto.request.tag.TagRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.tag.TagResponse;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.model.entity.tag.Tag;
import com.itransition.courseproject.repository.tag.TagRepository;
import com.itransition.courseproject.service.CRUDService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.itransition.courseproject.util.constant.ResourceConstants.TAG_ENG;
import static com.itransition.courseproject.util.constant.ResourceConstants.TAG_RUS;


@Service
@RequiredArgsConstructor
public class TagService implements CRUDService<Long, TagRequest> {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    public APIResponse create(TagRequest tagRequest) {
        return APIResponse.success(tagRepository
                .save(modelMapper.map(tagRequest, Tag.class)));
    }

    @Override
    public APIResponse get(Long id) {
        return APIResponse.success(modelMapper
                .map(tagRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(TAG_ENG,TAG_RUS, id);
                }), TagRequest.class));
    }

    @Override
    public APIResponse update(Long id, TagRequest tagRequest) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(TAG_ENG,TAG_RUS, id);
        });
        modelMapper.map(tag, tagRequest);
        tagRepository.save(tag);

        return APIResponse.success(modelMapper.map(tag, TagResponse.class));
    }

    @Override
    public APIResponse delete(Long id) {
        tagRepository.findById(id).orElseThrow(()->{
            throw new ResourceNotFoundException(TAG_ENG,TAG_RUS,id);
        });
        tagRepository.deleteById(id);
        return APIResponse.success(HttpStatus.OK.value());
    }

    @Override
    public APIResponse getAll(){
        return APIResponse.success(modelMapper
                .map(tagRepository.findAll(), TagResponse[].class));
    }
}
