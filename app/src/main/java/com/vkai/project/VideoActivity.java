package com.vkai.project;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_video);
        ListView HistoryList=(ListView)findViewById(R.id.listView);
        String[] myList={"push_up","customize_1","customize_2","customize_3"};
        ListAdapter LA=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myList);
        HistoryList.setAdapter(LA);
        HistoryList.setOnItemClickListener(

                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String sports = String.valueOf(parent.getItemAtPosition(position));
                        Intent td = new Intent(VideoActivity.this, EditSport.class);
                        Toast.makeText(VideoActivity.this, sports, Toast.LENGTH_SHORT).show();

                    }
                }
        );
        ImageButton sport=(ImageButton)findViewById(R.id.situp);
        sport.setOnClickListener(
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent s = new Intent(VideoActivity.this, SportActivity.class);
                    }
                }
        );

        Button searchB=(Button)findViewById(R.id.button);
        searchB.setOnClickListener(
                new AdapterView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText searchWords=(EditText)findViewById(R.id.editText);

                        String inputText = searchWords.getText().toString();
                        String webseturi = "https://www.youtube.com/results?search_query=" + inputText;
                        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(webseturi));
                        startActivity(intent);
                    }
                }
        );


    }
}
