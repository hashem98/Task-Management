package com.example.taskmanagement.controller;


import com.example.taskmanagement.bo.AuthenticationResponse;
import com.example.taskmanagement.bo.CODE;
import com.example.taskmanagement.bo.CreateAuthenticationRequest;
import com.example.taskmanagement.bo.Response;
import com.example.taskmanagement.service.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthenticationController {

    private final AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<AuthenticationResponse>> login(@RequestBody CreateAuthenticationRequest authenticationRequest) {

        return new ResponseEntity<>(Response.<AuthenticationResponse>builder()
                .data(authService.login(authenticationRequest))
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .success(true)
                .build(), HttpStatus.OK);
    }


}
