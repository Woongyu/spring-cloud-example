package com.platform.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platform.common.dto.enums.Content;
import com.platform.common.dto.enums.ContentArticle;
import com.platform.common.dto.enums.Country;
import com.platform.common.dto.enums.Title;
import com.platform.common.dto.enums.UserName;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public final class CommonUtil {
    public static final Random CommonRandom = new SecureRandom();
    public static final TypeReference<Map<String, Object>> JsonTypeRef =
        new TypeReference<>() {
        };

    public static String generateUserName() {
        return UserName.generateFullName();
    }

    public static String generateCountry() {
        return Country.getRandom().getName();
    }

    public static String generateTitle() {
        StringBuilder sb = new StringBuilder();
        Title[] titles = Title.values();
        int numWords = getRandomInt(2, 4);
        for (int i = 0; i < numWords; i++) {
            int index = CommonRandom.nextInt(titles.length);
            String word = titles[index].getValue();
            sb.append(i == 0 ? Character.toUpperCase(word.charAt(0)) + word.substring(1) : word);
            if (i < numWords - 1) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    private int getRandomInt(int min, int max) {
        return CommonRandom.nextInt((max - min) + 1) + min;
    }

    public static String generateContent() {
        StringBuilder sb = new StringBuilder();
        int numWords = getRandomInt(5, 10);
        for (int i = 0; i < numWords; i++) {
            int index = CommonRandom.nextInt(Content.values().length);
            String word = Content.values()[index].getValue();
            sb.append(word);
            if (i < numWords - 1) {
                sb.append(" ");
            }
        }

        sb.append(".");
        sb.insert(0, ContentArticle.values()[CommonRandom.nextInt(ContentArticle.values().length)].getValue().toUpperCase() + " ").append(" ");
        sb.append(ContentArticle.values()[CommonRandom.nextInt(ContentArticle.values().length)].getValue()).append(" ");
        sb.append(Content.values()[CommonRandom.nextInt(Content.values().length)].getValue().toLowerCase()).append(".");

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
