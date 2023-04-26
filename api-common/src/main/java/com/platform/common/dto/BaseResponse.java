package com.platform.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.common.dto.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BaseResponse {

    @JsonIgnore
    private int httpStatus;

    @JsonProperty("rsp_code")
    protected String rspCode;

    @JsonProperty("rsp_msg")
    protected String rspMsg;

    public BaseResponse() {
        httpStatus = ErrorType.SUCCESS.getHttpStatus();
        rspCode = ErrorType.SUCCESS.getCode();
        rspMsg = ErrorType.SUCCESS.getMessage();
    }

    public BaseResponse(ErrorType errorType) {
        httpStatus = errorType.getHttpStatus();
        rspCode = errorType.getCode();
        rspMsg = errorType.getMessage();
    }

    public void setErrorType(ErrorType errorType) {
        httpStatus = errorType.getHttpStatus();
        rspCode = errorType.getCode();
        rspMsg = errorType.getMessage();
    }
}
