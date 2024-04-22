package org.example.cmpe202_final.controller.courses;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cmpe202_final.controller.course.CourseController;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CourseController.class)
public class CoursesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SemesterService semesterService;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Set up any necessary test data here
    }

    @Test
    public void testGetCoursesWithoutUserId() throws Exception {
        // Mock Response
        given(courseService.findAllCourses()).willReturn(getCourses());

        // Test Response without userId
        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getDefaultResponseViews())))
                .andReturn();
    }

    @Test
    public void testGetCoursesForStudent() throws Exception {
        Date mockDate = new Date();
        // Mock Response
        given(courseService.findByEnrolledStudent("studentId")).willReturn(getCourses());
        given(semesterService.findAllSemesters()).willReturn(getSemesters(mockDate));
        given(userService.findById("studentId")).willReturn(getStudent());

        // Test Response without userId
        mockMvc.perform(MockMvcRequestBuilders.get("/courses?userId=studentId"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getStudentResponseViews(mockDate))))
                .andReturn();
    }

    @Test
    public void testGetCoursesForProfessor() throws Exception {
        Date mockDate = new Date();
        // Mock Response
        given(courseService.findByInstructor("instructor1")).willReturn(getCourses());
        given(semesterService.findAllSemesters()).willReturn(getSemesters(mockDate));
        given(userService.findById("instructor1")).willReturn(getOptionalFaculty());

        // Test Response without userId
        mockMvc.perform(MockMvcRequestBuilders.get("/courses?userId=instructor1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getProfessorResponseViews(mockDate))))
                .andReturn();
    }

    @Test
    public void testGetCoursesForAdmin() throws Exception {
        Date mockDate = new Date();
        // Mock Response
        given(courseService.findAllCourses()).willReturn(getCourses());
        given(semesterService.findAllSemesters()).willReturn(getSemesters(mockDate));
        given(userService.findByType(UserType.FACULTY)).willReturn(List.of(getFaculty()));
        given(userService.findById("admin1")).willReturn(getAdmin());

        // Test Response without userId
        mockMvc.perform(MockMvcRequestBuilders.get("/courses?userId=admin1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getAdminViews(mockDate))))
                .andReturn();
    }

    List<CourseViewCourse> getDefaultResponseViews() {
        return Arrays.asList(
                new CourseViewCourse(
                        "id1",
                        "instructor1",
                        new HashSet<>(Set.of("Student3", "Student4")),
                        new HashSet<>(Set.of("Assignment3", "Assignment4")),
                        "semester1",
                        true,
                        "course1",
                        "description1"
                ),
                new CourseViewCourse(
                        "id2",
                        "instructor1",
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        true,
                        "course2",
                        "description2"
                ),
                new CourseViewCourse(
                        "id3",
                        null,
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        false,
                        "course2",
                        "description2"
                )
        );
    }

    List<CourseViewEntity> getProfessorResponseViews(Date date) {
        return Arrays.asList(
                new CourseViewSemester(
                        "semester1",
                        date,
                        date,
                        "Semester Name"
                ),
                new CourseViewCourse(
                        "id1",
                        "instructor1",
                        new HashSet<>(Set.of("Student3", "Student4")),
                        new HashSet<>(Set.of("Assignment3", "Assignment4")),
                        "semester1",
                        true,
                        "course1",
                        "description1"
                ),
                new CourseViewCourse(
                        "id2",
                        "instructor1",
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        true,
                        "course2",
                        "description2"
                ),
                new CourseViewCourse(
                        "id3",
                        null,
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        false,
                        "course2",
                        "description2"
                )
        );
    }

    List<CourseViewEntity> getStudentResponseViews(Date date) {
        return Arrays.asList(
                new CourseViewSemester(
                        "semester1",
                        date,
                        date,
                        "Semester Name"
                ),
                new CourseViewCourse(
                        "id1",
                        "instructor1",
                        new HashSet<>(Set.of("Student3", "Student4")),
                        new HashSet<>(Set.of("Assignment3", "Assignment4")),
                        "semester1",
                        true,
                        "course1",
                        "description1"
                ),
                new CourseViewCourse(
                        "id2",
                        "instructor1",
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        true,
                        "course2",
                        "description2"
                )
        );
    }

    List<CourseViewEntity> getAdminViews(Date date) {
        return Arrays.asList(
                new CourseViewFaculty(
                        "instructor1",
                        "1234",
                        UserType.FACULTY.name(),
                        "ProfessorName",
                        "ProfessorLastName"
                ),
                new CourseViewSemester(
                        "semester1",
                        date,
                        date,
                        "Semester Name"
                ),
                new CourseViewCourse(
                        "id1",
                        "instructor1",
                        new HashSet<>(Set.of("Student3", "Student4")),
                        new HashSet<>(Set.of("Assignment3", "Assignment4")),
                        "semester1",
                        true,
                        "course1",
                        "description1"
                ),
                new CourseViewCourse(
                        "id2",
                        "instructor1",
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        true,
                        "course2",
                        "description2"
                ),
                CourseViewFaculty.getUnassignedView(),
                new CourseViewSemester(
                        "semester1",
                        date,
                        date,
                        "Semester Name"
                ),
                new CourseViewCourse(
                        "id3",
                        null,
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        false,
                        "course2",
                        "description2"
                )

        );
    }

    List<Course> getCourses() {
        return Arrays.asList(
                new Course(
                        "id1",
                        "instructor1",
                        new HashSet<>(Set.of("Student3", "Student4")),
                        new HashSet<>(Set.of("Assignment3", "Assignment4")),
                        "semester1",
                        true,
                        "course1",
                        "description1"
                ),
                new Course(
                        "id2",
                        "instructor1",
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        true,
                        "course2",
                        "description2"
                ),
                new Course(
                        "id3",
                        null,
                        new HashSet<>(Set.of("Student1", "Student2")),
                        new HashSet<>(Set.of("Assignment1", "Assignment2")),
                        "semester1",
                        false,
                        "course2",
                        "description2"
                )
        );
    }

    List<Semester> getSemesters(Date date) {
        return List.of(new Semester("semester1", date, date, "Semester Name"));
    }

    Optional<User> getStudent() {
        return Optional.of(new User("studentId", "1234", UserType.STUDENT.name(), "StudentName", "StudentLastName"));
    }

    Optional<User> getOptionalFaculty() {
        return Optional.of(getFaculty());
    }

    User getFaculty(){
        return new User("instructor1", "1234", UserType.FACULTY.name(), "ProfessorName", "ProfessorLastName");
    }

    Optional<User> getAdmin() {
        return Optional.of(new User("admin1", "1234", UserType.ADMIN.name(), "AdminName", "AdminLastName"));
    }

    @Test
    public void testGetStudentsByCourseName() throws Exception {
        // Given
        String courseName = "Intro to Testing";
        List<User> mockUsers = Arrays.asList(
                new User("user1", "password1", UserType.STUDENT.name(), "John", "Doe"),
                new User("user2", "password2", UserType.STUDENT.name(), "Jane", "Doe")
        );

        // When
        given(courseService.findStudentsByCourseName(courseName)).willReturn(mockUsers);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/{courseName}/students", courseName))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockUsers)))
                .andReturn();
    }
}
