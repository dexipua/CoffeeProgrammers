package com.school.dto.userNews;

import com.school.models.UserNews;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
public class UserNewsResponse {
    private long id;
    private String text;
    private String time;

    public UserNewsResponse(UserNews news) {
        this.id = news.getId();
        this.text = news.getText();
        this.time = news.getTime().withNano(0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
