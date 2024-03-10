package org.example.cmpe202_final;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AdminTests {

    @Test
    public void testAdminConstructor() {
        Admin admin = new Admin(1, "John Doe", "johndoe@example.com");
        assertEquals(1, admin.getId());
        assertEquals("John Doe", admin.getName());
        assertEquals("johndoe@example.com", admin.getEmail());
    }

    @Test
    public void testSetAndGetId() {
        Admin admin = new Admin(1, "John Doe", "johndoe@example.com");
        admin.setId(1);
        assertEquals(1, admin.getId());
    }

    @Test
    public void testSetAndGetName() {
        Admin admin = new Admin(1, "John Doe", "johndoe@example.com");
        admin.setName("Jane Doe");
        assertEquals("Jane Doe", admin.getName());
    }

    @Test
    public void testSetAndGetEmail() {
        Admin admin = new Admin(1, "John Doe", "johndoe@example.com");
        admin.setEmail("janedoe@example.com");
        assertEquals("janedoe@example.com", admin.getEmail());
    }
}