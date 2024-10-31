package co.com.usc.hostalusc.server.config;


import co.com.usc.hostalusc.api.config.ApiModuleConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ApiModuleConfig.class})
public class ServerConfig {

}