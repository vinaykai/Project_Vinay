package com.vkai.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class RunHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView myListView=(ListView)findViewById(R.id.mylistView);
        String [] historyL={"id(1):   miles    timeStart         time","id(2):   miles    timeStart         time","id(3):   miles    timeStart         time","id(4):   miles    timeStart         time","id(5):   miles    timeStart         time","id(6):   miles    timeStart         time","id(7):   miles    timeStart         time","id(8):   miles    timeStart         time" };
        ListAdapter myListAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,historyL);
        myListView.setAdapter(myListAdapter);

        myListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent a = new Intent(RunHistoryActivity.this, detail.class);
                        startActivity(a);

                    }
                }
        );
    }

}
