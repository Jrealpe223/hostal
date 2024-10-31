package co.com.usc.hostalusc.test.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jvillada
 */
public class SQLDatabaseScriptExceptionTest {

    @Test
    public void test_constructor() throws Exception {

        String message = "my_path";

        DataBaseScriptException dataBaseScriptException = new DataBaseScriptException(message);
        Assertions.assertEquals(dataBaseScriptException.getMessage(), message);
    }
    @Test
    public void test_with_exception() throws Exception {

        String message = "my_path";
        Exception exception = new Exception();

        DataBaseScriptException dataBaseScriptException = new DataBaseScriptException(message, exception);
        Assertions.assertEquals(dataBaseScriptException.getMessage(), message);
        Assertions.assertEquals(dataBaseScriptException.getCause(), exception);
    }
}