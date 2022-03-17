package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * "Разбирает" объект на составные части
 */
public interface EntityClassMetaData<T> {
    String getName();

    Constructor<T> getConstructor() throws NoSuchMethodException;

    //Поле Id должно определять по наличию аннотации Id
    //Аннотацию @Id надо сделать самостоятельно
    Field getIdField();

    List<Field> getAllFields();

    List<Field> getFieldsWithoutId();

    List<Method> getAllMethods() throws NoSuchMethodException ;

    List<Method> getMethodsWithoutId() throws NoSuchMethodException ;

    void setMethodInvoke(Field field, ResultSet rs, T optional) throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException;
}
