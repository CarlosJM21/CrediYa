package co.com.mrcompany.r2dbc.config;

import co.com.mrcompany.r2dbc.config.converters.ReadConverterUUID;
import co.com.mrcompany.r2dbc.config.converters.WriterConverterUUID;
import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.mariadb.r2dbc.SslMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.MySqlDialect;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class MySQLConnectionPool {
    public static final int INITIAL_SIZE = 12;
    public static final int MAX_SIZE = 15;
    public static final int MAX_IDLE_TIME = 30;

    @Bean
    public ConnectionPool getConnectionConfig(MySqlConnectionProperties properties) {
        MariadbConnectionConfiguration dbConfiguration = MariadbConnectionConfiguration.builder()
                .host(properties.host())
                .port(properties.port())
                .database(properties.database())
                .username(properties.username())
                .password(properties.password())
                .sslMode(SslMode.DISABLE)
                .allowPublicKeyRetrieval(true)
                .build();

        ConnectionFactory connectionFactory = new MariadbConnectionFactory(dbConfiguration);

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .name("api-mariadb-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

        return new ConnectionPool(poolConfiguration);
    }

    @Bean
    R2dbcCustomConversions customConversions()
    {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new ReadConverterUUID());
        converters.add(new WriterConverterUUID());
        return R2dbcCustomConversions.of(MySqlDialect.INSTANCE, converters);
    }
}