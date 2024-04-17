package org.example.cmpe202_final.controller.course.strategy;

import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseViewEntity;

import java.util.ArrayList;

public interface CourseStrategy {
   ArrayList<CourseViewEntity> getCourseViews(
           CourseService courseService,
           SemesterService semesterService,
           UserService userService,
           String userId
   );
}
