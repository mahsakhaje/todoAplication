package com.example.hw9.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.hw9.States;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Task;
import model.database.OpenHelper;
import model.database.Schema;
import model.database.TaskWrapper;

public class TaskRepository {
    private Task task;
    private List<Task> tasksTodo;
    private List<Task> tasksDoing;
    private List<Task> tasksDone;

    private SQLiteDatabase database;

    private List<String[]> description;
    private static TaskRepository instance;
    private Context mContext;

    public static TaskRepository getInstance(Context context) {
        if (instance == null)
            instance = new TaskRepository(context);
        return instance;

    }

    private TaskRepository(Context context) {
        mContext = context.getApplicationContext();
        database = new OpenHelper(mContext).getWritableDatabase();

    }

    public void addTask(Task task) {
        ContentValues values = getContentValues(task);
        database.insert(Schema.TaskTable.TableName, null, values);
    }

    //public Task getTask(int index) {
    //   return tasks.get(index);
    // }

    public Task getTask(UUID taskId) {

        String[] selectionArgs = {"" + taskId.toString()};
        TaskWrapper cursor = (TaskWrapper) queryTask(null, Schema.TaskTable.columns.UUID + " =?", selectionArgs);
        try {
            cursor.moveToFirst();
            if (cursor == null || cursor.getCount() == 0)
                return null;
            return cursor.getTask();

        } finally {
            cursor.close();
        }

    }

    public void removeTask(UUID uuid) {
        Task task1 = getTask(uuid);
        String a = task1.getTaskState();
        if (a.equals(States.TODO)) {
            for (Task task : tasksTodo) {
                if (task.getID().equals(uuid)) {
                    String uuid1 = uuid.toString();

                    String selection = Schema.TaskTable.columns.UUID + " = ?";
                    String[] selectionArgs = {uuid1};
                    tasksTodo.remove(task);
                    database.delete(Schema.TaskTable.TableName, selection, selectionArgs);


                }
            }
        } else if (a.equals(States.DONE)) {
            for (Task task : tasksDone) {
                if (task.getID().equals(uuid)) {
                    tasksDone.remove(task);
                    String uuid1 = uuid.toString();

                    String selection = "UUID = ?";
                    String[] selectionArgs = { uuid1};

                    database.delete(Schema.TaskTable.TableName, selection, selectionArgs);


                }
            }
        } else if (a.equals(States.DOING)) {
            for (Task task : tasksDoing) {
                if (task.getID().equals(uuid)) {
                    String uuid1 = uuid.toString();

                    String selection = "UUID = ?";
                    String[] selectionArgs = {uuid1};
                    tasksDoing.remove(task);
                    database.delete(Schema.TaskTable.TableName, selection, selectionArgs);

                }
            }
        }
    }

    public void updateTask(Task taskTodo) {
//        removeTask(taskTodo.getID());
//        addTask(task);
        ContentValues values = getContentValues(taskTodo);
        String sellection = Schema.TaskTable.columns.UUID + " = ?";
        String[] sellectionArgs = {taskTodo.getID().toString()};
        database.update(Schema.TaskTable.TableName, values, sellection, sellectionArgs);

    }


    private ContentValues getContentValues(Task task) {

        ContentValues values = new ContentValues();
        values.put(Schema.TaskTable.columns.UUID, task.getID().toString());

        values.put(Schema.TaskTable.columns.TITLE, task.getTitle());
        values.put(Schema.TaskTable.columns.DESCRIPTION, task.getDescription());
        if (task.getTime() != null)
            values.put(Schema.TaskTable.columns.TIME, task.getTime().getTime());
        if (task.getDate() != null)
            values.put(Schema.TaskTable.columns.DATE, task.getDate().getTime());
        values.put(Schema.TaskTable.columns.STATE, task.getTaskState());


        return values;
    }

    private CursorWrapper queryTask(String[] coulmns, String where, String[] whereArgs) {
        Cursor cursor = database.query(Schema.TaskTable.TableName, coulmns, where, whereArgs, null, null, null);
        return new TaskWrapper(cursor);
    }

    public List<Task> getTodoTasks() {
        tasksTodo = new ArrayList<>();
        // tasksDoing = new ArrayList<>();
        //  tasksDone = new ArrayList<>();
        String where = Schema.TaskTable.columns.STATE + " = ?";
        String[] args = {"todo"};
        TaskWrapper cursor = (TaskWrapper) queryTask(null, where, args);
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {


                tasksTodo.add(cursor.getTask());
//

                cursor.moveToNext();


            }


        } finally {
            cursor.close();
        }
        return tasksTodo;
    }

    public List<Task> getDoingTasks() {
        tasksDoing = new ArrayList<>();
        String where = Schema.TaskTable.columns.STATE + " = ?";
        String[] args = {"doing"};
        TaskWrapper cursor = (TaskWrapper) queryTask(null, where, args);
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                tasksDoing.add(cursor.getTask());
                cursor.moveToNext();

            }

        } finally {
            cursor.close();
        }
        return tasksDoing;

    }

    public List<Task> getDoneTasks() {
        tasksDone = new ArrayList<>();
        String where = Schema.TaskTable.columns.STATE + " = ?";
        String[] args = {"done"};
        TaskWrapper cursor = (TaskWrapper) queryTask(null, where, args);
        try {
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                if (cursor.getTask().getTaskState().equals("done")) {

                    tasksDone.add(cursor.getTask());
                    cursor.moveToNext();
                }
            }

        } finally {
            cursor.close();
        }
        return tasksDone;

    }

    public boolean dataBaseHasTask(Task task) {
        String sellection = Schema.TaskTable.columns.UUID + " = ?";
        String[] args = {task.getID().toString()};

        TaskWrapper cursor = (TaskWrapper) queryTask(null, sellection, args);
        cursor.moveToFirst();
        while (cursor.isAfterLast()) {


            if (cursor.getTask().getID().toString().equals(task.getID().toString())) {
                return true;
            }


            cursor.moveToNext();

        }
        cursor.close();


        return false;
    }

    public List<Task> searchTask(String s, String states) {
        List<Task> searchedTask = new ArrayList<>();
        if (states.equals("todo")) {

            for (Task task : getTodoTasks()) {
                if (task.getTitle().contains(s)) {
                    searchedTask.add(task);
                }
            }


        }
        else if(states.equals("doing")){
            for(Task task:getDoingTasks()){
                if(task.getTitle().contains(s))
                    searchedTask.add(task);
            }
        }
        else if(states.equals("done"))
        {
            for(Task task:getDoneTasks()){
                if(task.getTitle().contains(s))
                    searchedTask.add(task);
            }
        }
        return searchedTask;
    }

}
