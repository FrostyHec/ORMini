package org.frostyheco.exception;
//Operation exception occurs when there's an invalid request,such as calling a sql id that not defined in xml.
public class OperationException extends Exception{
    public OperationException(String message) {
        super(message);
    }
    public OperationException(String message, Throwable cause) {
        super(message, cause);
    }
    public OperationException(Throwable cause) {
        super(cause);
    }
}
