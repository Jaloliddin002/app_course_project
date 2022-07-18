package com.itransition.courseproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.OK;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class APIResponse implements Response{

    private int statusCode;
    private String message;
    private Object data;
    private String error;
    private String path;
    private Date timestamp;

    private APIResponse() {
    }

    public static APIResponse success(Object data){
        APIResponse response = new APIResponse();
        response.message = OK.name();
        response.statusCode = OK.value();
        response.data = data;
        return response;
    }

    public static APIResponse error(WebRequest webRequest, String errorMessage, HttpStatus status){
        APIResponse response = new APIResponse();
        response.message = errorMessage;
        response.statusCode = status.value();
        response.error = status.name();
        response.timestamp = new Date();
        response.path = webRequest == null ? "/" : webRequest.getContextPath();
        return response;
    }
}
