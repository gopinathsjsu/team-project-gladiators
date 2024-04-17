package org.example.cmpe202_final.controller.course.strategy;

import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseViewCourse;
import org.example.cmpe202_final.view.course.CourseViewEntity;

import java.util.ArrayList;
import java.util.List;

public class DefaultCourseStrategy implements CourseStrategy{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof DefaultCourseStrategy;
    }

    @Override
    public ArrayList<CourseViewEntity> getCourseViews(
            CourseService courseService,
            SemesterService semesterService,
            UserService userService,
            String userId) {
       List<Course> courses =  courseService.findAllCourses();
       ArrayList<CourseViewEntity> courseViews = new ArrayList<>();
        for (Course course: courses) {
            courseViews.add(new CourseViewCourse(course));
        }
       return courseViews;
    }
}
