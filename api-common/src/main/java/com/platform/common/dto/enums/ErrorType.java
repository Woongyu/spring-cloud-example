package com.platform.common.dto.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    SUCCESS(200, Constants.SUCCESS, "Success"),
    INVALID_PARAMS(400, "40001", "Invalid parameters"),
    AUTHENTICATION_FAILED(401, "40101", "Authentication failed"),
    PAYMENT_REQUIRED(402, "40201", "Payment required"),
    FORBIDDEN(403, "40301", "Forbidden access"),
    ENDPOINT_NOT_FOUND(404, "40401", "Endpoint not found"),
    DATA_NOT_FOUND(404, "40402", "Data not found"),
    USER_NOT_FOUND(404, "40403", "User not found"),
    TOO_MANY_REQUESTS(429, "42901", "Too many requests"),
    SYSTEM_ERROR(500, "50001", "System error"),
    API_ERROR(500, "50002", "API error"),
    UNKNOWN_ERROR(500, "50004", "Unknown error"),
    SERVER_ERROR(500, "server_error", "Server error"),
    NOT_IMPLEMENTED(501, "50101", "Not implemented"),
    BAD_GATEWAY(502, "50201", "Bad gateway"),
    SERVICE_UNAVAILABLE(503, "50301", "Service unavailable"),
    GATEWAY_TIMEOUT(504, "50401", "Gateway timeout");

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
}
