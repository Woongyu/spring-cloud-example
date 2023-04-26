package com.platform.member.util;

import java.security.SecureRandom;
import java.util.Random;

public final class CommonUtil {
    public static final Random CommonRandom = new SecureRandom();

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

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
