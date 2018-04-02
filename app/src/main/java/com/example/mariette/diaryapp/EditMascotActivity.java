package com.example.mariette.diaryapp;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mariette.diaryapp.database.DataSource;

import pub.devrel.easypermissions.EasyPermissions;

public class EditMascotActivity extends AppCompatActivity {

    DataSource mDataSource;
    private ImageView mascotView;
    public static String newMascotPath;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}; //permissions for reading and writing files
    private static int IMG_RESULT = 1; //result for image upload intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_mascot);
        mascotView = (ImageView) findViewById(R.id.mascotView);

        //open database
        mDataSource = new DataSource(EditMascotActivity.this);
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

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, IMG_RESULT);
                    }
                }
        );

        if(EasyPermissions.hasPermissions(EditMascotActivity.this, galleryPermissions)) {
            //do nothing
        } else {
            //popup asking for permissions
            EasyPermissions.requestPermissions(this, "Access for storage", 101, galleryPermissions);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMG_RESULT && resultCode == RESULT_OK && null != data) {

            try {
                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(URI, FILE, null, null, null);

                //get the filepath from storage
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(FILE[0]);
                newMascotPath = cursor.getString(columnIndex);

                //save this mascot to app mascot table
                mDataSource = new DataSource(EditMascotActivity.this);//open data source
                mDataSource.open();
                mDataSource.saveSetting("SELECTED_MASCOT", "Selected Mascot", newMascotPath);
                Toast.makeText(EditMascotActivity.this, "global mascot saved", Toast.LENGTH_SHORT).show();

                mascotView.setImageBitmap(BitmapFactory.decodeFile(newMascotPath));

            } catch (Exception e) {
                Toast.makeText(EditMascotActivity.this, "there was a problem updating the mascot", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
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
