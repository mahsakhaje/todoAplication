package model.database;

public class Schema {
    public static final String Name = "todolist.db";

    public static final class TaskTable {
        public static final String TableName = "taskTable";

        public static final class columns {
            public static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TIME = "time";
            public static final String DESCRIPTION = "description";
            public static final String STATE = "state";
         //   public static final String USERID="userId";

        }

    }
    public static final class UserTable {
        public static final String TableName = "userTable";

        public static final class columns {
            public static final String ID = "_id";
            public static final String UUID = "uuid";
            public static final String USERNAME = "userName";
            public static final String PASSWORD = "password";


        }

    }
}
