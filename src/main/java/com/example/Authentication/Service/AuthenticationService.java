package com.example.Authentication.Service;

import com.example.Authentication.Dto.JwtAuthenticationResponse;
import com.example.Authentication.Dto.RefreshTokenRequest;
import com.example.Authentication.Dto.SignInRequest;
import com.example.Authentication.Dto.SignUpRequest;
import com.example.Authentication.Entity.User;

public interface AuthenticationService {
    User signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
