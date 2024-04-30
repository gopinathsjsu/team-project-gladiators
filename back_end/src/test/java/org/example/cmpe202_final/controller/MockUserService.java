package org.example.cmpe202_final.controller;

import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.example.cmpe202_final.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

public class MockUserService extends UserService {

    public MockUserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public List<User> findAllUsers() {
        User user = User.builder().email("napo92@hotmail.com").password("password{noop}").id("id").build();
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }
}
