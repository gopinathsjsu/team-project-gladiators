package org.example.cmpe202_final.model.notification;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Notification {
    @Id
    private String id;
    private String courseName;
    private String eventTitle;
    private String eventId;
    private LocalDateTime date;
    private String type;

    public Notification(String courseName, String eventTitle, String eventId, LocalDateTime date, String type) {
        this.courseName = courseName;
        this.eventTitle = eventTitle;
        this.eventId = eventId;
        this.date = date;
        this.type = type;
    }
}