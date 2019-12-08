package model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.hw9.States;

import java.text.Format;
import java.util.Date;
import java.util.UUID;

import model.Task;

public class TaskWrapper extends CursorWrapper {
    public TaskWrapper(Cursor cursor) {
        super(cursor);
    }

    public Task getTask() {
        String title = getString(getColumnIndex(Schema.TaskTable.columns.TITLE));
        String description = getString(getColumnIndex(Schema.TaskTable.columns.DESCRIPTION));
        Long date = getLong(getColumnIndex(Schema.TaskTable.columns.DATE));
        Long time = getLong(getColumnIndex(Schema.TaskTable.columns.TIME));
        String uuid = getString(getColumnIndex(Schema.TaskTable.columns.UUID));
        String state = getString(getColumnIndex(Schema.TaskTable.columns.STATE));
      //  String userId=getString(getColumnIndex(Schema.TaskTable.columns.USERID));

        UUID uuid1 = UUID.fromString(uuid);
      //  UUID userId1=UUID.fromString(userId);
        Date date1 = new Date(date);
        Date time1 = new Date(time);
        Task task = new Task(uuid1);
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(date1);
        task.setTime(time1);
       // task.setUserId(userId1);
        if (state.equals("todo")) {
            task.setTaskState(States.TODO);
        } else if (state.equals("doing")) {
            task.setTaskState(States.DOING);

        } else if (state.equals("done")) {
            task.setTaskState(States.DONE);
        }
        return task;
    }
}
