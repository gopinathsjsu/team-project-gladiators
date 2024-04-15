package org.example.cmpe202_final.view.course;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CourseViewCourse.class, name = CourseViewEntity.COURSE_TYPE),
        @JsonSubTypes.Type(value = CourseViewFaculty.class, name =  CourseViewEntity.FACULTY_TYPE),
        @JsonSubTypes.Type(value = CourseViewSemester.class, name =  CourseViewEntity.SEMESTER_TYPE)
})
public interface CourseViewEntity {
    String COURSE_TYPE = "Course";
    String FACULTY_TYPE = "Faculty";
    String SEMESTER_TYPE = "Semester";

    String getType();
}
