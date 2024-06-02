package com.school.dto.mark;

import com.school.models.Mark;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class MarkRequest {
    private int mark;

    public static Mark toMark(MarkRequest markRequest) {
        Mark result = new Mark();
        result.setMark(markRequest.getMark());
        return result;
    }
}
