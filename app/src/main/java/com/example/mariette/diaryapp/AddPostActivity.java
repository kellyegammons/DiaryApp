package com.example.mariette.diaryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mariette.diaryapp.database.DataSource;
import com.example.mariette.diaryapp.model.Post;

public class AddPostActivity extends AppCompatActivity {

    DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);

        final EditText editTitle = (EditText) findViewById(R.id.editTitle);
        final EditText editContent = (EditText) findViewById(R.id.editContent);
        final Button btnSavePost = (Button) findViewById(R.id.btnSavePost);

        mDataSource = new DataSource(this);
        mDataSource.open();

        btnSavePost.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String title = editTitle.getText().toString();
                        String content = editContent.getText().toString();

                        if(title.trim().length() != 0 && content.trim().length() != 0) {
                            try {
                                Post newPost = new Post(null, null, title, content);
                                mDataSource.createPost(newPost);
                                Toast.makeText(AddPostActivity.this, "created new post", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            } catch (Exception e) {
                                Toast.makeText(AddPostActivity.this, "could not create new post", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(AddPostActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
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
