package ru.otus.mapper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Testcontainers
public class DataTemplateJdbcTest {

    private HikariDataSource dataSource;

    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12-alpine")
            .withDatabaseName("testDataBase")
            .withUsername("owner")
            .withPassword("secret")
            .withClasspathResourceMapping(
                    "00_createTables.sql", "/docker-entrypoint-initdb.d/00_createTables.sql", BindMode.READ_ONLY)
            .withClasspathResourceMapping(
                    "01_insertData.sql", "/docker-entrypoint-initdb/01_insertData.sql", BindMode.READ_ONLY);


    private Properties getConnectionProperties() {
        Properties props = new Properties();

        props.setProperty("user", postgreSQLContainer.getUsername());
        props.setProperty("password", postgreSQLContainer.getPassword());
        props.setProperty("ssl", "false");

        return props;
    }

    private void makeConnectionPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(postgreSQLContainer.getJdbcUrl());
        config.setConnectionTimeout(3000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(600000);
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.setDataSourceProperties(getConnectionProperties());

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private Connection getConnection(boolean usePool) throws SQLException {
        return dataSource.getConnection();
    }
}
