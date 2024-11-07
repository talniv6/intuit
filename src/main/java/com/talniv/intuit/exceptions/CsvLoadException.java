package com.talniv.intuit.exceptions;

public class CsvLoadException extends RuntimeException {
    public CsvLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
