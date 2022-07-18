package com.itransition.courseproject.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CommentAuthor {
    private long id;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
}
