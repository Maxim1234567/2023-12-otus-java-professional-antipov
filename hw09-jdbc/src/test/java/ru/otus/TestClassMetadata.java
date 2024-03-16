package ru.otus;

import ru.otus.mapper.annotation.Id;

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
}
