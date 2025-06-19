package com.example.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException() {
        super("Resource was not found.");
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
