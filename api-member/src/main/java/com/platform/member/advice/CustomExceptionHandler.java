package com.platform.member.advice;

import com.platform.common.dto.BaseResponse;
import com.platform.common.dto.ErrorResponse;
import com.platform.common.dto.enums.ErrorType;
import com.platform.common.exception.APIException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    protected ResponseEntity<Map<String, String>> handleWebExchangeBindException(WebExchangeBindException e) {
        log.error("WebExchangeBindException ::", e);

        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
            .getAllErrors()
            .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException e) {
        log.error("ResponseStatusException ::", e);

        ErrorResponse errorResponse = new ErrorResponse(e.getStatus().value(), e.getReason());
        return ResponseEntity.status(e.getRawStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(APIException.class)
    protected ResponseEntity<BaseResponse> handleAPIException(APIException e) {
        log.error("APIException ::", e);

        BaseResponse response = e.getResponse();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getHttpStatus()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new BaseResponse(ErrorType.API_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, String>> handleNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException ::", e);

        final Map<String, String> errors = new HashMap<>();
        e.getBindingResult()
            .getAllErrors()
            .forEach(error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException ::", e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
