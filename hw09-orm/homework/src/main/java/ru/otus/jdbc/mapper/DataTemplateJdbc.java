package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.model.Client;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        List<Field> fields = entityClassMetaData.getAllFields();
        List<Object> params = new ArrayList<>();

        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    for (Field field:fields) {
                        params.add(rs.getObject(field.getName(),
                                field.getType()));
                    }
                    entityClassMetaData.getConstructor().newInstance(params);
                }
                return null;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<Field> fields = entityClassMetaData.getAllFields();
        List<Object> params = new ArrayList<>();
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var list = new ArrayList<T>();
            try {
                while (rs.next()) {
                    for (Field field:fields) {
                        params.add(rs.getObject(field.getName(),
                                field.getType()));
                    }
                    list.add(entityClassMetaData.getConstructor().newInstance(params));
                }
                return list;
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getAllFields();
        try {
            List<Object> values = new ArrayList<>();
            for(Field field:fields)
                values.add(field.get(client));
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        try {
            List<Object> values = new ArrayList<>();
            for(Field field:fields)
                values.add(field.get(client));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    values);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

}
