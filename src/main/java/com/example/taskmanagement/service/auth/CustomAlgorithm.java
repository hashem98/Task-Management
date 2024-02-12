package com.example.taskmanagement.service.auth;

import io.jsonwebtoken.SignatureAlgorithm;

public class CustomAlgorithm {
    public static SignatureAlgorithm getAlgorithm() {
        return SignatureAlgorithm.HS512;
    }
}
