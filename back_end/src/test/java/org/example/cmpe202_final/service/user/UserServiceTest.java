package org.example.cmpe202_final.service.user;


import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindById() {
        // Mock repository behavior
        String userId = "123";
        User user = new User(userId, "password", "type", "John", "Doe");
        when(repository.findById(userId)).thenReturn(Optional.of(user));

        // Test service method
        Optional<User> result = userService.findById(userId);
        assertEquals(Optional.of(user), result);
    }

    @Test
    public void testFindByType() {
        // Mock repository behavior
        UserType type = UserType.STUDENT;
        User user1 = new User("1", "password", type.name(), "John", "Doe");
        User user2 = new User("2", "password", type.name(), "Jane", "Smith");
        List<User> users = Arrays.asList(user1, user2);
        when(repository.findByType(type)).thenReturn(users);

        // Test service method
        List<User> result = userService.findByType(type);
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
    }

    @Test
    public void testFindAllUsers() {
        // Mock repository behavior
        User user1 = new User("1", "password", "type", "John", "Doe");
        User user2 = new User("2", "password", "type", "Jane", "Smith");
        List<User> users = Arrays.asList(user1, user2);
        when(repository.findAll()).thenReturn(users);

        // Test service method
        List<User> result = userService.findAlUsers();
        assertEquals(users.size(), result.size());
        assertEquals(users, result);
    }
}
