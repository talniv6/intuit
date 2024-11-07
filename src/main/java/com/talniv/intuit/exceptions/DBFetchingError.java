package com.talniv.intuit.exceptions;

public class DBFetchingError extends RuntimeException {
    public DBFetchingError(String message, Throwable cause) {
        super(message, cause);
    }
}
