package com.example.taskmanagement.service.auth;

import com.example.taskmanagement.bo.AuthenticationResponse;
import com.example.taskmanagement.bo.CreateAuthenticationRequest;
import com.example.taskmanagement.bo.CustomUserDetails;
import com.example.taskmanagement.exceptions.UserNotFoundException;
import com.example.taskmanagement.modle.UserEntity;
import com.example.taskmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           CustomUserDetailsService userDetailsService,
                           JWTUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public AuthenticationResponse login(CreateAuthenticationRequest authenticationRequest) {
        requiredNonNUll(authenticationRequest.getUsername(), "username");
        requiredNonNUll(authenticationRequest.getPassword(), "password");

        String username = authenticationRequest.getUsername().toLowerCase().trim();
        String password = authenticationRequest.getPassword();

        authenticate(username, password);
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String accessToken = jwtUtil.generateToken(userDetails);
        return AuthenticationResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .token("Bearer " + accessToken)
                .build();
    }


    private UserEntity getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No User Found with User ID: {" + userId + "}"));
    }


    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationServiceException e) {
            throw new UserNotFoundException("Incorrect Username");
        }
    }

    private void requiredNonNUll(Object obj, String name) {
        if (obj == null || obj.toString().isEmpty()) {
            throw new NullPointerException(name + " can't be empty!");
        }
    }
}
