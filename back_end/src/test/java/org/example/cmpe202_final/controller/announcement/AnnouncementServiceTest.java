package org.example.cmpe202_final.controller.announcement;

import org.example.cmpe202_final.model.announcement.Announcement;
import org.example.cmpe202_final.repository.announcement.AnnouncementRepository;
import org.example.cmpe202_final.service.announcement.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTest {

    @Mock
    private AnnouncementRepository announcementRepository;

    @InjectMocks
    private AnnouncementService announcementService;

    @Test
    public void testGetAnnouncementsByCourse() {
        // Prepare test data
        String courseId = "123";
        ArrayList<Announcement> expectedAnnouncements = new ArrayList<>();
        // Mock repository behavior
        when(announcementRepository.findByCourseId(courseId)).thenReturn(Optional.of(expectedAnnouncements));

        // Call service method
        ArrayList<Announcement> result = announcementService.getAnnouncementsByCourse(courseId);

        // Verify repository method is called
        verify(announcementRepository, times(1)).findByCourseId(courseId);
        // Verify result
        assertEquals(expectedAnnouncements, result);
    }

    @Test
    public void testSaveAnnouncement() {
        // Prepare test data
        Announcement announcementToSave = new Announcement(/* provide necessary constructor arguments */);
        Announcement savedAnnouncement = new Announcement(/* provide necessary constructor arguments */);
        // Mock repository behavior
        when(announcementRepository.save(announcementToSave)).thenReturn(savedAnnouncement);

        // Call service method
        Announcement result = announcementService.saveAnnouncement(announcementToSave);

        // Verify repository method is called
        verify(announcementRepository, times(1)).save(announcementToSave);
        // Verify result
        assertEquals(savedAnnouncement, result);
    }
}
