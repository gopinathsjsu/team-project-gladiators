package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public List<Course> findAllCourses(){
        return courseRepository.findAllBy();
    }

    public List<Course> findByEnrolledStudent(String studentId){
        return courseRepository.findByEnrolledStudent(studentId);
    }

    public List<Course> findByInstructor(String instructor){
        return courseRepository.findByInstructor(instructor);
    }

    public List<User> findStudentsByCourseId(String courseId) {
        return userRepository.findByCourseId(courseId);
    }
}
