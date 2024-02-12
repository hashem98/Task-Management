package com.example.taskmanagement.exceptions;

public class BodyGuardException extends RuntimeException{
    public BodyGuardException() {
    }

    public BodyGuardException(Throwable cause) {
        super(cause);
    }


    public BodyGuardException(String str) {
        super(str);
    }

    public BodyGuardException(String message, Throwable cause) {
        super(message, cause);
    }
}
