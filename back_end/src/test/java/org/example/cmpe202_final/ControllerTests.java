package org.example.cmpe202_final;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ControllerTests.class)
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

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
        public void getCourseById_ExistingId_ShouldReturnCourse() throws Exception {
            mockMvc.perform(get("/courses/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Course 101"))
                    .andExpect(jsonPath("$.description").value("Introduction to Java"));
        }

        @Test
        public void getCourseById_NonExistingId_ShouldReturnNotFound() throws Exception {
            mockMvc.perform(get("/courses/999"))
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