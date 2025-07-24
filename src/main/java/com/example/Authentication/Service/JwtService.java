package com.example.Authentication.Service;

import com.example.Authentication.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
    String generateToken(UserDetails userDetails);
    String extractUsername(String token);
    boolean isvalidateToken(String token, UserDetails userDetails);


    String generateRefreshToken(HashMap<String , Object> extra, UserDetails userDetails);
}
