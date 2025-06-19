package com.example.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("Resource was not found.");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
