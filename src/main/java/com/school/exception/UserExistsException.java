package com.school.exception;

public class UserExistsException extends RuntimeException{
    public UserExistsException() {}
    public UserExistsException(String message) {super(message);}
}
