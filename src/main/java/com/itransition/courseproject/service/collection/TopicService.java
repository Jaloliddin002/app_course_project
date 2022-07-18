package com.itransition.courseproject.service.collection;

import com.itransition.courseproject.dto.request.collection.TopicRequest;
import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.dto.response.collection.TopicResponse;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.model.entity.collection.Topic;
import com.itransition.courseproject.repository.collection.TopicRepository;
import com.itransition.courseproject.service.CRUDService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itransition.courseproject.util.constant.ResourceConstants.TOPIC_ENG;
import static com.itransition.courseproject.util.constant.ResourceConstants.TOPIC_RUS;

@Service
public class TopicService implements CRUDService<Long, TopicRequest> {

    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    public TopicService(ModelMapper modelMapper, TopicRepository topicRepository) {
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @Override
    public APIResponse create(TopicRequest topicRequest) {
        //// TODO: 21.06.2022 topic name should be unique
        return APIResponse.success(topicRepository
                .save(modelMapper.map(topicRequest, Topic.class)));
    }

    @Override
    public APIResponse get(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(TOPIC_ENG, TOPIC_RUS, id);
        });
        return APIResponse.success(topic);
    }

    @Override
    public APIResponse update(Long id, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(TOPIC_ENG, TOPIC_RUS, id);
        });
        modelMapper.map(topicRequest, topic);
        return APIResponse.success(topicRepository.save(topic));
    }

    @Override
    public APIResponse delete(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(TOPIC_ENG, TOPIC_RUS, id);
        });
        topicRepository.delete(topic);
        return APIResponse.success(true);
    }

    public List<TopicResponse> getList() {
        return List.of(modelMapper.map(topicRepository.findAll(), TopicResponse[].class));
    }
    @Override
    public APIResponse getAll() {
        return APIResponse.success(
                List.of(modelMapper.map(topicRepository.findAll(), TopicResponse[].class)));
    }
}
