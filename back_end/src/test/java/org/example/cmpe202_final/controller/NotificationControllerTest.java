package org.example.cmpe202_final.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.cmpe202_final.model.Notification;
import org.example.cmpe202_final.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Set up any necessary test data here
    }

    @Test
    void testFetchAllNotifications() throws Exception {
        // Given
        Notification notification1 = new Notification(
                "1",
                "CMPE 202",
                "Quiz 1",
                "1",
                LocalDateTime.now(),
                "ASSIGNMENT_CREATED"
        );
        Notification notification2 = new Notification(
                "2",
                "CMPE 202",
                "Quiz 2",
                "2",
                LocalDateTime.now(),
                "ASSIGNMENT_GRADED"
        );
        List<Notification> notifications = Arrays.asList(notification1, notification2);
        given(notificationService.getAllNotifications()).willReturn(notifications);

        // When & Then
        mockMvc.perform(get("/notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(notifications)));
    }

    @Test
    void testInsertNewNotification() throws Exception {
        // Given
        Notification notification = new Notification(
                "1",
                "CMPE 202",
                "Quiz 1",
                "1",
                LocalDateTime.now(),
                "ASSIGNMENT_CREATED"
        );
        String notificationJson = objectMapper.writeValueAsString(notification);

        // When & Then
        mockMvc.perform(post("/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notificationJson))
                .andExpect(status().isOk());
    }
}