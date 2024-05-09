package org.example.cmpe202_final.repository.announcement;

import org.example.cmpe202_final.model.announcement.Announcement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface AnnouncementRepository extends MongoRepository<Announcement, String> {
    Optional<ArrayList<Announcement>> findByCourseId(String courseId);
}
