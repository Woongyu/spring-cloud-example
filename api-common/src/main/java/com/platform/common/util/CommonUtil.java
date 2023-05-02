package com.platform.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platform.common.dto.enums.Title;
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
        Title[] titles = Title.values();
        int numWords = CommonRandom.nextInt(1, 4);
        for (int i = 0; i < numWords; i++) {
            int index = CommonRandom.nextInt(titles.length);
            String word = titles[index].getValue();
            sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            if (i < numWords - 1) {
                sb.append(" ");
            }
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

    public static <T> List<T> pageOf(String page, Integer limit, List<T> sort) {
        int nextPageNum = 1;
        if (StringUtils.isNotBlank(page)) {
            nextPageNum = Math.max(nextPageNum, Integer.parseInt(page));
        }

        int totalItemCount = sort.size();

        int itemsPerPage = limit;
        //int totalPages = (int) Math.ceil((double) totalItemCount / itemsPerPage);

        int startIndex = (nextPageNum - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItemCount);

        List<T> items = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            items.add(sort.get(i));
        }

        return items;
    }

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
