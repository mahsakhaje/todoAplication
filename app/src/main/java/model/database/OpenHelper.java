package model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {
    public static final int version = 1;
    public static final String name = Schema.Name;

    public OpenHelper(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Schema.TaskTable.TableName + "("
                + Schema.TaskTable.columns.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Schema.TaskTable.columns.TITLE + ", "
                + Schema.TaskTable.columns.DESCRIPTION + ", "
                + Schema.TaskTable.columns.STATE + ", "
                + Schema.TaskTable.columns.TIME + ", "
                + Schema.TaskTable.columns.DATE + ", "
                + Schema.TaskTable.columns.UUID + " )"


        );
        sqLiteDatabase.execSQL("CREATE TABLE " + Schema.UserTable.TableName + "("
                + Schema.UserTable.columns.ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Schema.UserTable.columns.UUID + ", "
                + Schema.UserTable.columns.USERNAME+ ", "
                + Schema.UserTable.columns.PASSWORD +
                " )"





        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
