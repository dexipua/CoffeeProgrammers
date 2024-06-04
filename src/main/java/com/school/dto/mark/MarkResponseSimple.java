package com.school.dto.mark;

import com.school.models.Mark;
import lombok.Data;

@Data
public class MarkResponseSimple {
    long id;
    private int value;

    public MarkResponseSimple(Mark mark) {
        this.id = mark.getId();
        this.value = mark.getValue();
    }
}
