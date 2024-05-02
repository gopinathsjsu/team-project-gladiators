package org.example.cmpe202_final.repository.user;

import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByType(UserType type);
    User findByEmail(String email);
    @Query("{'_id': {$in: ?0}, 'type': 'STUDENT'}")
    List<User> findStudentsByEnrolledIds(Set<String> studentIds);

}
