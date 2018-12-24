package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidu.mapapi.map.Marker;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.util.Macro;

public class PicturePreviewActivity extends AppCompatActivity {

    PhotoView photoView;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        photoView = findViewById(R.id.photo_view);
        if (savedInstanceState == null){
            Intent intent = getIntent();
            imagePath = intent.getStringExtra(Macro.IMAGE_PATH);
        }
        else{
            imagePath = savedInstanceState.getString(Macro.IMAGE_PATH);
        }
        Glide.with(this).load(imagePath).into(photoView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Macro.IMAGE_PATH, imagePath);
    }
}
