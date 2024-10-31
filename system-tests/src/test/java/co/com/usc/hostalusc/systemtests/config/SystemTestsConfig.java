package co.com.usc.hostalusc.systemtests.config;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SystemTestsConfig {

    @Value("${wiremock.server.port}")
    private Integer wireMockPort;


}
