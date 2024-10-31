package co.com.usc.hostalusc.server;

import co.com.usc.hostalusc.api.config.ApiModuleConfig;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({ApiModuleConfig.class})
public class UscHostaluscServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UscHostaluscServerApplication.class, args);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
