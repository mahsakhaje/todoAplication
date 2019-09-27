package model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TaskTodo implements Serializable {
    private String title;
    private String description;
    private Date date;
    private Date time;
    private UUID ID;

    public UUID getID() {
        return ID;
    }

   public TaskTodo(){
    ID=UUID.randomUUID();
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
