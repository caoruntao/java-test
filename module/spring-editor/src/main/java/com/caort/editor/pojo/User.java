package com.caort.editor.pojo;

import java.util.Properties;

/**
 * @author Caort
 * @date 2021/1/7 20:22
 */
public class User {
    private String id;
    private String name;
    private Properties context;
    private String asText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Properties getContext() {
        return context;
    }

    public void setContext(Properties context) {
        this.context = context;
    }

    public String getAsText() {
        return asText;
    }

    public void setAsText(String asText) {
        this.asText = asText;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", context=" + context +
                ", asText='" + asText + '\'' +
                '}';
    }
}
