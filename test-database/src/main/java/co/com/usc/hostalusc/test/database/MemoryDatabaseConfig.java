package co.com.usc.hostalusc.test.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;

import javax.sql.DataSource;


/**
 * @author jvillada
 */
@Configuration
@ComponentScan("co.com.usc.hostalusc.test")
public class MemoryDatabaseConfig {

    @Value("${testcontainers.postgresql.version}")
    String databaseVersion;

    @Value("${testcontainers.postgresql.database}")
    String database;

    @Value("${testcontainers.postgresql.user}")
    String username;

    @Value("${testcontainers.postgresql.password}")
    String password;

    @Bean(name = "dataSource")
    @DependsOn({"postgresqlContainer"})
    @SuppressWarnings("java:S3740")
    public DataSource createMemoryDatasource(PostgreSQLContainer postgreSQLContainer) {
        var config = new HikariConfig();
        config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        config.setDriverClassName(postgreSQLContainer.getDriverClassName());
        config.setUsername(postgreSQLContainer.getUsername());
        config.setPassword(postgreSQLContainer.getPassword());
        return new HikariDataSource(config);
    }

    @Bean(name = "postgresqlContainer")
    @SuppressWarnings({"squid:S2095", "java:S3740"})
    public PostgreSQLContainer embeddedCluster() {
        PostgreSQLContainer container = new PostgreSQLContainer(databaseVersion).withDatabaseName(database).withUsername(username)
                                                                                .withPassword(password);
        container.withInitScript("scripts/schema.sql");
        container.start();
        var jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, "scripts/data.sql");

        return container;
    }


}
