package com.itransition.courseproject.controller.collection;

import com.itransition.courseproject.controller.CRUDController;
import com.itransition.courseproject.dto.request.field.FieldRequest;
import com.itransition.courseproject.service.collection.FieldService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itransition.courseproject.controller.ControllerUtils.FIELD_URI;

@RestController
@RequestMapping(FIELD_URI)
public class FieldController extends CRUDController<FieldService, Long, FieldRequest> {

    protected FieldController(FieldService service) {
        super(service);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getFieldsByCollection(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(service.getAllFieldsByCollectionId(id));
    }
}
