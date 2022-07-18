package com.itransition.courseproject.dto.request.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.request.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopicRequest implements Request {

    @JsonProperty("name_eng")
    @NotBlank
    private String nameENG;

    @JsonProperty("name_rus")
    @NotBlank
    private String nameRUS;
}
