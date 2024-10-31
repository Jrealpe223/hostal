package co.com.usc.hostalusc.domain.exceptions;

public class UscException extends RuntimeException {

    public UscException(String message) {
        super(message);
    }

    public UscException(String message, Throwable cause) {
        super(message, cause);
    }
}
