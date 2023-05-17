package com.rest_api.fs14backend.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseUtils {
    /* Map<String, String> payload = Map.of("message", "Book not found");
            return new ResponseEntity<>(payload, HttpStatus.NOT_FOUND);

            */
    public static ResponseEntity<?> respMessageStatus(String message, HttpStatus status) {
        Map<String, String> payload = Map.of("message", message);
        return new ResponseEntity<>(payload, status);
    }
    public static ResponseEntity<?> respNotFound(String message) {
        return respMessageStatus(message, HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> respBadRequest(String message) {
        return respMessageStatus(message, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> respConflict(String message) {
        return respMessageStatus(message, HttpStatus.CONFLICT);
    }

}
