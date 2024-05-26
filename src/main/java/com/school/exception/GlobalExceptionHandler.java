package com.school.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {StudentExistException.class})
    public ResponseEntity<?> handleStudentExistException(StudentExistException ex) {
        log.error("handleStudentExistException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {StudentNotFoundException.class})
    public ResponseEntity<?> handleStudentNotFoundException(StudentNotFoundException ex) {
        log.error("handleStudentNotFoundException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TeacherExistException.class})
    public ResponseEntity<?> handleTeacherExistException(TeacherExistException ex) {
        log.error("handleTeacherExistException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TeacherNotFoundException.class})
    public ResponseEntity<?> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        log.error("handleTeacherNotFoundException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<?> handleUserExistException(UserExistsException ex) {
        log.error("handleUserExistException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("handleUserNotFoundException: {}", ex.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

}
