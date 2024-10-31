package co.com.usc.hostalusc.domain.config;


import co.com.usc.hostalusc.repository.config.RepositoryModuleConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("co.com.usc.hostalusc.domain")
@Import({RepositoryModuleConfig.class})
public class DomainModuleConfig {


}