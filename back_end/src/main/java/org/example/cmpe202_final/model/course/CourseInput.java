package org.example.cmpe202_final.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cmpe202_final.model.user.User;

import java.util.ArrayList;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseInput {
    private ArrayList<Semester> semesters;
    private ArrayList<User> users;
}
