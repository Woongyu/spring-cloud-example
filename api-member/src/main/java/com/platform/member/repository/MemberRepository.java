package com.platform.member.repository;

import com.platform.member.entity.MemberEntity;
import reactor.core.publisher.Flux;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberEntity> findByUserId(Integer userId);

    Integer save(MemberEntity entity);

    Flux<MemberEntity> findAll();

    Integer findMaxUserId();
}
