package com.itransition.courseproject.controller.collection;

import com.itransition.courseproject.controller.CRUDController;
import com.itransition.courseproject.dto.request.collection.TopicRequest;
import com.itransition.courseproject.service.collection.TopicService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itransition.courseproject.controller.ControllerUtils.TOPIC_URI;

@RestController
@RequestMapping(TOPIC_URI)
public class TopicController extends CRUDController<TopicService, Long, TopicRequest> {
    protected TopicController(TopicService service) {
        super(service);
    }
}
