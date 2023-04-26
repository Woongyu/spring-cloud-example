package com.platform.member.service;

import com.platform.member.dto.MemberDetail;
import com.platform.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Mono<MemberDetail.Response> getMemberById(Integer userId) {
        return Mono.fromSupplier(() -> memberRepository.findByUserId(userId)
            .map(memberEntity -> {
                MemberDetail.Response response = new MemberDetail.Response();
                BeanUtils.copyProperties(memberEntity, response);
                return response;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NO DATA")));
    }
}
