package ru.otus.model;

import java.util.List;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    protected ObjectForMessage clone() {
        try {
            this.data = List.copyOf(data);
            return (ObjectForMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
