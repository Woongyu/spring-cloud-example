package com.platform.member.dto.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Grade {
    IRON(0, 0, 39, "Iron"),
    BRONZE(1, 40, 79, "Bronze"),
    SILVER(2, 80, 119, "Silver"),
    GOLD(3, 120, 159, "Gold"),
    PLATINUM(4, 160, 199, "Platinum"),
    DIAMOND(5, 200, 239, "Diamond"),
    MASTER(6, 240, 279, "Master"),
    GRANDMASTER(7, 280, Integer.MAX_VALUE, "Grandmaster");

    private final int tier;
    private final int minLikes;
    private final int maxLikes;
    private final String name;

    Grade(int tier, int minLikes, int maxLikes, String name) {
        this.tier = tier;
        this.minLikes = minLikes;
        this.maxLikes = maxLikes;
        this.name = name;
    }

    public static Grade from(final int tier) {
        return Arrays.stream(Grade.values())
            .filter(grade -> grade.tier == tier)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("This code doesn't exist."));
    }

    public static Grade fromLikesCount(int likesCount) {
        for (Grade grade : values()) {
            if (likesCount >= grade.minLikes && likesCount <= grade.maxLikes) {
                return grade;
            }
        }
        return GRANDMASTER;
    }
}
