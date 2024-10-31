package co.com.usc.hostalusc.test.runner;

import co.com.usc.hostalusc.test.model.SQLDatabaseScriptPOJO;
import co.com.usc.hostalusc.test.exception.DataBaseScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

@Component
@DependsOn({"postgresqlContainer"})
@SuppressWarnings("java:S3740")
public class ScriptRunner {

    PostgreSQLContainer sqlDatabaseContainer;

    @Autowired
    public ScriptRunner(PostgreSQLContainer sqlDatabaseContainer) {
        this.sqlDatabaseContainer = sqlDatabaseContainer;
    }

    public void executeScript(SQLDatabaseScriptPOJO SQLDatabaseScriptPOJO) {
        try {
            var jdbcDatabaseDelegate = new JdbcDatabaseDelegate(sqlDatabaseContainer, "");
            ScriptUtils.executeDatabaseScript(jdbcDatabaseDelegate, "scripts/drop_db.sql", buildRecreateDatabaseScript("public"));
            ScriptUtils.runInitScript(jdbcDatabaseDelegate, "scripts/schema.sql");
            ScriptUtils.runInitScript(jdbcDatabaseDelegate, SQLDatabaseScriptPOJO.getPath());
        } catch (Exception e) {
            throw new DataBaseScriptException("error running scripts", e);
        }

    }

    private String buildRecreateDatabaseScript(String databaseName) {
        var sb = new StringBuilder();
        sb.append("DROP SCHEMA IF EXISTS ").append(databaseName).append(" CASCADE;");
        sb.append("CREATE SCHEMA IF NOT EXISTS ").append(databaseName).append(";");
        return sb.toString();
    }


}
