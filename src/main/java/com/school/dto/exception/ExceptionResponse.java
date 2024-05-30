package com.school.dto.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ExceptionResponse {
    private final String message;

    public ExceptionResponse(String message) {
        this.message = message;

    }

}
