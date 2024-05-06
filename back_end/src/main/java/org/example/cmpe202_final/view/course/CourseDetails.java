package org.example.cmpe202_final.view.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDetails {
    private Course course;
    private User instructor;
}
