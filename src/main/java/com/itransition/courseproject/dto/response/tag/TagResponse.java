package com.itransition.courseproject.dto.response.tag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponse implements Response {

    private long id;
    @JsonProperty("name_eng")
    private String nameENG;
    @JsonProperty("name_rus")
    private String nameRUS;
}
