package com.rest_api.fs14backend.exceptions;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String message) {
        super(message);
    }
}
