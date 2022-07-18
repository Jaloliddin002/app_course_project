package com.itransition.courseproject.controller;

public abstract class ControllerUtils {
    private static final String HOST = "";
    private static final String API = "/api";
    private static final String VERSION = "/v1";
    private static final String AUTH = "/auth";
//    private static final String AUTH2 = "/auth2";
    private static final String HOME = "/home";
    private static final String ALL = "/**";
    public static final String BASE_URI = HOST + API + VERSION;
    public static final String COMMENT_URI = BASE_URI + "/comment";
    public static final String COLLECTION_URI = BASE_URI + "/collection";
    public static final String FILE_URI = BASE_URI + "/file";
    public static final String FIELD_URI = BASE_URI + "/field";
    public static final String ITEM_URI = BASE_URI + "/item";
    public static final String TOPIC_URI = BASE_URI + "/topic";
    public static final String USER_URI = BASE_URI + "/user";
    public static final String TAG_URI = BASE_URI + "/tag";
    public static final String SEARCH_URI = BASE_URI+"/search";
    public static final String[] OPEN_PATH = {
            USER_URI,
            COMMENT_URI,
            BASE_URI + HOME,
            SEARCH_URI+ALL,
            TAG_URI + ALL,
            ITEM_URI + ALL,
            FIELD_URI + ALL,
            COLLECTION_URI + ALL,
            BASE_URI + AUTH + ALL,
            "/swagger-ui/**",
            "/api-docs/**",
            "/app/**",
            "/socket/**"
    };
    public static final String[] OPEN_FILES = {
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };
}
