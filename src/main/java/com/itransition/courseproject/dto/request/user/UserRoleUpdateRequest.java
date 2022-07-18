package com.itransition.courseproject.dto.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleUpdateRequest {
    @JsonProperty("set_as_admin")
    private boolean setAsAdmin;

    private List<Long> content;
}
