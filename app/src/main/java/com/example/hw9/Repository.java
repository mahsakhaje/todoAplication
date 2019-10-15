package com.example.hw9;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Task;
import model.User;

public class Repository {
    private Task task;
    private List<Task> tasksTodo ;
    private List<Task> tasksDoing ;
    private List<Task> tasksDone ;


    private List<String[]> description;
    private static Repository instance;

    public static Repository getInstance(Task task) {
        if (instance == null)
            instance = new Repository(task);
        return instance;

    }

    private Repository(Task task) {
        tasksTodo = new ArrayList<Task>();

        tasksDoing=new ArrayList<Task>();
        tasksDone=new ArrayList<Task>();
    }

    public void addTask(Task task) {
        if(task.getTaskState().equals(States.TODO))
            tasksTodo.add(task);
        else if(task.getTaskState().equals(States.DOING))
            tasksDoing.add(task);
       else if(task.getTaskState().equals(States.DONE))
            tasksDone.add(task);
    }

    //public Task getTask(int index) {
     //   return tasks.get(index);
   // }

    public Task getTask(UUID taskId){
        for (Task task: tasksTodo){
            if (task.getID().equals(taskId))
                return task;
        }
        for (Task task: tasksDoing){
            if (task.getID().equals(taskId))
                return task;
        }
        for (Task task: tasksDone){
            if (task.getID().equals(taskId))
                return task;
        }
        return null;
    }

    public void removeTask(UUID uuid) {
     tasksDoing.remove(getTask(uuid));
     tasksDone.remove(getTask(uuid));
     tasksTodo.remove(getTask(uuid));

    }

    public void updateTask(Task task){
        removeTask(task.getID());
        addTask(task);
    }




    public List<Task> getTodoTasks() {
        return tasksTodo;
    }
    public List<Task> getDoingTasks(){
        return tasksDoing;
    }
    public List<Task> getDoneTasks(){
        return tasksDone;
    }
}
