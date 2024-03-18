package ru.otus.mapper;

import ru.otus.exception.ConstructorNotFoundException;
import ru.otus.exception.IdNotFoundException;
import ru.otus.mapper.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Stream;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private Class<T> aClass;

    public EntityClassMetaDataImpl(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public String getName() {
        String simpleName = aClass.getSimpleName();
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return aClass.getDeclaredConstructor(
                    Stream.of(aClass.getDeclaredFields()).map(Field::getType).toList().toArray(new Class[0])
            );
        } catch (NoSuchMethodException e) {
            throw new ConstructorNotFoundException(aClass.getSimpleName(), e);
        }
    }

    @Override
    public Field getIdField() {
        return Stream.of(aClass.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(IdNotFoundException::new);
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(aClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Stream.of(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .toList();
    }
}
