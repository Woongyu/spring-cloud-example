package com.platform.common.dto.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ErrorType {

    SUCCESS(200, Constants.SUCCESS, "성공"),
    INVALID_PARAMS(400, "40001", "유효하지 않은 파라미터"),
    NO_END_POINT(404, "40401", "존재하지 않는 엔드포인트"),
    NO_DATA(404, "40402", "존재하지 않는 자산"),
    NO_USER(404, "40403", "존재하지 않는 사용자"),
    SYSTEM_ERROR(500, "50001", "시스템 장애"),
    API_ERROR(500, "50002", "API 요청 처리 실패"),
    UNKNOWN_ERROR(500, "50004", "알 수 없는 에러"),
    SERVER_ERROR(500, "server_error", "서버 오류");

    private final int httpStatus;
    private final String code;
    private final String message;

    ErrorType(final int httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private static class Constants {
        public static final String SUCCESS = "00000";
    }

    public static ErrorType findByErrorType(int status) {
        return Arrays.stream(ErrorType.values())
            .filter(errorType -> errorType.getHttpStatus() == status)
            .findAny()
            .orElse(null);
    }
}
