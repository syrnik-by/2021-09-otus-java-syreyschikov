package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> clazz;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
        return clazz.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    @Override
    public Field getIdField() {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                fields.add(field);
            }
        }
        return fields;
    }

    @Override
    public List<Method> getAllMethods() throws NoSuchMethodException {
        List<Field> fields = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        for (Field field : getAllFields()) {
            String nameField = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            methods.add(clazz.getDeclaredMethod("get" + nameField));
        }
        return methods;
    }

    @Override
    public List<Method> getMethodsWithoutId() throws NoSuchMethodException {
        List<Field> fields = new ArrayList<>();
        List<Method> methods = new ArrayList<>();
        for (Field field : getFieldsWithoutId()) {
            String nameField = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            methods.add(clazz.getDeclaredMethod("get" + nameField));
        }
        return methods;
    }

    @Override
    public void setMethodInvoke(Field field, ResultSet rs, T optional) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        String nameField = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        Method method = clazz.getDeclaredMethod("set" + nameField, field.getType());
        method.invoke(optional, rs.getObject(field.getName(),field.getType()));
    }
}
