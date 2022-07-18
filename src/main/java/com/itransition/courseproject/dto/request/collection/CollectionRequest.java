package com.itransition.courseproject.dto.request.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.request.Request;
import com.itransition.courseproject.dto.request.field.FieldRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRequest implements Request {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @JsonProperty("user_id")
    private long userId;
    @JsonProperty("topic_id")
    private long topicId;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("custom_fields")
    private FieldRequest[] customFields;
}
