package com.platform.common.util;

import com.fasterxml.jackson.core.type.TypeReference;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public final class CommonUtil {
    public static final Random CommonRandom = new SecureRandom();
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final TypeReference<Map<String, Object>> JsonTypeRef =
        new TypeReference<Map<String, Object>>() {
        };

    public static String generateUserName() {
        final String[] firstName = {"효리", "민수", "길동", "효복", "유지", "예진", "바다", "하늘"};
        final String[] lastName = {"김", "이", "박", "정", "장", "송", "한", "신"};

        StringBuilder sb = new StringBuilder();
        sb.append(firstName[CommonRandom.nextInt(firstName.length)])
            .append(lastName[CommonRandom.nextInt(lastName.length)]);

        return sb.toString();
    }

    public static String generateCountry() {
        final String[] countries = {"USA", "Canada", "France", "Germany", "Japan", "South Korea", "Brazil", "Australia", "India", "China"};
        return countries[CommonRandom.nextInt(countries.length)];
    }

    public static String generateTitle() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int index = CommonRandom.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }

        return sb.toString();
    }

    public static String generateContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            int index = CommonRandom.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
