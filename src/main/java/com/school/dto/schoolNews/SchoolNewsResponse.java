package com.school.dto.schoolNews;

import com.school.models.SchoolNews;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class SchoolNewsResponse {
    private String title;
    private LocalDateTime time;

    public SchoolNewsResponse(SchoolNews schoolNews) {
        this.title = schoolNews.getTitle();
        this.time = schoolNews.getTime();
    }
}
