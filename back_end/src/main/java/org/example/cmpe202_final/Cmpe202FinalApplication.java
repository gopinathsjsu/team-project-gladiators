package org.example.cmpe202_final;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Cmpe202FinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cmpe202FinalApplication.class, args);
    }


    @RestController
    public static class AdminController {

        @GetMapping("/admins/{id}")
        public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
            // Simulated data retrieval logic
            if (id == 999) { // Assume 999 is an ID that does not exist
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Admin(id, "Admin Jane", "adminjane@example.com"));
            }
        }
    }

    @RestController
    public static class FacultyController {

        @GetMapping("/faculties/{id}")
        public ResponseEntity<Faculty> getFacultyById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Faculty(id, "Faculty John", "facultyjohn@example.com"));
            }
        }
    }

    @RestController
    public static class StudentController {

        @GetMapping("/students/{id}")
        public ResponseEntity<Student> getStudentById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Student(id, "Student Jane", "studentjane@example.com"));
            }
        }
    }

    @RestController
    public static class CourseController {

        @GetMapping("/courses/{id}")
        public ResponseEntity<Course> getCourseById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Course(id, "Course 101", "Introduction to Java", "Spring 2024", true, null, null, null, null, null));
            }
        }
    }

    @RestController
    public static class AssignmentController {

        @GetMapping("/assignments/{id}")
        public ResponseEntity<Assignment> getAssignmentById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Assignment(id, "Assignment content", null, "2024-04-01", 100));
            }
        }
    }

    @RestController
    public static class QuizController {

        @GetMapping("/quizzes/{id}")
        public ResponseEntity<Quiz> getQuizById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Quiz(id, "Quiz content", null, null));
            }
        }
    }

    @RestController
    public static class AnnouncementController {

        @GetMapping("/announcements/{id}")
        public ResponseEntity<Announcement> getAnnouncementById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Announcement(id, "Announcement message", new java.util.Date(), null));
            }
        }
    }

    @RestController
    public static class GradeController {

        @GetMapping("/grades/{id}")
        public ResponseEntity<Grade> getGradeById(@PathVariable int id) {
            if (id == 999) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(new Grade(id, null, null, 95.0));
            }
        }
    }
}
