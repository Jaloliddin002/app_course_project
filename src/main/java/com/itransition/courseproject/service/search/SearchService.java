package com.itransition.courseproject.service.search;


import com.itransition.courseproject.dto.response.APIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
@RequiredArgsConstructor
public class SearchService {
    @PersistenceContext
    private EntityManager entityManager;

    public APIResponse search(String text) {
        text = text.toLowerCase() + ":*";
        Query query = entityManager.createNativeQuery("select * from full_text_search(:text)");
        query.setParameter("text", text);
        return APIResponse.success(query.getResultList());
    }

}
