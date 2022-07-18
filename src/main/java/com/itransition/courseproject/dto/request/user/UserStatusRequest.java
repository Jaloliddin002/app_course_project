package com.itransition.courseproject.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itransition.courseproject.dto.request.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatusRequest implements Request {
    private String action;
    private List<Long> content;
}
