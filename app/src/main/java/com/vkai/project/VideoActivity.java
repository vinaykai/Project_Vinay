package com.vkai.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton sport=(ImageButton)findViewById(R.id.situp);
        sport.setOnClickListener(
                new AdapterView.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent s=new Intent(VideoActivity.this,SportActivity.class);
                        startActivity(s);
                    }
                }
        );
    }

}
