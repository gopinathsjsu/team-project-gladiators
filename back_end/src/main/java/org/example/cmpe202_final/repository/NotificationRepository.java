package org.example.cmpe202_final.repository;

import org.example.cmpe202_final.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findAllByOrderByDateDesc();

}