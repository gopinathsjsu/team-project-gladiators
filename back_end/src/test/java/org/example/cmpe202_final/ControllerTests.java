package org.example.cmpe202_final;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerTests.class)
public class ControllerTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Nested
    public class AdminControllerTest {

        @Test
        public void getAdminById_ExistingId_ShouldReturnAdmin() throws Exception {
            mockMvc.perform(get("/admins/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Admin Jane"))
                    .andExpect(jsonPath("$.email").value("adminjane@example.com"));
        }

        @Test
        public void getAdminById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/admins/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class FacultyControllerTest {

        @Test
        public void getFacultyById_ExistingId_ShouldReturnFaculty() throws Exception {
            mockMvc.perform(get("/faculties/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Faculty John"))
                    .andExpect(jsonPath("$.email").value("facultyjohn@example.com"));
        }

        @Test
        public void getFacultyById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/faculties/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class StudentControllerTest {
        @Test
        public void getStudentById_ExistingId_ShouldReturnStudent() throws Exception {
            mockMvc.perform(get("/students/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Student Jane"))
                    .andExpect(jsonPath("$.email").value("studentjane@example.com"));
        }

        @Test
        public void getStudentById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/students/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class CourseControllerTest {
        @Test
        public void getStudentsByCourseId_WhenStudentsExist_ShouldReturnStudents() throws Exception {
            // Given
            Student student1 = new Student(1, "Student One", "student1@example.com");
            Student student2 = new Student(2, "Student Two", "student2@example.com");
            List<Student> students = Arrays.asList(student1, student2);
            Course course = new Course(1, "Java 101", "Introduction to Java", "Spring 2024", true, null, students, null, null, null);

            given(courseService.findCourseById(1)).willReturn(Optional.of(course));

            // When & Then
            mockMvc.perform(get("/courses/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(student1.getId()))
                    .andExpect(jsonPath("$[0].name").value(student1.getName()))
                    .andExpect(jsonPath("$[1].id").value(student2.getId()))
                    .andExpect(jsonPath("$[1].name").value(student2.getName()));
        }

        @Test
        public void getStudentsByCourseId_WhenCourseDoesNotExist_ShouldReturnNotFound() throws Exception {
            // Given
            int nonExistentCourseId = 999;
            given(courseService.findCourseById(nonExistentCourseId)).willReturn(Optional.empty());

            // When & Then
            mockMvc.perform(get("/courses/{id}", nonExistentCourseId))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class AssignmentControllerTest {

        @Test
        public void getAssignmentById_ExistingId_ShouldReturnAssignment() throws Exception {
            mockMvc.perform(get("/assignments/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.content").value("Assignment content"));
        }

        @Test
        public void getAssignmentById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/assignments/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class QuizControllerTest {

        @Test
        public void getQuizById_ExistingId_ShouldReturnQuiz() throws Exception {
            mockMvc.perform(get("/quizzes/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.content").value("Quiz content"));
        }

        @Test
        public void getQuizById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/quizzes/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class AnnouncementControllerTest {

        @Test
        public void getAnnouncementById_ExistingId_ShouldReturnAnnouncement() throws Exception {
            mockMvc.perform(get("/announcements/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.message").value("Announcement message"));
        }

        @Test
        public void getAnnouncementById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/announcements/999"))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    public class GradeControllerTest {

        @Test
        public void getGradeById_ExistingId_ShouldReturnGrade() throws Exception {
            mockMvc.perform(get("/grades/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.value").value(95.0));
        }

        @Test
        public void getGradeById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/grades/999"))
                    .andExpect(status().isNotFound());
        }
    }
}