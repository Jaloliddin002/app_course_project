package com.itransition.courseproject.model.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE", "АКТИВНЫЙ"),
    BLOCKED("BLOCKED", "ЗАБЛОКИРОВАНО"),
        DELETED("DELETED","УДАЛЕНО");

    public final String statusENG;
    public final String statusRUS;

    Status(String statusENG, String statusRUS) {
        this.statusENG = statusENG;
        this.statusRUS = statusRUS;
    }
}
