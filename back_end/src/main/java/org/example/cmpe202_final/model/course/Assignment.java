package org.example.cmpe202_final.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "assignments")
public class Assignment {
    @Id
    private String id;
    private Date dueDate;
    private String name;
    private ArrayList<Grade> grades;
}