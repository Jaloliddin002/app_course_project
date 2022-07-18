package com.itransition.courseproject.dto.request.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldValueRequest implements Request {
    private String value;
    @JsonProperty("item_id")
    private long itemId;
    @JsonProperty("field_id")
    private long fieldId;
}
