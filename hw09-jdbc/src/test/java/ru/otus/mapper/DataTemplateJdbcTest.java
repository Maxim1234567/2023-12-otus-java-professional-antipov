package ru.otus.mapper;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.otus.TestClassMetadata;
import ru.otus.core.repository.executor.DbExecutorImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class DataTemplateJdbcTest {

    private HikariDataSource dataSource;

    private DataTemplateJdbc<TestClassMetadata> dataTemplateJdbc;

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testDataBase")
            .withUsername("owner")
            .withPassword("secret")
            .withClasspathResourceMapping(
                    "00_createTables.sql", "/docker-entrypoint-initdb.d/00_createTables.sql", BindMode.READ_ONLY)
            .withClasspathResourceMapping(
                    "01_insertData.sql", "/docker-entrypoint-initdb.d/01_insertData.sql", BindMode.READ_ONLY);

    @BeforeEach
    public void setUp() {
        makeConnectionPool();

        var dbExecutor = new DbExecutorImpl();
        var entityClassMetaDataClient = new EntityClassMetaDataImpl<>(TestClassMetadata.class);
        var entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        dataTemplateJdbc = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient);
    }

    @Test
    public void shouldCorrectReturnAllElements() throws SQLException {
        List<TestClassMetadata> elements = List.of(
                new TestClassMetadata(1L, "field1_1", "field2_1"),
                new TestClassMetadata(2L, "field1_2", "field2_2"),
                new TestClassMetadata(3L, "field1_3", "field2_3"),
                new TestClassMetadata(4L, "field1_4", "field2_4")
        );

        List<TestClassMetadata> result = dataTemplateJdbc.findAll(getConnection());

        assertThat(result).isNotNull()
                .hasSize(4)
                .isEqualTo(elements);
    }

    @Test
    public void shouldCorrectReturnElementById() throws SQLException {
        TestClassMetadata element = new TestClassMetadata(3L, "field1_3", "field2_3");

        TestClassMetadata result = dataTemplateJdbc.findById(getConnection(), 3L).get();

        assertThat(result).isNotNull()
                .isEqualTo(element);
    }

    @Test
    public void shouldCorrectInsertElement() throws SQLException {
        TestClassMetadata save = new TestClassMetadata(null,"field1_5", "field2_5");

        long id = dataTemplateJdbc.insert(getConnection(), save);

        TestClassMetadata element = new TestClassMetadata(id,"field1_5", "field2_5");

        TestClassMetadata result = dataTemplateJdbc.findById(getConnection(), id).get();

        assertThat(result).isNotNull()
                .isEqualTo(element);
    }

    @Test
    public void shouldCorrectUpdateElement() throws SQLException {
        TestClassMetadata update = new TestClassMetadata(4L, "field1_6", "field2_6");

        dataTemplateJdbc.update(getConnection(), update);

        TestClassMetadata result = dataTemplateJdbc.findById(getConnection(), 4L).get();

        assertThat(result).isNotNull()
                .isEqualTo(update);
    }


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
        config.setAutoCommit(true);
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

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
