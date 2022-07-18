package com.itransition.courseproject.dto.response.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseByTag implements Response {
    @JsonProperty("item_id")
    private long itemId;
    @JsonProperty("field_key")
    private String fieldKey;
    @JsonProperty("field_value")
    private String fieldValue;
}
