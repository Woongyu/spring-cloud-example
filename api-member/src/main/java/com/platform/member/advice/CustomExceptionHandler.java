package com.platform.member.advice;

import com.platform.member.dto.common.BaseResponse;
import com.platform.member.dto.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleWebExchangeBindException(WebExchangeBindException e) {
        log.error("WebExchangeBindException ::", e);

        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
            .getAllErrors()
            .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseResponse> handleResponseStatusException(ResponseStatusException e) {
        log.error("ResponseStatusException ::", e);

        HttpStatus status = e.getStatus();
        BaseResponse response = new BaseResponse(ErrorType.findByErrorType(status.value()));

        return ResponseEntity.status(status)
            .body(response);
    }
}
