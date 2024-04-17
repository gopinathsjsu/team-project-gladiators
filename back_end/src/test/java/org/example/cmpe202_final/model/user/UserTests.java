package org.example.cmpe202_final.model.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {

    private final User user = new User();

    @Test
    public void testGettersAndSetters() {
        // Set up test data
        String id = "123";
        String password = "password123";
        String type = "STUDENT";
        String firstName = "John";
        String lastName = "Doe";

        // Set values using setters
        user.setId(id);
        user.setPassword(password);
        user.setType(type);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Test getters
        assertEquals(id, user.getId());
        assertEquals(password, user.getPassword());
        assertEquals(type, user.getType());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }
}
