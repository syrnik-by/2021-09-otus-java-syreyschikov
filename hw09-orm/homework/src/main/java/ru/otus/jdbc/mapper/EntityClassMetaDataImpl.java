package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public Constructor<T> getConstructor() {

        return (Constructor<T>) clazz.getConstructors()[clazz.getConstructors().length-1];
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
            methods.add(clazz.getMethod("get" + nameField));
        }
        return methods;
    }
}
