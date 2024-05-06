package org.example.cmpe202_final.service.course;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.repository.courses.CourseRepository;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public List<Course> findAllCourses(){
        return courseRepository.findAllBy();
    }

    public Optional<Course> findById(String courseId){
        return courseRepository.findById(courseId);
    }

    public List<Course> findByEnrolledStudent(String studentId){
        return courseRepository.findByEnrolledStudent(studentId);
    }

    public Course addItem(Course course){
        return courseRepository.save(course);
    }

    public List<Course> findByInstructor(String instructor){
        return courseRepository.findByInstructor(instructor);
    }
    public List<User> findStudentsByCourseId(String courseId) {
        // Fetch the course by ID
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Get the enrolled student IDs from the course
        Set<String> enrolledStudentIds = course.getEnrolledStudents();

        // Fetch and return the student Users
        return userRepository.findStudentsByEnrolledIds(enrolledStudentIds);
    }
}
