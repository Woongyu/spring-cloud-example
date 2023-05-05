package com.platform.common.dto.enums;

import com.platform.common.util.CommonUtil;
import lombok.Getter;

@Getter
public enum Country {
    USA("United States of America"),
    CANADA("Canada"),
    FRANCE("France"),
    GERMANY("Germany"),
    JAPAN("Japan"),
    SOUTH_KOREA("South Korea"),
    BRAZIL("Brazil"),
    AUSTRALIA("Australia"),
    INDIA("India"),
    CHINA("China");

    private final String name;

    Country(String name) {
        this.name = name;
    }

    public static Country getRandom() {
        return values()[CommonUtil.CommonRandom.nextInt(values().length)];
    }
}
