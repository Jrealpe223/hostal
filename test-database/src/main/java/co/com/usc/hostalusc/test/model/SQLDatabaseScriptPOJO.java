package co.com.usc.hostalusc.test.model;

import java.io.InputStreamReader;

/**
 * @author jvillada
 */
public class SQLDatabaseScriptPOJO {

    private String path;

    private InputStreamReader data;

    public SQLDatabaseScriptPOJO() {
    }

    public SQLDatabaseScriptPOJO(String path, InputStreamReader data) {
        this.path = path;
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public InputStreamReader getData() {
        return data;
    }

    public void setData(InputStreamReader data) {
        this.data = data;
    }
}
