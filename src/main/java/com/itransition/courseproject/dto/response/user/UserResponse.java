package com.itransition.courseproject.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse{
    private long id;
    private String name;
    private String email;
    private String status;
    private String language;
    private List<String> role;
    @JsonProperty("image_url")
    private String imageUrl;
}
