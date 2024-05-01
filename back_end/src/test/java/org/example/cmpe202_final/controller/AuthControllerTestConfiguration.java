package org.example.cmpe202_final.controller;

import org.example.cmpe202_final.repository.user.UserRepository;
import org.example.cmpe202_final.service.user.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import static org.mockito.ArgumentMatchers.any;

@Configuration
@EnableWebSecurity
public class AuthControllerTestConfiguration {

    @MockBean
    UserRepository userRepository;

    @Bean
    public UserService userService(){
        return new MockUserService(userRepository);
    }
}
