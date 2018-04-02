package com.example.mariette.diaryapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mariette.diaryapp.database.DataSource;
import com.example.mariette.diaryapp.model.Post;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Post> postList; //list to hold posts
    DataSource mDataSource;
    private ImageView mascotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mascotView = (ImageView) findViewById(R.id.mascotView);

        //open database
        mDataSource = new DataSource(MainActivity.this);
        mDataSource.open();

        //get all posts from database
        postList = mDataSource.getPosts("all");

        //reverse order of posts to show newest first
        Collections.reverse(postList);

        //show recycle view of posts
        PostAdapter adapter = new PostAdapter(postList, MainActivity.this);
        RecyclerView postListView = (RecyclerView) findViewById(R.id.list_posts_recView);
        postListView.setAdapter(adapter);

        //set mascot
        String mascotPath = null;
        if(mDataSource.getSetting("SELECTED_MASCOT") != null) {
            mascotPath = mDataSource.getSetting("SELECTED_MASCOT");
            try {
                mascotView.setImageBitmap(BitmapFactory.decodeFile(mascotPath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mascotView.setImageResource(R.drawable.flowers);
        }

        //set mascot onclick
        mascotView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, EditMascotActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }
        );

    }

    //create menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuNewPost:
                Intent intentNewPost = new Intent(MainActivity.this, AddPostActivity.class);
                MainActivity.this.startActivity(intentNewPost);
                return true;
            case R.id.menuChangeMascot:
                Intent intentChangeMascot = new Intent(MainActivity.this, EditMascotActivity.class);
                MainActivity.this.startActivity(intentChangeMascot);
                return true;
        }
        return false;
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
