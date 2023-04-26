package com.platform.member.repository;

import com.platform.member.entity.MemberEntity;
import com.platform.member.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final Map<Integer, MemberEntity> userMap = new HashMap<>();
    private final AtomicInteger globalIndex = new AtomicInteger(1);

    @PostConstruct
    private void init() {
        makeUserMap();
    }

    private void makeUserMap() {
        final int max = 20;
        while (globalIndex.get() <= max) {
            MemberEntity entity = MemberEntity.builder()
                .userId(globalIndex.get())
                .userName(CommonUtil.generateUserName())
                .userAge(CommonUtil.CommonRandom.nextInt(60))
                .country(CommonUtil.generateCountry())
                .build();

            userMap.put(globalIndex.getAndIncrement(), entity);
        }
    }

    @Override
    public Optional<MemberEntity> findByUserId(Integer userId) {
        return Optional.ofNullable(userMap.get(userId));
    }

    @Override
    public Integer save(MemberEntity entity) {
        userMap.put(globalIndex.getAndIncrement(), entity);
        return globalIndex.get();
    }
}
