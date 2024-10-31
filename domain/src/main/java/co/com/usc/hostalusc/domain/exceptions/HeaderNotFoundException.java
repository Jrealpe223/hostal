package co.com.usc.hostalusc.domain.exceptions;

public class HeaderNotFoundException extends RuntimeException {
    private static final String MESSAGE = "The header '%s' can't be null, empty or whitespaces only";

    public HeaderNotFoundException(String headerName) {
        super(String.format(MESSAGE, headerName));
    }
}
