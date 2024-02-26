package org.frostyheco.exception;
//Unsupported exception occurs when session don't support some interface.
public class UnsupportedException extends RuntimeException {
    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedException(Throwable cause) {
        super(cause);
    }
}
