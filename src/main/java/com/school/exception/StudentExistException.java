package com.school.exception;

public class StudentExistException extends RuntimeException{
    public StudentExistException() {}
    public StudentExistException(String message) {super(message);}
}
