package com.c.myPoc.models;

import com.tridium.json.JSONObject;

public class PointDto {
    private String name;
    private String value;
    private String path;

    public PointDto(String name, String value, String path) {
        this.name = name;
        this.value = value;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("value", value);
        json.put("path", path);
        return json;
    }
}