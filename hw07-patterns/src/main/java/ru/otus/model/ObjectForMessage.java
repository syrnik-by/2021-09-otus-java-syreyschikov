package ru.otus.model;

import java.util.ArrayList;
import java.util.List;

public class ObjectForMessage implements Cloneable {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public ObjectForMessage clone() {
        var newData = new ArrayList<>(this.getData());
        var newObject = new ObjectForMessage();
        newObject.setData(newData);
        return newObject;
    }
}
