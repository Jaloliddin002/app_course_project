package com.itransition.courseproject.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
@Getter
@Setter
@Accessors(chain = true)
public class CommentResponse {
    private long id;
    @JsonProperty("item_id")
    private long itemId;
    private String text;
    @JsonProperty("creation_date")
    private Date creationDate;
    @JsonProperty("user")
    private CommentAuthor commentAuthor;
}
