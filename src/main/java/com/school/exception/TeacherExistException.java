package com.school.exception;

public class TeacherExistException extends RuntimeException {
    public TeacherExistException() {}
    public TeacherExistException(String message) {super(message);}
}
