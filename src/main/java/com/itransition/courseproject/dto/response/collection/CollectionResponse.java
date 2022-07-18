package com.itransition.courseproject.dto.response.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import com.itransition.courseproject.dto.response.field.FieldResponse;
import com.itransition.courseproject.dto.response.user.CollectionUserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CollectionResponse implements Response {

    private long id;
    private String name;
    private TopicResponse topic;
    @JsonProperty("image_url")
    private String imageUrl;
    private String description;
    @JsonProperty("creation_date")
    private Date creationDate;
    @JsonProperty("collection_author")
    private CollectionUserResponse userResponse;
    private FieldResponse[] fields;
}
