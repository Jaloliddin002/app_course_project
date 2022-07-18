package com.itransition.courseproject.dto.response.collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponse implements Response {

    private Long id;
    @JsonProperty("name_eng")
    private String nameENG;
    @JsonProperty("name_rus")
    private String nameRUS;

}
