package com.school.exception;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(){}
    public TeacherNotFoundException(String message){super(message);}
}
