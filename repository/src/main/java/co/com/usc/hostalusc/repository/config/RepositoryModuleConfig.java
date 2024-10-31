package co.com.usc.hostalusc.repository.config;



import co.com.usc.hostalusc.repository.datasource.TenantAwareHikariDataSource;
import com.zaxxer.hikari.HikariDataSource;
import feign.Retryer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

@Configuration
@EnableFeignClients(basePackages = {"co.com.usc.hostalusc.repository"})
@ComponentScan("co.com.usc.hostalusc.repository")
@EnableTransactionManagement
@EntityScan("co.com.usc.hostalusc.repository")
@EnableJpaRepositories(
        basePackages = "co.com.usc.hostalusc.repository")
public class RepositoryModuleConfig {

    @Bean
    public Retryer retryer() {

        return new RetryConfig();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        HikariDataSource dataSource = new TenantAwareHikariDataSource();
        return DataSourceBuilder.derivedFrom(dataSource).build();
    }

}