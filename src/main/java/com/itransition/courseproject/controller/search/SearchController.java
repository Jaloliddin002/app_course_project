package com.itransition.courseproject.controller.search;


import com.itransition.courseproject.service.search.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.itransition.courseproject.controller.ControllerUtils.SEARCH_URI;

@RequestMapping(SEARCH_URI)
@RestController
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping()
    public ResponseEntity<?> search(
            @RequestParam(name = "text") String text
    ){
        return ResponseEntity.ok(searchService.search(text));
    }

}
