package com.example.mariette.diaryapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mariette.diaryapp.database.DataSource;
import com.example.mariette.diaryapp.model.Post;

public class EditPostActivity extends AppCompatActivity {

    DataSource mDataSource;
    public static final String POST_KEY ="post_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);

        final EditText editTitle = (EditText) findViewById(R.id.editTitle);
        final EditText editContent = (EditText) findViewById(R.id.editContent);
        final Button btnSavePost = (Button) findViewById(R.id.btnSavePost);

        final Post post = getIntent().getExtras().getParcelable(Diary.POST_KEY);
        editTitle.setText(post.getPostTitle());
        editContent.setText(post.getPostContent());

        mDataSource = new DataSource(EditPostActivity.this);
        mDataSource.open();

        btnSavePost.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String title = editTitle.getText().toString();
                        String content = editContent.getText().toString();

                        post.setPostTitle(title);
                        post.setPostContent(content);

                        if(title.trim().length() != 0 && content.trim().length() != 0) {

                            try {
                                //save updated text to database
                                mDataSource.updatePost(post);
                                //message to show the save as successful
                                Toast.makeText(EditPostActivity.this, "The post has been updated.", Toast.LENGTH_SHORT).show();
                                //send the updated post object to the view activity
                                Intent returnIntent = new Intent();
                                returnIntent.putExtra(POST_KEY, post);
                                setResult(Activity.RESULT_OK, returnIntent);
                                //finish the activity
                                finish();
                            } catch (Exception e) {
                                Toast.makeText(EditPostActivity.this, "There was a problem editing this post.", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(EditPostActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

}
