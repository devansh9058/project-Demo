package com.example.Authentication.Dto;

import lombok.Data;

@Data
public class SignInRequest {
    private String email;
    private String password;

}
