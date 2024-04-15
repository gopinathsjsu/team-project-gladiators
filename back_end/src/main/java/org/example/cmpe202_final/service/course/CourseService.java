package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> findAllCourses(){
        return courseRepository.findAllBy();
    }

    public List<Course> findByEnrolledStudent(String studentId){
        return courseRepository.findByEnrolledStudent(studentId);
    }

    public List<Course> findByInstructor(String instructor){
        return courseRepository.findByInstructor(instructor);
    }
}
