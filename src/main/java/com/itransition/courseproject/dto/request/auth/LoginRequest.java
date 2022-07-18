package com.itransition.courseproject.dto.request.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itransition.courseproject.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Request {
    @Email
    private String email;
    @Size(min = 4, max = 18)
    private String password;
}
