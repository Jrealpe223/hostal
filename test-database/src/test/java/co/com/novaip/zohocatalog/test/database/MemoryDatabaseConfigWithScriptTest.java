package co.com.usc.hostalusc.test.database;

import co.com.usc.hostalusc.test.annotations.SQLDatabaseScript;
import co.com.usc.hostalusc.test.extension.SQLDatabaseDatabaseExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;


import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({SpringExtension.class, SQLDatabaseDatabaseExtension.class})
@ContextConfiguration(classes = {MemoryDatabaseConfig.class})
@SQLDatabaseScript(fileName = "data.sql",forceReRun = true)
@TestPropertySource("classpath:application.properties")
public class MemoryDatabaseConfigWithScriptTest {


    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;

    @Autowired
    @Qualifier("postgresqlContainer")
    PostgreSQLContainer postgreSQLContainer;

    @Test
    public void testCreateMemoryDatasource() throws Exception {
        assertNotNull(dataSource);
        assertNotNull(postgreSQLContainer);
    }
}