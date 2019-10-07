package model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RepositoryDoing {
    private TaskTodo task;
    private List<TaskTodo> tasks = new ArrayList<>();
    private List<String[]> description;
    private static RepositoryDoing instance;

    public static RepositoryDoing getInstance(TaskTodo task) {
        if (instance == null)
            instance = new RepositoryDoing(task);
        return instance;

    }

    private RepositoryDoing(TaskTodo task) {
        tasks = new ArrayList<TaskTodo>();
    }

    public void updateTask(TaskTodo taskTodo) {

        removeTask(taskTodo.getID());
        addTask(taskTodo);
    }

    public void removeTask(UUID id) {
        for (TaskTodo taskTodo : tasks) {
            if (taskTodo.getID().equals(id))
                tasks.remove(taskTodo);
        }
    }

    public void addTask(TaskTodo task) {
        tasks.add(task);
    }

    public TaskTodo getTask(int index) {
        return tasks.get(index);
    }

    public void removeTask(int index) {
        tasks.remove(index);
    }

    public List<TaskTodo> getTasks() {
        return tasks;
    }
}



