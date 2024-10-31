package co.com.usc.hostalusc.test.model;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jvillada
 */
public class SQLDatabaseScriptPOJOTest {

    @Test
    public void test_constructor() throws Exception {

        String path = "my_path";
        InputStreamReader data = new InputStreamReader(new ByteArrayInputStream(path.getBytes()));

        SQLDatabaseScriptPOJO pojo = new SQLDatabaseScriptPOJO();
        pojo.setPath(path);
        pojo.setData(data);
        Assertions.assertEquals(pojo.getData(), data);
        Assertions.assertEquals(pojo.getPath(), path);

    }
}