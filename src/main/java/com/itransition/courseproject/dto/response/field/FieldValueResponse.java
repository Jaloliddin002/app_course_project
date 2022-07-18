package com.itransition.courseproject.dto.response.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldValueResponse implements Response {
    private String value;
    @JsonProperty("item_id")
    private long itemId;
    @JsonProperty("field_id")
    private long fieldId;
}