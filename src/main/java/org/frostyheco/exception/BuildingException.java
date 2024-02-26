package org.frostyheco.exception;
//Building Exceptions occurs when the xml has wrongs, if the yml setting has wrong, a runtime exception will be thrown with building exception.
public class BuildingException extends Exception{
    public BuildingException(String message) {
        super(message);
    }
    public BuildingException(String message, Throwable cause) {
        super(message, cause);
    }
    public BuildingException(Throwable cause) {
        super(cause);
    }

}
