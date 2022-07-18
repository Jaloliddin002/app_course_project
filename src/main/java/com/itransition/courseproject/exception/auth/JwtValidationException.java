package com.itransition.courseproject.exception.auth;
public class JwtValidationException extends RuntimeException{
    public JwtValidationException(String message) {
        super(message);
    }
}
