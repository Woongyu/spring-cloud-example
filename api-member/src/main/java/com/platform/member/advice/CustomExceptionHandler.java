package com.platform.member.advice;

import com.platform.member.dto.common.BaseResponse;
import com.platform.member.dto.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({DecodingException.class, WebExchangeBindException.class})
    public ResponseEntity<BaseResponse> handleValidationException(Exception e) {
        BaseResponse response = new BaseResponse(ErrorType.INVALID_PARAMS);

        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getHttpStatus()));
    }
}
