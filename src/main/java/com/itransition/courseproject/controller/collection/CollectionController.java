package com.itransition.courseproject.controller.collection;

import com.itransition.courseproject.controller.CRUDController;
import com.itransition.courseproject.dto.request.collection.CollectionRequest;
import com.itransition.courseproject.service.collection.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.itransition.courseproject.controller.ControllerUtils.COLLECTION_URI;


@RestController
@RequestMapping(COLLECTION_URI)
public class CollectionController extends CRUDController<CollectionService, Long, CollectionRequest> {

    protected CollectionController(CollectionService service) {
        super(service);
    }

//    @GetMapping
//    public ResponseEntity<?> getList(
//        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
//        @RequestParam(name = "size", required = false, defaultValue = "20") Integer size,
//        @RequestParam(name = "order", required = false, defaultValue = "DESC") String order,
//        @RequestParam(name = "categories", required = false) String[] categories
//    ){
//        return ResponseEntity.ok(service.getList(page, size, order, categories));
//    }

    @GetMapping("/latest")
    public ResponseEntity<?> getTopCollections(){
        return ResponseEntity.ok(service.getTopCollections());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserCollections(@PathVariable("userId") Long id){
        return ResponseEntity.ok(service.getUserCollections(id));
    }

    @GetMapping("/csv")
    public void createCSV(
            @RequestParam(name = "collection_id") Long collectionId,
            @RequestParam(name = "lang") String lang,
            HttpServletResponse response
    ) {
        service.loadCSVFile(response, collectionId, lang);
    }
}
