package com.thcreate.vegsurveyassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SenlinyangdiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senlinyangdi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onAddQiaomuyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, QiaomuyangfangActivity.class);
        startActivity(intent);
    }

    public void onAddGuanmuyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, GuanmuyangfangActivity.class);
        startActivity(intent);
    }

    public void onAddCaobenyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, CaobenyangfangActivity.class);
        startActivity(intent);
    }
}
