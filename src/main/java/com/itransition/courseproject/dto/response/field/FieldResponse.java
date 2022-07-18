package com.itransition.courseproject.dto.response.field;

import com.itransition.courseproject.dto.response.Response;
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
public class FieldResponse implements Response {
    private long id;
    private int type;
    private String name;
}
