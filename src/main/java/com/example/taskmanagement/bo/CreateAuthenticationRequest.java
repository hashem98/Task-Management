package com.example.taskmanagement.bo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateAuthenticationRequest {

    private String username;

    private String password;
}
