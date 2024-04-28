package org.example.cmpe202_final.controller.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cmpe202_final.controller.course.CourseController;
import org.example.cmpe202_final.model.assignment.Assignment;
import org.example.cmpe202_final.model.notification.Notification;
import org.example.cmpe202_final.service.assignment.AssignmentService;
import org.example.cmpe202_final.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(AssignmentController.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AssignmentService assignmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Set up any necessary test data here
    }

    @Test
    void testFetchAssignments() throws Exception {
        // Given
        Assignment assignment1 = new Assignment("id", new Date(), "Name", new ArrayList<>(), "Course", "Link");
        Assignment assignment2 = new Assignment("id", new Date(), "Name2", new ArrayList<>(), "Course", "Link");
        ArrayList<Assignment> assignments = new ArrayList<>(Arrays.asList(assignment1, assignment2));
        given(assignmentService.findByCourse("course")).willReturn(Optional.of(assignments));

        // When & Then
        mockMvc.perform(get("/assignments/course").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(assignments)));
    }

    @Test
    void testCreateAssignment() throws Exception {
        // Given
        Assignment assignment1 = new Assignment("id", new Date(), "Name", new ArrayList<>(), "Course", "Link");
        Assignment assignment2 = new Assignment("id", new Date(), "Name2", new ArrayList<>(), "Course", "Link");
        ArrayList<Assignment> assignments = new ArrayList<>(Arrays.asList(assignment1, assignment2));
        given(assignmentService.addItem(assignment2)).willReturn(assignment2);

        // When & Then
        mockMvc.perform(post("/assignments").contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testAddAssignment() throws Exception {
        // Mock Assignment data
        Assignment assignment = new Assignment();
        assignment.setCourse("Math");
        assignment.setName("Assignment 1");

        // Mock the service behavior
        when(assignmentService.addItem(any(Assignment.class))).thenReturn(assignment);

        // Perform POST request
        mockMvc.perform(post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"course\":\"Math\",\"title\":\"Assignment 1\"}"))
                .andExpect(status().isOk());

        // Verify that addItem method was called with the correct assignment object
        verify(assignmentService).addItem(any(Assignment.class));
    }
}
