package model;

import com.example.hw9.States;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Task implements Serializable {
    private String title;
    private String description;
    private Date date;
    private Date time;
    private UUID ID;
    private States taskState;

    public States getTaskState() {
        return taskState;
    }

    public void setTaskState(States taskState) {
        this.taskState = taskState;
    }

    public UUID getID() {
        return ID;
    }

    public Task() {
        ID = UUID.randomUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}