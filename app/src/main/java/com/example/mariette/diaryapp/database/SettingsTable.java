package com.example.mariette.diaryapp.database;

public class SettingsTable {

    public static final String TABLE_SETTINGS = "settings";
    public static final String COLUMN_ID = "settingsId";
    public static final String COLUMN_LABEL = "settingsLabel";
    public static final String COLUMN_VALUE = "settingsValue";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_LABEL, COLUMN_VALUE};

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_SETTINGS + "(" +
                    COLUMN_ID + " TEXT NOT NULL PRIMARY KEY," +
                    COLUMN_LABEL + " TEXT," +
                    COLUMN_VALUE + " TEXT" + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_SETTINGS;
}
