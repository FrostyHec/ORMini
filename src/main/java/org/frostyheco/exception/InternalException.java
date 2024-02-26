package org.frostyheco.exception;
//Internal exceptions usually occurs when the program goes into an unexpected state.
public class InternalException extends RuntimeException {
    public InternalException(String s) {
        super(s);
    }

    public InternalException(String s, Throwable e) {
        super(s, e);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }
}
