package org.example.cmpe202_final.controller.course.strategy;


import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.service.course.CourseService;
import org.example.cmpe202_final.service.semester.SemesterService;
import org.example.cmpe202_final.service.user.UserService;
import org.example.cmpe202_final.view.course.CourseViewCourse;
import org.example.cmpe202_final.view.course.CourseViewEntity;
import org.example.cmpe202_final.view.course.CourseViewFaculty;
import org.example.cmpe202_final.view.course.CourseViewSemester;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AdminCourseStrategy implements CourseStrategy{

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AdminCourseStrategy;
    }
    @Override
    public ArrayList<CourseViewEntity> getCourseViews(
            CourseService courseService,
            SemesterService semesterService,
            UserService userService,
            String userId
    ) {
        List<Course> courses = courseService.findAllCourses();
        List<Semester> semesters = semesterService.findAllSemesters();
        List<User> professors = userService.findByType(UserType.FACULTY);

        // Organize courses by professors by semester
        HashMap<String, HashMap<String, ArrayList<CourseViewEntity>>> coursesByProfessorBySemester = new HashMap<>();
        for (Course course : courses) {
            String instructor = course.getInstructor() != null ? course.getInstructor() : "UNASSIGNED";
            HashMap<String, ArrayList<CourseViewEntity>> existingCoursesByInstructor = coursesByProfessorBySemester.get(instructor);
            if(existingCoursesByInstructor == null){
                existingCoursesByInstructor = new HashMap<>();
            }
            ArrayList<CourseViewEntity> existingCourses = existingCoursesByInstructor.get(course.getSemester());
            if(existingCourses == null){
                existingCourses = new ArrayList<>();
            }
            existingCourses.add(new CourseViewCourse(course));
            existingCoursesByInstructor.put(course.getSemester(), existingCourses);
            coursesByProfessorBySemester.put(instructor, existingCoursesByInstructor);
        }

        // Sort professors alphabetically
        professors.sort(new UserSortComparator());

        // Compile final List
        ArrayList<CourseViewEntity> views = new ArrayList<>();
        for (User professor : professors){
            HashMap<String, ArrayList<CourseViewEntity>> coursesBySemester = coursesByProfessorBySemester.get(professor.getId());
            if(coursesBySemester == null || coursesBySemester.isEmpty()){
               continue;
            }
            views.add(new CourseViewFaculty(professor));
            // Get Courses by semester for this user
            for (Semester semester: semesters) {
                if(coursesBySemester.containsKey(semester.getId())){
                    views.add(new CourseViewSemester(semester));
                    views.addAll(coursesBySemester.get(semester.getId()));
                }
            }
        }

        // Add Unassigned courses
        HashMap<String, ArrayList<CourseViewEntity>> unassignedCourses = coursesByProfessorBySemester.get("UNASSIGNED");
        if(unassignedCourses == null || unassignedCourses.isEmpty()){
            return views;
        }
        views.add(CourseViewFaculty.getUnassignedView());
        // Get Courses by semester for this user
        for (Semester semester: semesters) {
            if(unassignedCourses.containsKey(semester.getId())){
                views.add(new CourseViewSemester(semester));
                views.addAll(unassignedCourses.get(semester.getId()));
            }
        }

        return views;
    }
}
