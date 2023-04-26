package com.platform.member.repository;

import com.platform.member.entity.MemberEntity;
import com.platform.member.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final Map<Integer, MemberEntity> userMap = new HashMap<>();

    @PostConstruct
    private void init() {
        makeUserMap();
    }

    private void makeUserMap() {
        int index = 1;
        final int max = 20;
        while (index <= max) {
            MemberEntity entity = MemberEntity.builder()
                .userId(index)
                .userName(CommonUtil.generateUserName())
                .userAge(CommonUtil.CommonRandom.nextInt(60))
                .country(CommonUtil.generateCountry())
                .build();

            userMap.put(index++, entity);
        }
    }

    @Override
    public Optional<MemberEntity> findByUserId(Integer userId) {
        return Optional.ofNullable(userMap.get(userId));
    }
}
