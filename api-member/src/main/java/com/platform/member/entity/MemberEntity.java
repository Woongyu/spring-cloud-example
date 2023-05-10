package com.platform.member.entity;

import com.platform.member.dto.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class MemberEntity extends BaseDateEntity {
    private Integer userId;
    private String userName;
    private Integer userAge;
    private String country;
    private Integer tier;
    private String gradeName;
    private String useYn;

    public Optional<MemberEntity> updateTier(Integer tier) {
        try {
            Grade grade = Grade.from(tier);
            this.tier = tier;
            this.gradeName = grade.getName();
            return Optional.of(this);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
