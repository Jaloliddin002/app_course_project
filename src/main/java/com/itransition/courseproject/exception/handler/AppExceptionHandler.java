package com.itransition.courseproject.exception.handler;

import com.itransition.courseproject.dto.response.APIResponse;
import com.itransition.courseproject.exception.auth.JwtValidationException;
import com.itransition.courseproject.exception.resource.ResourceNotFoundException;
import com.itransition.courseproject.exception.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<?> handlePersistenceException(PersistenceException ex, WebRequest webRequest){
        return ResponseEntity
                .status(CONFLICT)
                .body(APIResponse.error(webRequest, ex.getMessage(), CONFLICT));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleCustomUserException(UserNotFoundException ex, WebRequest webRequest){
        return ResponseEntity
                .status(NOT_FOUND)
                .body(APIResponse.error(webRequest, ex.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity<?> handleJwtValidationException(JwtValidationException ex, WebRequest webRequest){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(APIResponse.error(webRequest, ex.getMessage(), UNAUTHORIZED));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        return ResponseEntity
                .status(CONFLICT)
                .body(APIResponse.error(webRequest, ex.getFieldError().getDefaultMessage(), CONFLICT));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        return ResponseEntity
                .status(NOT_FOUND)
                .body(APIResponse.error(webRequest, ex.getMessage(), NOT_FOUND));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest webRequest) {
        return ResponseEntity
                .status(EXPECTATION_FAILED)
                .body(APIResponse.error(webRequest, ex.getMessage(), EXPECTATION_FAILED));
    }

}
