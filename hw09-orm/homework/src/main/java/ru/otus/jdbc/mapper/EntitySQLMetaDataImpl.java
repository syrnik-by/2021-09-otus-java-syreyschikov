package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData<?> entity;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entity) {
        this.entity = entity;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entity.getName().toLowerCase();
    }

    @Override
    public String getSelectByIdSql() {
        if (entity.getIdField()==null)
            return getSelectAllSql();
        else
            return String.format("SELECT * FROM %s WHERE %s = ?",
                    entity.getName().toLowerCase(),
                    entity.getIdField().getName().toLowerCase()
            );
    }

    @Override
    public String getInsertSql() {
        StringBuilder query = new StringBuilder();
        StringBuilder values = new StringBuilder();

        query.append("INSERT INTO ").append(entity.getName().toLowerCase()).append(" (");
        for (Field field : entity.getFieldsWithoutId()) {
            query.append(field.getName()).append(",");
            values.append("?,");
        }
        values.deleteCharAt(values.length() - 1);
        values.append(')');
        query.deleteCharAt(query.length() - 1);
        query.append(") VALUES (").append(values);
        return query.toString();
    }

    @Override
    public String getUpdateSql() {
        if (entity.getIdField()==null)
            return "";
        else {
            StringBuilder query = new StringBuilder();

            query.append("UPDATE ").append(entity.getName().toLowerCase()).append(" SET ");
            for (Field field : entity.getFieldsWithoutId()) {
                query.append(field.getName()).append(" = ?,");
            }
            query.deleteCharAt(query.length() - 1)
                    .append(" WHERE ")
                    .append(entity.getIdField().getName().toLowerCase())
                    .append(" = ?");
            return query.toString();
        }
    }
}
