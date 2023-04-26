package com.platform.common.exception;

import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import lombok.Getter;

@Getter
public class APIException extends Exception {

    private static final long serialVersionUID = -8384843427879006950L;
    private final transient BaseResponse response;

    public APIException(ErrorType errorType) {
        super(errorType.getMessage());

        response = new BaseResponse(errorType);
    }

    public APIException(ErrorType errorType, String message) {
        super(message);

        response = new BaseResponse(errorType);
        response.setRspMsg(message);
    }
}
