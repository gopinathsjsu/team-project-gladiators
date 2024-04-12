package org.example.cmpe202_final.controller;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.Notification;
import org.example.cmpe202_final.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> fetchAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PostMapping
    public void insertNewNotification(Notification notification) {
        notificationService.insertNotification(notification);
    }
}