package ru.otus.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;

    private final EntityClassMetaData<T> entityClassMetaData;

    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entityClassMetaData = entityClassMetaData;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createObject(rs, entityClassMetaData.getConstructor());
                }

                return null;
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
                .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
                    var lists = new ArrayList<T>();

                    try {
                        while (rs.next()) {
                            lists.add(createObject(rs, entityClassMetaData.getConstructor()));
                        }

                        return lists;
                    } catch (Exception e) {
                        throw new DataTemplateException(e);
                    }
                }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            List<Object> values = entityClassMetaData.getFieldsWithoutId().stream()
                    .map(f -> getValue(f, client))
                    .toList();

            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            List<Object> values = entityClassMetaData.getAllFields().stream()
                    .map(f -> getValue(f, client))
                    .toList();

            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createObject(ResultSet rs, Constructor<T> constructor) throws Exception {
        Object[] args = new Object[constructor.getParameterCount()];

        for (int i = 1; i <= constructor.getParameterCount(); i++)
            args[i] = rs.getObject(i);

        return constructor.newInstance(args);
    }

    private Object getValue(Field f, T object) {
        try {
            return f.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
