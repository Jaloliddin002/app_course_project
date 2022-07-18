package com.itransition.courseproject.dto.request.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.request.Request;
import com.itransition.courseproject.dto.request.field.FieldValueRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemRequest implements Request {
    @JsonProperty("collection_id")
    private long collectionId;
    @JsonProperty("item_name")
    private String name;
    @JsonProperty("tag_ids")
    private List<Long> tagIdList;
    @JsonProperty("field_values")
    private List<FieldValueRequest> fieldValues;
}
