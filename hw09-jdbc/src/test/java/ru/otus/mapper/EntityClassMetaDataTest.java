package ru.otus.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.TestClassMetadata;
import ru.otus.TestClassMetadataWithoutIdAndConstructor;
import ru.otus.exception.ConstructorNotFoundException;
import ru.otus.exception.IdNotFoundException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class EntityClassMetaDataTest {

    private EntityClassMetaData<TestClassMetadata> entityClassMetaData;

    private EntityClassMetaData<TestClassMetadataWithoutIdAndConstructor> entityClassMetaDataWithoutIdAndConstructor;

    @BeforeEach
    public void setUp() {
        entityClassMetaData = new EntityClassMetaDataImpl<>(TestClassMetadata.class);
        entityClassMetaDataWithoutIdAndConstructor = new EntityClassMetaDataImpl<>(TestClassMetadataWithoutIdAndConstructor.class);
    }

    @Test
    public void shouldCorrectReturnName() {
        String nameClass = "testClassMetadata";

        String result = entityClassMetaData.getName();

        assertThat(result).isNotNull()
                .isEqualTo(nameClass);
    }

    @Test
    public void shouldCorrectReturnConstructor() {
        Constructor<?> result = entityClassMetaData.getConstructor();

        assertThat(result).isNotNull()
                .matches(c -> c.getParameterCount() == 3);
    }

    @Test
    public void shouldThrowExceptionConstructorNotFoundException() {
        String message =
                assertThrows(ConstructorNotFoundException.class, () -> entityClassMetaDataWithoutIdAndConstructor.getConstructor())
                        .getMessage();

        assertThat(message).isNotNull()
                .isEqualTo("In class TestClassMetadataWithoutIdAndConstructor constructor not found");
    }

    @Test
    public void shouldCorrectReturnIdField() {
        String name = "fieldId";
        String type = "Long";

        Field result = entityClassMetaData.getIdField();

        assertThat(result).isNotNull()
                .matches(f -> f.getName().equals(name))
                .matches(f -> f.getType().getSimpleName().equals(type));
    }

    @Test
    public void shouldThrowIdNotFoundException() {
        assertThrows(IdNotFoundException.class, () -> entityClassMetaDataWithoutIdAndConstructor.getIdField());
    }

    @Test
    public void shouldCorrectReturnAllFields() {
        List<TestField> fields = List.of(
                new TestField("fieldId", "Long"),
                new TestField("field1", "String"),
                new TestField("field2", "String")
        );

        List<TestField> result = entityClassMetaData.getAllFields().stream()
                .map(TestField::toFiled)
                .toList();

        assertThat(result).hasSize(3)
                .isEqualTo(fields);
    }

    @Test
    public void shouldCorrectReturnFieldsWithoutId() {
        List<TestField> fields = List.of(
                new TestField("field1", "String"),
                new TestField("field2", "String")
        );

        List<TestField> result = entityClassMetaData.getFieldsWithoutId().stream()
                .map(TestField::toFiled)
                .toList();

        assertThat(result).hasSize(2)
                .isEqualTo(fields);
    }

    private record TestField(String name, String type) {

        public static TestField toFiled(Field field) {
            return new TestField(field.getName(), field.getType().getSimpleName());
        }

    }
}
