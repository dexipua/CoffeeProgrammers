package com.school.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ExceptionResponse {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime dateTime;

    public ExceptionResponse(String message, HttpStatus httpStatus, ZonedDateTime dateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.dateTime = dateTime;
    }

}
