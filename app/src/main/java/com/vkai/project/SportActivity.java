package com.vkai.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.session.MediaController;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;

public class SportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ProgressDialog progressDialog;
        MediaController mediaControls;

        setContentView(R.layout.content_sport);
        WebView mWebView=(WebView)findViewById(R.id.myWebView);
        String videoID="https://www.youtube.com/watch?v=1fbU_MkV7NE";
        mWebView.loadUrl(videoID);

        Button back=(Button)findViewById(R.id.backButton);
        back.setOnClickListener(
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent b = new Intent(SportActivity.this, VideoActivity.class);
                        startActivity(b);
                    }
                }
        );




    }

}
