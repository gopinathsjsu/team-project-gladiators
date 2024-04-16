package org.example.cmpe202_final.model.user;

import org.example.cmpe202_final.controller.course.strategy.AdminCourseStrategy;
import org.example.cmpe202_final.controller.course.strategy.DefaultCourseStrategy;
import org.example.cmpe202_final.controller.course.strategy.FacultyCourseStrategy;
import org.example.cmpe202_final.controller.course.strategy.StudentCourseStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTypeTests {

    @Test
    public void testGetCourseStrategy() {
        // Test each enum value's course strategy
        assertEquals(new StudentCourseStrategy(), UserType.STUDENT.getCourseStrategy());
        assertEquals(new FacultyCourseStrategy(), UserType.FACULTY.getCourseStrategy());
        assertEquals(new AdminCourseStrategy(), UserType.ADMIN.getCourseStrategy());
        assertEquals(new DefaultCourseStrategy(), UserType.UNKNOWN.getCourseStrategy());
    }

    @Test
    public void testFindByKey() {
        // Test finding user type by key
        assertEquals(UserType.STUDENT, UserType.findByKey("STUDENT"));
        assertEquals(UserType.FACULTY, UserType.findByKey("faculty"));
        assertEquals(UserType.ADMIN, UserType.findByKey("AdMiN"));
        assertEquals(UserType.UNKNOWN, UserType.findByKey("unknown"));
        // Test unknown key
        assertEquals(UserType.UNKNOWN, UserType.findByKey("invalid"));
    }
}
