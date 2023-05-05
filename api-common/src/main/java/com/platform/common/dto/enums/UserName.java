package com.platform.common.dto.enums;

import com.platform.common.util.CommonUtil;
import lombok.Getter;

import java.util.Random;

@Getter
public enum UserName {
    FIRST_NAME(new String[]{"Emma", "Liam", "Olivia", "Noah", "Ava", "Ethan", "Sophia", "Logan", "Mia", "Lucas", "Amelia", "Jackson", "Charlotte", "Elijah", "Harper", "Aiden", "Aria", "Caden", "Ella", "Grayson", "Mila", "Michael", "Luna", "Jacob", "Avery", "William", "Evelyn", "Benjamin", "Chloe", "Carter", "Lily", "Mason", "Hannah", "Evelyn", "Mia", "Oliver", "Layla", "Daniel", "Abigail", "Sebastian", "Emily"}),
    LAST_NAME(new String[]{"Smith", "Johnson", "Brown", "Taylor", "Miller", "Wilson", "Moore", "Anderson", "Jackson", "Harris", "Thompson", "Martin", "Garcia", "Martinez", "Davis", "Rodriguez", "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins", "Stewart", "Sanchez", "Morris", "Rogers", "Reed", "Cook", "Cooper", "Morgan", "Bell", "Murphy", "Bailey", "Rivera", "Hernandez", "Gonzales", "Ford", "Hamilton", "Graham", "Sullivan", "Wallace", "Woods"});

    private final String[] names;

    UserName(String[] names) {
        this.names = names;
    }

    private String generate(boolean isFirstName) {
        Random random = CommonUtil.CommonRandom;
        String[] name = isFirstName ? FIRST_NAME.names : LAST_NAME.names;
        return name[random.nextInt(name.length)];
    }

    public static String generateFullName() {
        Random random = CommonUtil.CommonRandom;
        UserName userName = values()[random.nextInt(values().length)];
        String firstName = userName.generate(true);
        String lastName = userName.generate(false);
        return firstName + " " + lastName;
    }
}
