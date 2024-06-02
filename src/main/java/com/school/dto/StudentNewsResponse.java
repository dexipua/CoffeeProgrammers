package com.school.dto;

import com.school.models.StudentNews;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class StudentNewsResponse {
    private long id;
    private String title;
    private String time;

    public StudentNewsResponse(StudentNews news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.time = news.getTime().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
