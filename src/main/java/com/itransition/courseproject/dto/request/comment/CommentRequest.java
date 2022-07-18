package com.itransition.courseproject.dto.request.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.request.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequest implements Request {
    @NotNull
    @JsonProperty("item_id")
    private Long itemId;

    @NotNull
    @JsonProperty("user_id")
    private Long userId;

    @NotBlank
    private String text;
}
