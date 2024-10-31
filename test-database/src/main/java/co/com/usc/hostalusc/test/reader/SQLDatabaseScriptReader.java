package co.com.usc.hostalusc.test.reader;

import co.com.usc.hostalusc.test.annotations.SQLDatabaseScript;

public class SQLDatabaseScriptReader {
    private SQLDatabaseScriptReader() {
    }

    public static String getSQLDatabaseScriptLocation(SQLDatabaseScript sqlDatabaseScript) {
        return sqlDatabaseScript.basePath() + sqlDatabaseScript.fileName();
    }
}
