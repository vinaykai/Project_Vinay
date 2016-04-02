package com.vkai.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by HOME on 3/11/2016.
 */
public class GymOptionsActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbygym);
    }
    @Override
    public void onBackPressed () {
        super.onBackPressed();
        Intent intent = new Intent(GymOptionsActivity.this, navigation.class);
        startActivity(intent);
        finish();
    }

}
