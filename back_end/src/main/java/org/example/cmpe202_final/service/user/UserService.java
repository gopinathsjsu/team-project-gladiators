package org.example.cmpe202_final.service.user;

import lombok.AllArgsConstructor;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.model.user.UserType;
import org.example.cmpe202_final.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository repository;

    public Optional<User> findById(String userId){
        return repository.findById(userId);
    }

    public List<User> findByType(UserType type){
        return repository.findByType(type);
    }

    public List<User> findAllUsers(){
        return repository.findAll();
    }

    public User findByEmail(String email){
        return repository.findByEmail(email);
    }

}
