package com.itransition.courseproject.dto.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionUserResponse {
    private long id;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
}
