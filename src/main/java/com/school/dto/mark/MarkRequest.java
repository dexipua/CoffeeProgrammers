package com.school.dto.mark;

import com.school.models.Mark;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class MarkRequest {
    @NotNull(message = "Mark value must be provided")
    @Min(value = 1, message = "Mark must be at least 1")
    @Max(value = 12, message = "Mark must be at most 12")
    private Integer value;

    public static Mark toMark(MarkRequest markRequest){
        Mark mark = new Mark();
        mark.setValue(markRequest.getValue());
        mark.setTime(LocalDateTime.now());
        return mark;
    }
}
