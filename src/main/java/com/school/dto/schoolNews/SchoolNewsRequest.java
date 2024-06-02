package com.school.dto.schoolNews;

import com.school.models.SchoolNews;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class SchoolNewsRequest {
    private String title;

    public static SchoolNews toSchoolNews(SchoolNewsRequest schoolNewsRequest) {
        return new SchoolNews(schoolNewsRequest.getTitle(), LocalDateTime.now());
    }
}
