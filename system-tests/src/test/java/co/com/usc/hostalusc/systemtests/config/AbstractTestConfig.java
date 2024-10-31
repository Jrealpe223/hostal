package co.com.usc.hostalusc.systemtests.config;

import co.com.usc.hostalusc.test.database.MemoryDatabaseConfig;
import co.com.usc.hostalusc.server.uschostaluscServerApplication;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("systemtest")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = uschostaluscServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
@AutoConfigureWireMock(port = 0, stubs = "classpath:/mappings/*.json")
@ContextConfiguration(classes = {SystemTestsConfig.class, MemoryDatabaseConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/database/data.sql")
public abstract class AbstractTestConfig {

    @LocalServerPort
    protected int port;

    @BeforeAll
    public void setUp() {
        RestAssured.port = port;
    }

}
