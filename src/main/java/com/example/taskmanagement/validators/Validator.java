package com.example.taskmanagement.validators;

public interface Validator <P, R>{
    R validate(P input);
}
