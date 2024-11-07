package com.talniv.intuit.exceptions;

public class CsvParsingException extends RuntimeException {
    public CsvParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
