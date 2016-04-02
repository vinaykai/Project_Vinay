package com.vkai.project;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class StatisticsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
    }
    @Override
    public void onBackPressed () {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), navigation.class);
        startActivity(intent);
        finish();
    }

}
