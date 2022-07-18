package com.itransition.courseproject.dto.request.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itransition.courseproject.dto.request.Request;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldRequestForCollection implements Request {
    private int id;
    private String name;
    private int type;
}
