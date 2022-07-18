package com.itransition.courseproject.exception;

public abstract class ApiException extends RuntimeException {
    public ApiException(String messageENG, String messageRUS) {
        super(messageENG +"\n"+messageRUS);
    }
}
