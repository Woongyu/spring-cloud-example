package com.platform.board.advice;

import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new BaseResponse(ErrorType.API_ERROR));
    }
}
