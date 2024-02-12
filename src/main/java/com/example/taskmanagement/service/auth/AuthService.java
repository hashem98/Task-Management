package com.example.taskmanagement.service.auth;


import com.example.taskmanagement.bo.AuthenticationResponse;
import com.example.taskmanagement.bo.CreateAuthenticationRequest;

public interface AuthService {
    AuthenticationResponse login(CreateAuthenticationRequest authenticationRequest);

}
