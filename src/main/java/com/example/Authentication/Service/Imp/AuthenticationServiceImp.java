package com.example.Authentication.Service.Imp;

import com.example.Authentication.Dto.JwtAuthenticationResponse;
import com.example.Authentication.Dto.RefreshTokenRequest;
import com.example.Authentication.Dto.SignInRequest;
import com.example.Authentication.Dto.SignUpRequest;
import com.example.Authentication.Entity.Role;
import com.example.Authentication.Entity.User;
import com.example.Authentication.Repository.UserRepository;
import com.example.Authentication.Service.AuthenticationService;
import com.example.Authentication.Service.JwtService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public User signUp(SignUpRequest request){
        User user =new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setSecondName(request.getSecondName());
        user.setRole(Role.USER);
        //user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);


    }



    public JwtAuthenticationResponse signIn(SignInRequest signInRequest){
authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
      var user=userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(()->new IllegalArgumentException("invailed email or password"));
var jwt=jwtService.generateToken(user);
var refreshToken=jwtService.generateRefreshToken(new HashMap<>(),user);
JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
jwtAuthenticationResponse.setToken(jwt);
jwtAuthenticationResponse.setRefreshtoken(refreshToken);
return jwtAuthenticationResponse;

    }
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest){
        String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
        User user=userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isvalidateToken(refreshTokenRequest.getToken(),user)){
            var jwt=jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse=new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshtoken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}
