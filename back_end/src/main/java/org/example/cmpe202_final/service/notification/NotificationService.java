package org.example.cmpe202_final.service.notification;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.notification.Notification;
import org.example.cmpe202_final.repository.notifications.NotificationRepository;
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