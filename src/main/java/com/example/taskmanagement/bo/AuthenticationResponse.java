package com.example.taskmanagement.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private Long id;

    private String username;

    private String token;

}
