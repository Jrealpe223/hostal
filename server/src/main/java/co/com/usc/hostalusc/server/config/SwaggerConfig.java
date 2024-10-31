package co.com.usc.hostalusc.server.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiDocs() {
        return GroupedOpenApi.builder()
                .group("usc hostal")
                .pathsToMatch("/v1/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("usc hostal")
                        .description("Microservicio encargado de gestionar la api de endpoints de usc")
                        .version("0.1")
                        .license(new License().name("License of API").url("API License URL")))
                        .externalDocs(new ExternalDocumentation()
                        .description("Terms of service")
                        .url("API License URL"));
    }

}
