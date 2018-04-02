package com.example.mariette.diaryapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mariette.diaryapp.model.Post;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    //posts
    public Post createPost(Post post) {
        ContentValues values = post.toValues();
        mDatabase.replace(PostTable.TABLE_POSTS, null, values);
        return post;
    }

    public Post updatePost(Post post) {
        ContentValues values = post.toValues();
        mDatabase.update(PostTable.TABLE_POSTS, values, PostTable.COLUMN_ID + " = '" + post.getPostId() + "'", null);
        return post;
    }

    public boolean deletePost(Post post) {
        mDatabase.delete(PostTable.TABLE_POSTS, PostTable.COLUMN_ID + " = '" + post.getPostId() + "'", null);
        return true;
    }

    public long getPostsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, PostTable.TABLE_POSTS);
    }

    public List<Post> getPosts(String selection) {
        List<Post> posts = new ArrayList<>();
        Cursor cursor;
        if (selection == "all") {
            cursor = mDatabase.query(PostTable.TABLE_POSTS, PostTable.ALL_COLUMNS,
                    null, null, null, null, null);
        } else {
            cursor = mDatabase.query(PostTable.TABLE_POSTS, PostTable.ALL_COLUMNS, PostTable.COLUMN_ID + " = ?",
                    new String[] {selection}, null, null, null);
        }
        while(cursor.moveToNext()) {
            Post post = new Post();
            post.setPostId(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_ID)));
            post.setPostTitle(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_TITLE)));
            post.setPostContent(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_CONTENT)));
            post.setPostTimeStamp(cursor.getLong(cursor.getColumnIndex(PostTable.COLUMN_DATE)));
            post.setMascotFilePath(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_FILEPATH)));
            posts.add(post);
        }

        return posts;
    }
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        Cursor cursor = mDatabase.query(PostTable.TABLE_POSTS, PostTable.ALL_COLUMNS,
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            Post post = new Post();
            post.setPostId(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_ID)));
            post.setPostTitle(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_TITLE)));
            post.setPostContent(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_CONTENT)));
            post.setPostTimeStamp(cursor.getLong(cursor.getColumnIndex(PostTable.COLUMN_DATE)));
            post.setMascotFilePath(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_FILEPATH)));
            posts.add(post);
        }
        return posts;
    }

    //save settings
    public boolean saveSetting(String settingId, String settingLabel, String settingValue) {
        ContentValues values = new ContentValues(3);
        values.put(SettingsTable.COLUMN_ID, settingId);
        values.put(SettingsTable.COLUMN_LABEL, settingLabel);
        values.put(SettingsTable.COLUMN_VALUE, settingValue);
        mDatabase.replace(SettingsTable.TABLE_SETTINGS, null, values);
        return true;
    }

    public String getSetting(String settingId) {
        String setting = null;
        Cursor cursor;
        try {
            cursor = mDatabase.query(SettingsTable.TABLE_SETTINGS, SettingsTable.ALL_COLUMNS, SettingsTable.COLUMN_ID + " = '" + settingId + "'",
            null, null, null, null);
            cursor.moveToFirst();
            setting = cursor.getString(cursor.getColumnIndex(SettingsTable.COLUMN_VALUE));
        } catch (Exception e) {
            System.out.println("No setting with this id. ");
            e.printStackTrace();
        }
        return setting;
    }

    public long getSettingsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, SettingsTable.TABLE_SETTINGS);
    }
}
