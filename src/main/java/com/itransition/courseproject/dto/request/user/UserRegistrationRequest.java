package com.itransition.courseproject.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itransition.courseproject.dto.request.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegistrationRequest implements Request {
    private String name;
    private String email;
    private String password;
}
