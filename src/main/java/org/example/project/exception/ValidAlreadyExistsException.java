package org.example.project.exception;

public class ValidAlreadyExistsException extends RuntimeException {
    public ValidAlreadyExistsException(String message) {
        super(message);
    }
}
