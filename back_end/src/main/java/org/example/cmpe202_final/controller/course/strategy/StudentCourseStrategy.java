package org.example.cmpe202_final.controller.course.strategy;

import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseViewCourse;
import org.example.cmpe202_final.view.course.CourseViewEntity;
import org.example.cmpe202_final.view.course.CourseViewSemester;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentCourseStrategy implements CourseStrategy{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StudentCourseStrategy;
    }

    @Override
    public ArrayList<CourseViewEntity> getCourseViews(
            CourseService courseService,
            SemesterService semesterService,
            UserService userService,
            String userId
    ) {
        List<Course> courses = courseService.findByEnrolledStudent(userId);
        List<Semester> semesters = semesterService.findAllSemesters();

        if(courses.isEmpty()){
            return new ArrayList<>();
        }

        // Organize courses by semesters
        HashMap<String, ArrayList<CourseViewEntity>> coursesBySemester = new HashMap<>();
        for (Course course : courses) {
            if(!course.isPublished()){
                continue; // Exclude unpublished courses
            }
            ArrayList<CourseViewEntity> existingCourses = coursesBySemester.get(course.getSemester());
            if(existingCourses == null){
                existingCourses = new ArrayList<>();
            }
            existingCourses.add(new CourseViewCourse(course));
            coursesBySemester.put(course.getSemester(), existingCourses);
        }

        // Compile final List
        ArrayList<CourseViewEntity> views = new ArrayList<>();
        for (Semester semester : semesters){
            ArrayList<CourseViewEntity> courseViews = coursesBySemester.get(semester.getId());
            if(courseViews == null){
                continue;
            }
            views.add(new CourseViewSemester(semester));
            views.addAll(coursesBySemester.get(semester.getId()));
        }
        return views;
    }
}
