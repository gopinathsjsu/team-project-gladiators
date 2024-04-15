package org.example.cmpe202_final.model.user;

import org.example.cmpe202_final.controller.course.strategy.*;

public enum UserType {
    STUDENT(new StudentCourseStrategy()),
    FACULTY(new FacultyCourseStrategy()),
    ADMIN(new AdminCourseStrategy()),
    UNKNOWN(new DefaultCourseStrategy());

    private final CourseStrategy courseStrategy;
    UserType(CourseStrategy courseStrategy) {
        this.courseStrategy = courseStrategy;
    }

    public CourseStrategy getCourseStrategy() {
        return courseStrategy;
    }

    static UserType findByKey(String key) {
        for (UserType userType : UserType.values()) {
            if (key.equalsIgnoreCase(userType.name())) {
                return userType;
            }
        }
        return UNKNOWN;
    }
}
