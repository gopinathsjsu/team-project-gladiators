package org.example.cmpe202_final.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GradeWithStudentName {
    private String id;
    private int score;
    private String studentFirstName;
    private String studentLastName;
    private String studentId;
    private String submissionLink;
}

