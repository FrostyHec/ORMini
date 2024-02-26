package org.frostyheco.exception;
//Invalid Exception won't be shown outside, it's an inside problem that need to be analyzed by callers.
public class InvalidException extends Exception{
    public InvalidException(String s) {
        super(s);
    }

    public InvalidException(String s, Throwable e) {
        super(s, e);
    }

    public InvalidException(Throwable cause) {
        super(cause);
    }
}
