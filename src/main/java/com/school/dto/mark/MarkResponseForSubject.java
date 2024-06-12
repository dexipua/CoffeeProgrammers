package com.school.dto.mark;

import com.school.dto.student.StudentResponseSimple;
import com.school.models.Mark;
import com.school.models.Student;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Setter
@Getter
public class MarkResponseForSubject {
    private StudentResponseSimple studentResponseSimple;
    private List<MarkResponseSimple> marks;

    public MarkResponseForSubject(Student student, List<Mark> marks) {
        this.studentResponseSimple = new StudentResponseSimple(student);

        this.marks = marks.stream()
                .map(MarkResponseSimple::new)
                .collect(Collectors.toList());
    }
}
