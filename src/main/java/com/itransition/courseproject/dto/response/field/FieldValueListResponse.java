package com.itransition.courseproject.dto.response.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itransition.courseproject.dto.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class FieldValueListResponse implements Response {
    private List<FieldResponse> types;
    private Map<Long, List<FieldValueResponse>> values;
    @JsonProperty("owner_id")
    private long ownerId;
}
