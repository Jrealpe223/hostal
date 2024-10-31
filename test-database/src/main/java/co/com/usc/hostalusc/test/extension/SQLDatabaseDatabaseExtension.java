package co.com.usc.hostalusc.test.extension;

import co.com.usc.hostalusc.test.annotations.SQLDatabaseScript;
import co.com.usc.hostalusc.test.runner.ScriptRunner;
import co.com.usc.hostalusc.test.model.SQLDatabaseScriptPOJO;
import co.com.usc.hostalusc.test.reader.SQLDatabaseScriptReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author jvillada
 */
public class SQLDatabaseDatabaseExtension implements SQLDatabaseExtensionI {

    private  static final Logger LOGGER = LoggerFactory.getLogger(SQLDatabaseDatabaseExtension.class);


    private String lastScript;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        processScript(context);
    }


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        processScript(context);
    }

    private void processScript(ExtensionContext context) {
        var springContext = SpringExtension.getApplicationContext(context);

        var scriptRunner = springContext.getBean(ScriptRunner.class);

        Optional<SQLDatabaseScript> sqlDatabaseScript = getSQLDatabaseScript(context);

        Function<SQLDatabaseScript, SQLDatabaseScriptPOJO> fx = (SQLDatabaseScript sqlDatabaseScript1) -> {

            SQLDatabaseScriptPOJO sqlDatabaseScriptPOJO = null;
            String resource = SQLDatabaseScriptReader.getSQLDatabaseScriptLocation(sqlDatabaseScript1);

            try (InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(resource);
                 var inputStreamReader = new InputStreamReader(systemResourceAsStream, StandardCharsets.UTF_8)) {

                sqlDatabaseScriptPOJO = new SQLDatabaseScriptPOJO(resource, inputStreamReader);

            } catch (Exception e) {
                LOGGER.error("Unable to read sqldatabase script {} ", resource);
            }
            return sqlDatabaseScriptPOJO;
        };

        sqlDatabaseScript
                .filter(script -> script.forceReRun() || !script.fileName().equals(lastScript))
                .map(script -> {
                    lastScript = script.fileName();
                    return script;
                })
                .map(fx::apply)
                .ifPresent(scriptRunner::executeScript);
    }
}
