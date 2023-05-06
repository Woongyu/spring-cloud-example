package com.platform.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    @JsonProperty("status")
    protected int status;

    @JsonProperty("message")
    protected String message;
}
