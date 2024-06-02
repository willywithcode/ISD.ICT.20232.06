package org.example.common.exception;

;

public class UnrecognizedException extends RuntimeException {
    public UnrecognizedException() {
        super("ERROR: Something went wrowng!");
    }
}
