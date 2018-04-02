package com.example.mariette.diaryapp.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.mariette.diaryapp.database.PostTable;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Post implements Parcelable {

    private String postId;
    private Long postTimeStamp;
    private String postTitle;
    private String postContent;

    private String mascotFilePath;

    public Post() {
        this.postId = null;
        this.postTimeStamp = null;
        this.postTitle = null;
        this.postContent = null;
        this.mascotFilePath = null;
    }

    public Post(String postId, Long postTimeStamp, String postTitle, String postContent) {
        if(postId == null) {
            postId = UUID.randomUUID().toString();
        }
        if(postTimeStamp == null) {
            Date now = new Date();
            postTimeStamp = now.getTime();
        }
        this.postId = postId;
        this.postTimeStamp = postTimeStamp;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.mascotFilePath = null;
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(5);
        values.put(PostTable.COLUMN_ID, postId);
        values.put(PostTable.COLUMN_TITLE, postTitle);
        values.put(PostTable.COLUMN_CONTENT, postContent);
        values.put(PostTable.COLUMN_DATE, postTimeStamp);
        values.put(PostTable.COLUMN_FILEPATH, mascotFilePath);
        return values;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }


    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public void setPostTimeStamp(Long timeStamp) {
        this.postTimeStamp = timeStamp;
    }

    public Long getPostTimeStamp() {
        return postTimeStamp;
    }

    public Date getPostDate() {
        Date date = new Date(this.postTimeStamp);
        return date;
    }

    public String getMascotFilePath() {
        return mascotFilePath;
    }

    public void setMascotFilePath(String mascotFilePath) {
        this.mascotFilePath = mascotFilePath;
    }

    //parcelable interface
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.postId);
        parcel.writeString(this.postTitle);
        parcel.writeString(this.postContent);
        parcel.writeLong(this.postTimeStamp);
        parcel.writeString(this.mascotFilePath);
    }

    protected Post(Parcel in) {
        this.postId = in.readString();
        this.postTitle = in.readString();
        this.postContent = in.readString();
        this.postTimeStamp = in.readLong();
        this.mascotFilePath = in.readString();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {

        @Override
        public Post createFromParcel(Parcel parcel) {
            return new Post(parcel);
        }

        @Override
        public Post[] newArray(int i) {
            return new Post[i];
        }
    };


}
