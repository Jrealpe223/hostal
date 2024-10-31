package co.com.usc.hostalusc.test.exception;

/**
 * @author jvillada
 */
public class DataBaseScriptException extends RuntimeException {

    public DataBaseScriptException(String message) {
        super(message);
    }

    public DataBaseScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
