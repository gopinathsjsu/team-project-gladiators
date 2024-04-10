package com.example.studentportal.notifications.mock

import com.example.studentportal.notifications.usecase.model.NotificationListUseCaseModel
import com.example.studentportal.notifications.usecase.model.NotificationUseCaseModel

object MockNotificationListDataSource {

    fun getMockNotificationList(): NotificationListUseCaseModel {
        return NotificationListUseCaseModel(
            listOf(
                NotificationUseCaseModel(id = "1", courseName = "CMPE 202", eventTitle = "Quiz 1",
                    eventId = "1", type = "ASSIGNMENT_CREATED"),
                NotificationUseCaseModel(id = "2", courseName = "CMPE 272", eventTitle = "Homework 2",
                    eventId = "2", type = "ASSIGNMENT_GRADED"),
                NotificationUseCaseModel(id = "3", courseName = "CMPE 255", eventTitle = "Change of classroom",
                    eventId = "3", type = "ANNOUNCEMENT_CREATED"),
                NotificationUseCaseModel(id = "1", courseName = "CMPE 202", eventTitle = "Quiz 1",
                    eventId = "1", type = "ASSIGNMENT_CREATED"),
                NotificationUseCaseModel(id = "2", courseName = "CMPE 272", eventTitle = "Homework 2",
                    eventId = "2", type = "ASSIGNMENT_GRADED"),
                NotificationUseCaseModel(id = "3", courseName = "CMPE 255", eventTitle = "Change of classroom",
                    eventId = "3", type = "ANNOUNCEMENT_CREATED"),
                NotificationUseCaseModel(id = "1", courseName = "CMPE 202", eventTitle = "Quiz 1",
                    eventId = "1", type = "ASSIGNMENT_CREATED"),
                NotificationUseCaseModel(id = "2", courseName = "CMPE 272", eventTitle = "Homework 2",
                    eventId = "2", type = "ASSIGNMENT_GRADED"),
                NotificationUseCaseModel(id = "3", courseName = "CMPE 255", eventTitle = "Change of classroom",
                    eventId = "3", type = "ANNOUNCEMENT_CREATED"),
                NotificationUseCaseModel(id = "1", courseName = "CMPE 202", eventTitle = "Quiz 1",
                    eventId = "1", type = "ASSIGNMENT_CREATED"),
                NotificationUseCaseModel(id = "2", courseName = "CMPE 272", eventTitle = "Homework 2",
                    eventId = "2", type = "ASSIGNMENT_GRADED"),
                NotificationUseCaseModel(id = "3", courseName = "CMPE 255", eventTitle = "Change of classroom",
                    eventId = "3", type = "ANNOUNCEMENT_CREATED"),
                NotificationUseCaseModel(id = "1", courseName = "CMPE 202", eventTitle = "Quiz 1",
                    eventId = "1", type = "ASSIGNMENT_CREATED"),
                NotificationUseCaseModel(id = "2", courseName = "CMPE 272", eventTitle = "Homework 2",
                    eventId = "2", type = "ASSIGNMENT_GRADED"),
                NotificationUseCaseModel(id = "3", courseName = "CMPE 255", eventTitle = "Change of classroom",
                    eventId = "3", type = "ANNOUNCEMENT_CREATED"),
                // Add more mock students as needed
            )
        )
    }
}