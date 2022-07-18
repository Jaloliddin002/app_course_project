package com.itransition.courseproject.dto.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import com.itransition.courseproject.dto.response.comment.CommentResponse;
import com.itransition.courseproject.dto.response.field.FieldValueListResponse;
import com.itransition.courseproject.dto.response.tag.TagResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ItemResponse implements Response {
    private long id;
    private String name;
    private long likes;
    private List<TagResponse> tags;
    private FieldValueListResponse fields;
    private List<CommentResponse> comments;
    @JsonProperty("is_liked")
    private boolean isLiked;
}
