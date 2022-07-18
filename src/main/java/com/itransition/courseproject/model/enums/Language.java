package com.itransition.courseproject.model.enums;

public enum Language {
    ENG("ENG", "АНГЛ"),
    RUS("RUS", "РУС");

    public final String languageENG;
    public final String languageRUS;

    Language(String languageENG, String languageRUS) {
        this.languageENG = languageENG;
        this.languageRUS = languageRUS;
    }
}
