package model.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import model.database.Schema.UserTable;
import model.User;

public class UserWrapper extends CursorWrapper {

    public UserWrapper(Cursor cursor) {
        super(cursor);
    }
    public User getUser(){

        String userName=getString(getColumnIndex(UserTable.columns.USERNAME));
        String passWord=getString(getColumnIndex(UserTable.columns.PASSWORD));
        String id=getString(getColumnIndex(UserTable.columns.UUID));
        UUID id1=UUID.fromString(id);
        User user=new User(id1);
        user.setUserName(userName);
        user.setPassword(passWord);
        return user;
    }
}
