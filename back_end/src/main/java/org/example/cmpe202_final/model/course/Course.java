package org.example.cmpe202_final.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String instructor;
    private Set<String> enrolledStudents;
    private Set<String> assignments;
    private String semester;
    private boolean isPublished;
    private String name;
    private String description;
}
