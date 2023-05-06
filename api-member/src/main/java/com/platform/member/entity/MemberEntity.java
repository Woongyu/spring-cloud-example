package com.platform.member.entity;

import com.platform.member.dto.enums.Grade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class MemberEntity {
    private Integer userId;
    private String userName;
    private Integer userAge;
    private String country;
    private Integer tier;
    private String gradeName;
    private String useYn;

    public void updateTier(Integer tier) {
        this.tier = tier;
        this.gradeName = Grade.from(tier).getName();
    }
}
