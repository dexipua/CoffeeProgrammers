package com.school.exception;

import com.school.dto.exception.ExceptionResponse;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ExceptionResponse> ConstraintViolationException(ConstraintViolationException e) {
        log.error("handleEntityExistsException: {}", e.getMessage());

        return e.getConstraintViolations().stream()
                .map((ex) -> new ExceptionResponse(ex.getMessage()))
                .sorted(Comparator.comparing((ExceptionResponse::getMessage)))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(RuntimeException e) {
        log.error("handleEntityNotFoundException: {}", e.getMessage());
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler({
            EntityExistsException.class,
            UnsupportedOperationException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestExceptions(RuntimeException e) {
        log.error("handleBadRequestExceptions: {}", e.getMessage());
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleBadCredentialsException(BadCredentialsException e) {
        log.error("handleBadCredentialsException: {}", e.getMessage());
        return new ExceptionResponse(e.getMessage());
    }
}
