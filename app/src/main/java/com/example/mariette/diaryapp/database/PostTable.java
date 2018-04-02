package com.example.mariette.diaryapp.database;

public class PostTable {

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "postId";
    public static final String COLUMN_TITLE = "postTitle";
    public static final String COLUMN_CONTENT = "postContent";
    public static final String COLUMN_DATE = "postTimeStamp";
    public static final String COLUMN_FILEPATH = "mascotFilePath";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_DATE, COLUMN_FILEPATH};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_POSTS + "(" +
                    COLUMN_ID + " TEXT NOT NULL PRIMARY KEY," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_CONTENT + " TEXT," +
                    COLUMN_DATE + " INTEGER," +
                    COLUMN_FILEPATH + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_POSTS;
}
