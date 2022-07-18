package com.itransition.courseproject.exception.auth;

import com.itransition.courseproject.exception.ApiException;

public class AuthorizationRequiredException extends ApiException {
    private static final String DEFAULT_MESSAGE_ENG = "Authorization required to do specific action";
    private static final String DEFAULT_MESSAGE_RUS = "Для выполнения определенных действий требуется авторизация";

    public AuthorizationRequiredException() {
        super(DEFAULT_MESSAGE_ENG,DEFAULT_MESSAGE_RUS);
    }
}
