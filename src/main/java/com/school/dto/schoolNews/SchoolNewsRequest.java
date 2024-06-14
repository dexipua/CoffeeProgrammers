package com.school.dto.schoolNews;

import com.school.models.SchoolNews;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchoolNewsRequest {
    @NotBlank(message = "The text must be provided")
    private String text;

    public static SchoolNews toSchoolNews(SchoolNewsRequest schoolNewsRequest) {
        return new SchoolNews(schoolNewsRequest.getText(), LocalDateTime.now());
    }
}
