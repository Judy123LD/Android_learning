package com.example.webviewtest;

public class App {
    private int id;
    private String version;
    private String name;

    public App(int id, String version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setName(String name) {
        this.name = name;
    }
}
