package org.example.cmpe202_final.model;

import lombok.Getter;
import lombok.Setter;
import org.example.cmpe202_final.model.user.User;

@Setter
@Getter
public class AuthResponse {
   private User user;
   private String jwtToken;

    public AuthResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

}
