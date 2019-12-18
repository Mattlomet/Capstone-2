package com.company.adminapi.exception;

public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException() {
    }
    public InvalidOrderException(String message) {
        super(message);
    }
}