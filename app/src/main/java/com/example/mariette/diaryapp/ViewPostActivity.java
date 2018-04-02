package com.example.mariette.diaryapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mariette.diaryapp.database.DataSource;
import com.example.mariette.diaryapp.model.Post;

import java.text.Format;
import java.text.SimpleDateFormat;

public class ViewPostActivity extends AppCompatActivity {

    DataSource mDataSource;

    private static int IMG_RESULT = 1; //result for image upload intent
    public static String currentMascotPath = null;
    public static Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);

        final TextView viewTitle = (TextView) findViewById(R.id.tvTitle);
        final TextView viewDate = (TextView) findViewById(R.id.tvDate);
        final TextView viewContent = (TextView) findViewById(R.id.tvContent);
        final ImageView mascotView = (ImageView) findViewById(R.id.mascotView);

        post = getIntent().getExtras().getParcelable(Diary.POST_KEY);

        viewTitle.setText(post.getPostTitle());
        viewContent.setText(post.getPostContent());
        viewDate.setText(Diary.dateFormat.format(post.getPostTimeStamp()));

        //open database
        mDataSource = new DataSource(ViewPostActivity.this);
        mDataSource.open();

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
                        Intent intentMascot = new Intent(ViewPostActivity.this, EditMascotActivity.class);
                        intentMascot.putExtra(Diary.POST_KEY, post);
                        ViewPostActivity.this.startActivity(intentMascot);
                    }
                }
        );

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuEditPost:
                Intent intentEdit = new Intent(ViewPostActivity.this, EditPostActivity.class);
                intentEdit.putExtra(Diary.POST_KEY, post);
                ViewPostActivity.this.startActivityForResult(intentEdit, 1);
                return true;
            case R.id.menuNewPost:
                Intent intentNew = new Intent(ViewPostActivity.this, AddPostActivity.class);
                ViewPostActivity.this.startActivity(intentNew);
                return true;
            case R.id.menuDeletePost:
                mDataSource = new DataSource(ViewPostActivity.this);
                mDataSource.open();
                mDataSource.deletePost(post);
                mDataSource.close();
                try {
                    finish();
                    startActivity(new Intent(ViewPostActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.menuChangeMascot:
                Intent intentChangeMascot = new Intent(ViewPostActivity.this, EditMascotActivity.class);
                ViewPostActivity.this.startActivity(intentChangeMascot);
                return true;
            case R.id.menuHome:
                Intent intentHome = new Intent(ViewPostActivity.this, MainActivity.class);
                getApplicationContext().startActivity(intentHome);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getStringExtra("result");

                //use the updated post object from the edit activity to reset the title and content
                final Post post = data.getExtras().getParcelable(Diary.POST_KEY);
                final TextView viewTitle = (TextView) findViewById(R.id.tvTitle);
                final TextView viewContent = (TextView) findViewById(R.id.tvContent);
                viewTitle.setText(post.getPostTitle());
                viewContent.setText(post.getPostContent());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        try {
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
