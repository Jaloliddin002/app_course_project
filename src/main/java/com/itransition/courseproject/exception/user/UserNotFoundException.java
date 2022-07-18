package com.itransition.courseproject.exception.user;

import com.itransition.courseproject.exception.ApiException;


public class UserNotFoundException extends ApiException {

    private static final String DEFAULT_MESSAGE_ENG ="User not found with ";

    private static final String DEFAULT_MESSAGE_RUS ="Пользователь не найден с ";

    public UserNotFoundException(Object parameter) {
        super(DEFAULT_MESSAGE_ENG + parameter, DEFAULT_MESSAGE_RUS+parameter);
    }
}
