package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTests {

    public static class TestableUser extends User {
        public TestableUser(int id, String name, String email) {
            super(id, name, email);
        }
    }

    @Test
    public void testUserConstructor() {
        TestableUser user = new TestableUser(1, "tester", "testuser@example.com");
        assertEquals(1, user.getId());
        assertEquals("tester", user.getName());
        assertEquals("testuser@example.com", user.getEmail());

    }

    @Test
    public void testSetAndGetId() {
        TestableUser user = new TestableUser(1, "tester", "testuser@example.com");
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testSetAndGetName() {
        TestableUser user = new TestableUser(1, "tester", "testuser@example.com");
        user.setName("tester");
        assertEquals("tester", user.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        TestableUser user = new TestableUser(1, "tester", "testuser@example.com");
        user.setName("testuser@example.com");
        assertEquals("testuser@example.com", user.getEmail());
    }
}