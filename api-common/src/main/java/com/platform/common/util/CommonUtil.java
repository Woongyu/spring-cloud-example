package com.platform.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platform.common.dto.enums.Title;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
        int numWords = getRandomInt(2, 4);
        for (int i = 0; i < numWords; i++) {
            int index = CommonRandom.nextInt(titles.length);
            String word = titles[index].getValue();
            sb.append(word);
            if (i < numWords - 1) {
                sb.append(" ");
            }
        }

        return Arrays.stream(sb.toString().split("\\s"))
            .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
            .collect(Collectors.joining(" "));
    }

    private static int getRandomInt(int min, int max) {
        return CommonRandom.nextInt((max - min) + 1) + min;
    }

    public static String generateContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            int index = CommonRandom.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static <T> List<T> pageOf(List<T> items, String page, Integer limit) {
        int nextPageNum = 1;
        if (StringUtils.isNotBlank(page)) {
            nextPageNum = Math.max(nextPageNum, Integer.parseInt(page));
        }

        int totalItemCount = items.size();

        int itemsPerPage = limit;
        //int totalPages = (int) Math.ceil((double) totalItemCount / itemsPerPage);

        int startIndex = (nextPageNum - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItemCount);

        List<T> newItems = new ArrayList<>();
        for (int i = startIndex; i < endIndex; i++) {
            newItems.add(items.get(i));
        }

        return newItems;
    }

    private CommonUtil() {
        throw new IllegalStateException("Utility class");
    }
}
