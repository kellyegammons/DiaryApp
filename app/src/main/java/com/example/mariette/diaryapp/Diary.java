package com.example.mariette.diaryapp;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.example.mariette.diaryapp.database.DataSource;

import java.text.Format;
import java.text.SimpleDateFormat;

class Diary {
    //singleton instance
    private static final Diary ourInstance = new Diary();

    //intent extras
    public static final String POST_KEY ="post_key";

    //date formats
    public static final Format dateFormat = new SimpleDateFormat("E, MMMM d yyyy 'at' h:mm a");

    public static Diary getInstance() {
        return ourInstance;
    }

    private Diary() {
        //da-dame...!
    }

}
