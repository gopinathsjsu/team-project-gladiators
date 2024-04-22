package org.example.cmpe202_final.repository.courses;

import org.example.cmpe202_final.model.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findAllBy();

    @Query("{ 'enrolledStudents' : ?0 }")
    List<Course> findByEnrolledStudent(String studentId);

    List<Course> findByInstructor(String instructor);

}
