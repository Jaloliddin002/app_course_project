package com.itransition.courseproject.exception.resource;

import com.itransition.courseproject.exception.ApiException;

public class ResourceNotFoundException extends ApiException {

    private static final String DEFAULT_MESSAGE_ENG = " not found with ";
    private static final String DEFAULT_MESSAGE_RUS = " не найдено с ";

    public ResourceNotFoundException(String resourceENG, String resourceRUS, Object param) {
        super(resourceENG + DEFAULT_MESSAGE_ENG + param,
                resourceRUS + DEFAULT_MESSAGE_RUS + param);
    }
}
