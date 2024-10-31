package co.com.usc.hostalusc.api.config;

import co.com.usc.hostalusc.domain.config.DomainModuleConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("co.com.usc.hostalusc.api")
@Import({DomainModuleConfig.class})
public class ApiModuleConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}