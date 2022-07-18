
package com.itransition.courseproject.service;

import com.itransition.courseproject.dto.request.Request;
import com.itransition.courseproject.dto.response.APIResponse;


public interface CRUDService<
        ID,
        C extends Request> {

    APIResponse create(C c);
    APIResponse get(ID id);
    APIResponse update(ID id, C u);
    APIResponse delete(ID id);
    APIResponse getAll();
}
