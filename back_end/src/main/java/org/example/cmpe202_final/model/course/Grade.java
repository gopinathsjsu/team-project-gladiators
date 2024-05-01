package org.example.cmpe202_final.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("grades")
public class Grade {
    public static final int NOT_GRADED = -1;
    @Id
    private String id;
    private int score = NOT_GRADED;
    private String studentId;
    private String assignmentId;
}
