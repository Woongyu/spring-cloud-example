package com.platform.member.entity;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
public class BaseDateEntity {

    //@CreatedDate
    private LocalDateTime rgstDt;

    //@LastModifiedDate
    private LocalDateTime chngDt;
}
