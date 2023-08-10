package com.litamihai.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "Tasks")
public class TaskModel implements Serializable {
    @Id
    private String id;
    private String text;
    private String date;
    private boolean reminder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isReminder() {
        return reminder;
    }

    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }

    public TaskModel() {
    }

    public TaskModel(String id, String text, String date, boolean reminder) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.reminder = reminder;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", reminder=" + reminder +
                '}';
    }
}
