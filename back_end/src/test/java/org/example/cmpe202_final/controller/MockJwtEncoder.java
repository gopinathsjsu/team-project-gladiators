package org.example.cmpe202_final.controller;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;

public class MockJwtEncoder implements JwtEncoder {

    @Override
    public Jwt encode(JwtEncoderParameters parameters) throws JwtEncodingException {
        return null;
    }
}
