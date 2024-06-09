package com.school.dto.schoolNews;

import com.school.models.SchoolNews;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class SchoolNewsResponse {
    private long id;
    private String title;
    private String time;

    public SchoolNewsResponse(SchoolNews schoolNews) {
        this.title = schoolNews.getTitle();
        this.time = schoolNews.getTime().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.id = schoolNews.getId();
    }
}
