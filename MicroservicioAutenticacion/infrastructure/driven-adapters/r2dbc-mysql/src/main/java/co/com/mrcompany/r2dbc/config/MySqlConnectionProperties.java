package co.com.mrcompany.r2dbc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@ConfigurationProperties(prefix = "spring.r2dbc")
public record MySqlConnectionProperties(
        String host,
        Integer port,
        String database,
        String username,
        String password) {
}
