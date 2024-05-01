package org.example.cmpe202_final.controller.auth;

import org.example.cmpe202_final.model.AuthResponse;
import org.example.cmpe202_final.model.user.User;
import org.example.cmpe202_final.service.auth.TokenService;
import org.example.cmpe202_final.service.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public AuthResponse token(Authentication authentication) {
        String token = tokenService.generateToken(authentication);
        User user = userService.findByEmail(authentication.getName());
        return new AuthResponse(
                user,
                token
        );
    }
}
