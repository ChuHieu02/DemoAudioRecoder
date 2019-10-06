package com.hieu.model;

public class Audio {
    private String name;
    private String path;
    private String date;
    private String duration;
    private String size;

    public Audio(String name, String path, String date, String duration, String size) {
        this.name = name;
        this.path = path;
        this.date = date;
        this.duration = duration;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
