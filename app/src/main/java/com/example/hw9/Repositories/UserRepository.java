package com.example.hw9.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.User;
import model.database.OpenHelper;
import model.database.Schema;
import model.database.Schema.UserTable;
import model.database.TaskWrapper;
import model.database.UserWrapper;

public class UserRepository {
    private static UserRepository instance;
    private User mUser;
    private Context mContext;
    List<User> users;
    private SQLiteDatabase database;

    public static UserRepository getInstance(Context context) {
        if (instance == null)
            instance = new UserRepository(context);
        return instance;
    }

    private UserRepository(Context context) {
        mContext = context.getApplicationContext();
        database = new OpenHelper(mContext).getWritableDatabase();
    }

    public void addUser(User user) {

        ContentValues values = getValues(user);
        database.insert(UserTable.TableName, null, values);
    }

    public void deletUser(User user) {
        for (User user1 : users) {
            if (user1.getId() == user.getId()) {
                String uuid1 = user.getId().toString();
                String sellection = "UUID = ?";
                String[] SellectionArgs = {uuid1};
                database.delete(UserTable.TableName, sellection, SellectionArgs);
                users.remove(user1);
            }
        }

    }

    public List<User> getUsers() {
        users = new ArrayList<User>();
        UserWrapper cursor = (UserWrapper) queryUser(null, null, null);
        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast()){
                users.add(cursor.getUser());
            }

        }finally {
            cursor.close();
        }
        return users;
    }

    public ContentValues getValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserTable.columns.USERNAME, user.getUserName());
        values.put(UserTable.columns.PASSWORD, user.getPassword());
        values.put(UserTable.columns.UUID, user.getId().toString());
        return values;
    }

    private CursorWrapper queryUser(String[] coulmns, String where, String[] whereArgs) {
        Cursor cursor = database.query(UserTable.TableName, coulmns, where, whereArgs, null, null, null);
        return new TaskWrapper(cursor);
    }
}
