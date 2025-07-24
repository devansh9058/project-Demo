package com.example.Authentication.Service.Imp;

import com.example.Authentication.Repository.UserRepository;
import com.example.Authentication.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

@Override
    public  UserDetailsService userDetailsService(){
        return new UserDetailsService() {


            @Override
            public UserDetails loadUserByUsername(String username)  {

                return userRepository.findByEmail(username)
                        .orElseThrow(()->new  UsernameNotFoundException("User not found"));
            }



        };
    }
}
