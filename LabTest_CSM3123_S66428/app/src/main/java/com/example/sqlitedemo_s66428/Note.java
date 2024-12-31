package com.example.sqlitedemo_s66428;

public class Note {
    private int id;
    private String title;
    private String content;
    private String createdAt;
    private String modifiedAt;

    public Note(int id, String title, String content, String createdAt, String modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }
}
