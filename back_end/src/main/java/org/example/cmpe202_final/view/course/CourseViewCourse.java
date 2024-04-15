package org.example.cmpe202_final.view.course;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cmpe202_final.model.course.Course;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Getter
@AllArgsConstructor
@JsonTypeName(CourseViewEntity.COURSE_TYPE)
public class CourseViewCourse implements CourseViewEntity {

    @Id
    private String id;
    private String instructor;
    private Set<String> enrolledStudents;
    private Set<String> assignments;
    private String semester;
    private boolean isPublished;
    private String name;
    private String description;

    public CourseViewCourse(Course course) {
        this.id = course.getId();
        this.instructor = course.getInstructor();
        this.assignments = course.getAssignments();
        this.semester = course.getSemester();
        this.isPublished = course.isPublished();
        this.name = course.getName();
        this.description = course.getDescription();
        this.enrolledStudents = course.getEnrolledStudents();
    }

    @Override
    public String getType() {
        return  CourseViewEntity.COURSE_TYPE;
    }
}
