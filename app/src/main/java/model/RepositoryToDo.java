package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryToDo {
    private TaskTodo task;
    private List<TaskTodo> tasks = new ArrayList<>();
    private List<String[]> description;
    private static RepositoryToDo instance;

    public static RepositoryToDo getInstance(TaskTodo task) {
        if (instance == null)
            instance = new RepositoryToDo(task);
        return instance;

    }

    private RepositoryToDo(TaskTodo task) {
        tasks = new ArrayList<TaskTodo>();
    }

    public void addTask(TaskTodo task) {
        tasks.add(task);
    }

    public TaskTodo getTask(int index) {
        return tasks.get(index);
    }

    public TaskTodo getTask(UUID taskId){
        for (TaskTodo task: tasks){
            if (task.getID().equals(taskId))
                return task;
        }
        return null;
    }

    public void removeTask(UUID uuid) {
       for (TaskTodo task:tasks){
           if(task.getID().equals(uuid))
               tasks.remove(task);
       }
    }

    public void updateTask(TaskTodo taskTodo){
        removeTask(taskTodo.getID());
        addTask(taskTodo);
    }


    public void removeTask(int index){
        tasks.remove(index);
    }

    public List<TaskTodo> getTasks() {
        return tasks;
    }
}
