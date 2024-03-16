package ru.otus.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private static final String SELECT = " select ";

    private static final String FROM = " from ";

    private static final String WHERE = " where ";

    private static final String INSERT = " insert into ";

    private static final String VALUES = " values ";

    private static final String UPDATE = " update ";

    private static final String SET = " set ";

    private static final String SPACE = " ";

    private static final String SEMICOLON = ";";

    private static final String EQUALS = "=";

    private static final String QUESTION = "?";

    private static final String LEFT_BRACKET = "(";

    private static final String RIGHT_BRACKET = ")";

    private EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        String allColumns = getAllColumns();

        String tableName = entityClassMetaData.getName();

        return SELECT + allColumns + FROM + tableName + SEMICOLON;
    }

    @Override
    public String getSelectByIdSql() {
        String allColumns = getAllColumns();

        String tableName = entityClassMetaData.getName();

        String fieldId = entityClassMetaData.getIdField().getName();

        return SELECT + allColumns + FROM + tableName + WHERE + fieldId + SPACE + EQUALS + SPACE + QUESTION + SEMICOLON;
    }

    @Override
    public String getInsertSql() {
        String allColumns = getAllColumns();

        String tableName = entityClassMetaData.getName();

        String values = entityClassMetaData.getAllFields().stream()
                .map(f -> QUESTION)
                .collect(Collectors.joining(","));

        return INSERT + tableName + LEFT_BRACKET + allColumns + RIGHT_BRACKET + VALUES + LEFT_BRACKET + values + RIGHT_BRACKET + SEMICOLON;
    }

    @Override
    public String getUpdateSql() {
        String allColumnsWithoutId = getAllColumnsWithoutId();
        String tableName = entityClassMetaData.getName();
        String fieldId = entityClassMetaData.getIdField().getName();

        return UPDATE + tableName + SET + allColumnsWithoutId + WHERE + fieldId + SPACE + EQUALS + SPACE + QUESTION + SEMICOLON;
    }

    private String getAllColumns() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

    private String getAllColumnsWithoutId() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(f -> f.getName() + SPACE + EQUALS + SPACE + QUESTION)
                .collect(Collectors.joining(","));
    }
}
