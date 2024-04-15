package org.example.cmpe202_final.repository.user;

import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByType(UserType type);
}
