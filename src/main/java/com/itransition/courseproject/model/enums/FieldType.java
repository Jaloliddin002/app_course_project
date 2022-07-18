package com.itransition.courseproject.model.enums;

import lombok.Getter;


@Getter
public enum FieldType {
    INTEGER(1),
    TEXT(2),
    CHAR(3),
    BOOLEAN(4),
    DATE(5),
    FLOAT(6);

    public final int value;

    FieldType(int value){
        this.value = value;
    }
}
