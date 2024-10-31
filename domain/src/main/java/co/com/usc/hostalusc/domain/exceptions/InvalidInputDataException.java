package co.com.usc.hostalusc.domain.exceptions;

public class InvalidInputDataException extends RuntimeException {

  private static final String INVALID_INPUT_DATA = "Invalid input data. %s";

  public InvalidInputDataException(String message) {
    super(String.format(INVALID_INPUT_DATA, message));
  }
}
