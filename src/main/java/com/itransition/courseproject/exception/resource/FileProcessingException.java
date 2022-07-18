package com.itransition.courseproject.exception.resource;

import com.itransition.courseproject.exception.ApiException;

public class FileProcessingException extends ApiException {

    public FileProcessingException(String messageENG, String messageRUS) {
        super(messageENG, messageRUS);
    }
}
