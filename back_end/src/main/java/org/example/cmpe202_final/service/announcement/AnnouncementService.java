package org.example.cmpe202_final.service.announcement;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.announcement.Announcement;
import org.example.cmpe202_final.repository.announcement.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AnnouncementService {
    @Autowired
    private AnnouncementRepository announcementRepository;

    public ArrayList<Announcement> getAnnouncementsByCourse(String courseId){
        Optional<ArrayList<Announcement>> optional =  announcementRepository.findByCourseId(courseId);
        return optional.orElseGet(ArrayList::new);
    }

    public Announcement saveAnnouncement(Announcement announcement){
        return announcementRepository.save(announcement);
    }
}
