package ru.otus;

import ru.otus.mapper.annotation.Id;

import java.util.Objects;

public class TestClassMetadata {

    @Id
    private Long fieldId;

    private String field1;

    private String field2;

    public TestClassMetadata(Long fieldId, String field1, String field2) {
        this.fieldId = fieldId;
        this.field1 = field1;
        this.field2 = field2;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestClassMetadata that = (TestClassMetadata) o;
        return fieldId.equals(that.fieldId) && field1.equals(that.field1) && field2.equals(that.field2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldId, field1, field2);
    }
}
