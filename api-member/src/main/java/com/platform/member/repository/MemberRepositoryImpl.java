package com.platform.member.repository;

import com.platform.common.constant.CommonConstants;
import com.platform.member.entity.MemberEntity;
import com.platform.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final Map<Integer, MemberEntity> userMap = new ConcurrentHashMap<>();
    private final AtomicInteger globalIndex = new AtomicInteger(CommonConstants.ONE);

    @PostConstruct
    private void init() {
        createUserMap();
    }

    private void createUserMap() {
        final int maxIndex = 20;
        while (globalIndex.get() <= maxIndex) {
            MemberEntity entity = MemberEntity.builder()
                .userId(globalIndex.get())
                .userName(CommonUtil.generateUserName())
                .userAge(CommonUtil.CommonRandom.nextInt(60))
                .country(CommonUtil.generateCountry())
                .useYn(CommonConstants.USE_Y)
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
        int userId = globalIndex.get();
        entity.setUserId(userId);
        userMap.put(globalIndex.getAndIncrement(), entity);
        return userId;
    }

    @Override
    public Flux<MemberEntity> findAll() {
        // Return shuffled result
        List<MemberEntity> shuffledValues = new ArrayList<>(userMap.values());
        Collections.shuffle(shuffledValues);
        return Flux.fromIterable(shuffledValues);
    }

    @Override
    public Integer findMaxUserId() {
        return userMap.keySet()
            .stream()
            .mapToInt(Integer::intValue)
            .max()
            .orElse(CommonConstants.ZERO); // Concern about occurrence of IllegalArgumentException
    }
}
