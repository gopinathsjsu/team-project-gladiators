package org.example.cmpe202_final.service;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.Notification;
import org.example.cmpe202_final.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository repository;
    public List<Notification> getAllNotifications() {
        return repository.findAllByOrderByDateDesc();
    }

    public void insertNotification(Notification notification) {
        repository.save(notification);
    }
}
