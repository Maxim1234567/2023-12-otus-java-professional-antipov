package ru.otus.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.TestClassMetadata;

import static org.assertj.core.api.Assertions.assertThat;

public class EntitySQLMetaDataTest {

    private EntityClassMetaData<TestClassMetadata> entityClassMetaData;

    private EntitySQLMetaData entitySQLMetaData;

    @BeforeEach
    public void setUp() {
        entityClassMetaData = new EntityClassMetaDataImpl<>(TestClassMetadata.class);
        entitySQLMetaData = new EntitySQLMetaDataImpl(entityClassMetaData);
    }

    @Test
    public void shouldCorrectReturnSelectAll() {
        String select = " select fieldId,field1,field2 from testClassMetadata;";

        String result = entitySQLMetaData.getSelectAllSql();

        assertThat(result).isNotNull()
                .isEqualTo(select);
    }

    @Test
    public void shouldCorrectReturnSelectById() {
        String select = " select fieldId,field1,field2 from testClassMetadata where fieldId = ?;";

        String result = entitySQLMetaData.getSelectByIdSql();

        assertThat(result).isNotNull()
                .isEqualTo(select);
    }

    @Test
    public void shouldCorrectReturnInsert() {
        String insert = " insert into testClassMetadata(fieldId,field1,field2) values (?,?,?);";

        String result = entitySQLMetaData.getInsertSql();

        assertThat(result).isNotNull()
                .isEqualTo(insert);
    }

    @Test
    public void shouldCorrectReturnUpdate() {
        String update = " update testClassMetadata set field1 = ?,field2 = ? where fieldId = ?;";

        String result = entitySQLMetaData.getUpdateSql();

        assertThat(result).isNotNull()
                .isEqualTo(update);
    }

}
