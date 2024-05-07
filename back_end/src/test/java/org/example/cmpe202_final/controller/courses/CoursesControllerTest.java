package org.example.cmpe202_final.controller.courses;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cmpe202_final.controller.AuthControllerTestConfiguration;
import org.example.cmpe202_final.controller.MockSecurityConfiguration;
import org.example.cmpe202_final.controller.auth.AuthController;
import org.example.cmpe202_final.controller.course.CourseController;
import org.example.cmpe202_final.model.course.Course;
import org.example.cmpe202_final.model.course.Semester;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.service.auth.TokenService;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest({CourseController.class, AuthController.class})
@ActiveProfiles("test")
@Import({MockSecurityConfiguration.class, TokenService.class, AuthControllerTestConfiguration.class})
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
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
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
                .andExpect(status().isOk())
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
        given(userService.findByType(UserType.FACULTY)).willReturn(new ArrayList<>(List.of(getFaculty())));
        given(userService.findById("admin1")).willReturn(getAdmin());

        // Test Response without userId
        mockMvc.perform(MockMvcRequestBuilders.get("/courses?userId=admin1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(getAdminViews(mockDate))))
                .andReturn();
    }

    @Test
    public void testCreateNewCourse() throws Exception {
        // Create mocks for the services
        UserService userService = mock(UserService.class);
        SemesterService semesterService = mock(SemesterService.class);
        CourseService courseService = mock(CourseService.class);

        // Create the controller instance with mocks injected
        CourseController controller = new CourseController(userService, semesterService, courseService);

        // Mock the behavior of UserType.ADMIN.getCourseStrategy().getCourseViews()
        ArrayList<CourseViewEntity> expectedCourseViews = new ArrayList<>();
        when(UserType.ADMIN.getCourseStrategy().getCourseViews(
                courseService,
                semesterService,
                userService,
                ""
        )).thenReturn(expectedCourseViews);

        // Set up MockMvc for controller testing
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform a POST request to create a course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"courseId\":\"123\", \"courseName\":\"Example Course\"}"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$").isArray()) // Expect response to be an array
                .andExpect(jsonPath("$").isEmpty()); // Expect response array to be empty

        // Verify that courseService.addItem(course) was called once
        verify(courseService, times(1)).addItem(any());

    }

    @Test
    public void testUpdateCourse() throws Exception {
        // Create mocks for the services
        UserService userService = mock(UserService.class);
        SemesterService semesterService = mock(SemesterService.class);
        CourseService courseService = mock(CourseService.class);

        // Create the controller instance with mocks injected
        CourseController controller = new CourseController(userService, semesterService, courseService);

        // Create a sample course object
        Course sampleCourse = new Course("123", "InstructorId", new HashSet<>(), new HashSet<>(), "Semester", true, "Example Course", "Description");

        // Mock the behavior of courseService.addItem(course) to return the updated course
        when(courseService.addItem(any())).thenReturn(sampleCourse);

        // Set up MockMvc for controller testing
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform a POST request to update the course
        mockMvc.perform(MockMvcRequestBuilders.post("/courses/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"123\", \"instructor\":\"NewInstructorId\", \"enrolledStudents\":[], \"assignments\":[], \"semester\":\"NewSemester\", \"published\":true, \"name\":\"Updated Course\", \"description\":\"New Description\"}"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.course.id").value("123")); // Expect courseId to be "123"// Expect instructor to be "NewInstructorId"

        // Verify that courseService.addItem(course) was called once
        verify(courseService, times(1)).addItem(any());
    }

    @Test
    public void testGetCourseInputData() throws Exception {
        // Create mocks for the services
        UserService userService = mock(UserService.class);
        SemesterService semesterService = mock(SemesterService.class);
        CourseService courseService = mock(CourseService.class);

        // Create the controller instance with mocks injected
        CourseController controller = new CourseController(userService, semesterService, courseService);

        // Create sample data for semesters and users
        List<Semester> sampleSemesters = Arrays.asList(new Semester(/* provide necessary constructor arguments */));
        List<User> sampleUsers = Arrays.asList(new User(/* provide necessary constructor arguments */));

        // Mock the behavior of semesterService.findAllSemesters() and userService.findByType(UserType.FACULTY)
        when(semesterService.findAllSemesters()).thenReturn(sampleSemesters);
        when(userService.findByType(UserType.FACULTY)).thenReturn(sampleUsers);

        // Set up MockMvc for controller testing
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform a GET request to get course input data
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/input"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.semesters").isArray()) // Expect semesters array
                .andExpect(jsonPath("$.semesters[0]").exists()) // Expect at least one semester
                .andExpect(jsonPath("$.users").isArray()) // Expect users array
                .andExpect(jsonPath("$.users[0]").exists()); // Expect at least one user

        // Verify that semesterService.findAllSemesters() and userService.findByType(UserType.FACULTY) were called once
        verify(semesterService, times(1)).findAllSemesters();
        verify(userService, times(1)).findByType(UserType.FACULTY);
    }

    @Test
    public void testGetCourseDetails() throws Exception {
        // Create mocks for the services
        UserService userService = mock(UserService.class);
        SemesterService semesterService = mock(SemesterService.class);
        CourseService courseService = mock(CourseService.class);

        // Create the controller instance with mocks injected
        CourseController controller = new CourseController(userService, semesterService, courseService);

        // Create a sample course object
        Course sampleCourse = new Course("123", "InstructorId", new HashSet<>(), new HashSet<>(), "Semester", true, "Example Course", "Description");

        // Create a sample instructor object
        User sampleInstructor = User.builder()
                .id("InstructorId")
                .password("password")
                .type("type")
                .firstName("John")
                .lastName("Doe")
                .biography("Biography")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();

        // Mock the behavior of courseService.findById(courseId) and userService.findById(instructorId)
        when(courseService.findById("123")).thenReturn(Optional.of(sampleCourse));
        when(userService.findById("InstructorId")).thenReturn(Optional.of(sampleInstructor));

        // Set up MockMvc for controller testing
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // Perform a GET request to get course details
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/123/details"))
                .andExpect(status().isOk()) // Expect HTTP status 200
                .andExpect(jsonPath("$.course.id").value("123")) // Expect courseId to be "123"
                .andExpect(jsonPath("$.course.name").value("Example Course")) // Expect courseName to be "Example Course"
                .andExpect(jsonPath("$.instructor.id").value("InstructorId")) // Expect instructorId to be "InstructorId"
                .andExpect(jsonPath("$.instructor.firstName").value("John")) // Expect firstName to be "John"
                .andExpect(jsonPath("$.instructor.lastName").value("Doe")); // Expect lastName to be "Doe"

        // Verify that courseService.findById(courseId) and userService.findById(instructorId) were called once
        verify(courseService, times(1)).findById("123");
        verify(userService, times(1)).findById("InstructorId");
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
        return Optional.of(new User("studentId", "1234", UserType.STUDENT.name(), "StudentName", "StudentLastName", "biography", "email", "phone"));
    }

    Optional<User> getOptionalFaculty() {
        return Optional.of(getFaculty());
    }

    User getFaculty(){
        return new User("instructor1", "1234", UserType.FACULTY.name(), "ProfessorName", "ProfessorLastName", "biography", "email", "phone");
    }

    Optional<User> getAdmin() {
        return Optional.of(new User("admin1", "1234", UserType.ADMIN.name(), "AdminName", "AdminLastName", "biography", "email", "phone"));
    }

    @Test
    public void testGetStudentsByCourseName() throws Exception {
        // Given
        String courseId = "CMPE_287";
        List<User> mockUsers = Arrays.asList(
                new User("user1", "password1", UserType.STUDENT.name(), "John", "Doe", null, null, null),
                new User("user2", "password2", UserType.STUDENT.name(), "Jane", "Doe", null, null, null)
        );

        // When
        given(courseService.findStudentsByCourseId(courseId)).willReturn(mockUsers);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/{courseId}/students", courseId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(mockUsers)))
                .andReturn();
    }

}
