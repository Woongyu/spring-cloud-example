package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum ContentArticle {
    A("a"),
    AN("an"),
    THE("the");

    private final String value;

    ContentArticle(String value) {
        this.value = value;
    }
}
