package com.platform.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class CommonUtil {
    public static final Random CommonRandom = new SecureRandom();
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final TypeReference<Map<String, Object>> JsonTypeRef =
        new TypeReference<>() {
        };

    public static String generateUserName() {
        final String[] firstName = {"효리", "민수", "길동", "효복", "유지", "예진", "바다", "하늘"};
        final String[] lastName = {"김", "이", "박", "정", "장", "송", "한", "신"};
        return firstName[CommonRandom.nextInt(firstName.length)] + lastName[CommonRandom.nextInt(lastName.length)];
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

    public static <T> List<T> pageOf(List<T> items, String nextPage, Integer limit) {
        int nextPageNum = 1;
        if (StringUtils.isNotBlank(nextPage)) {
            nextPageNum = Math.max(nextPageNum, Integer.parseInt(nextPage));
        }

        int totalItemCount = items.size();

        int itemsPerPage = limit;
        int totalPages = (int) Math.ceil((double) totalItemCount / itemsPerPage);

        int startIndex = (nextPageNum - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItemCount);

        List<T> localItems = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            localItems.add(items.get(i));
        }

        return localItems;
    }

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
