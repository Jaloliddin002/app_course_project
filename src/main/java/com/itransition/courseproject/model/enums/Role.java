package com.itransition.courseproject.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER(1, "ROLE_USER"),

    ADMIN(2,"ROLE_ADMIN");

    public final int flag;

    public final String name;

    Role(int flag, String role) {
        this.flag = flag;
        this.name = role;
    }
}
