package com.preid.firebasetester;

public class TodoItem {
    private String name;
    private String description;

    public TodoItem() {};

    public TodoItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
