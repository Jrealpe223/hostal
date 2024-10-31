package co.com.usc.hostalusc.test.reader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jvillada
 */
public class SQLDatabaseScriptReaderTest {

    @Test
    public void test_constructor() throws Exception {
        Constructor<SQLDatabaseScriptReader> constructor = SQLDatabaseScriptReader.class.getDeclaredConstructor();
        Assertions.assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }

}