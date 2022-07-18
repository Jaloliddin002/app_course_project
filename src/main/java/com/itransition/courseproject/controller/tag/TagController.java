package com.itransition.courseproject.controller.tag;

import com.itransition.courseproject.controller.CRUDController;
import com.itransition.courseproject.dto.request.tag.TagRequest;
import com.itransition.courseproject.service.tag.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.itransition.courseproject.controller.ControllerUtils.TAG_URI;

@RestController
@RequestMapping(TAG_URI)
public class TagController extends CRUDController<TagService, Long, TagRequest> {

    private final TagService tagService;

    protected TagController(TagService service) {
        super(service);
        this.tagService = service;
    }

}
