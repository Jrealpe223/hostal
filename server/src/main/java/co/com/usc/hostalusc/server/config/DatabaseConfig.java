package co.com.usc.hostalusc.server.config;


import co.com.usc.hostalusc.test.database.MemoryDatabaseConfig;
import org.springframework.context.annotation.*;

@Configuration
@Profile({"local"})
@Import({MemoryDatabaseConfig.class})
public class DatabaseConfig {


}
