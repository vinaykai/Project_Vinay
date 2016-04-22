package com.vkai.project;

/**
 * Created by Ria on 4/7/2016.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class TableHistoryActivity extends Activity {

    TableLayout table_layout;
    TableUserProfile sqlcon;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_table_history);
        sqlcon = new TableUserProfile(this);
        table_layout = (TableLayout) findViewById(R.id.tableLayout1);
        BuildTable();
        new MyAsync().execute();
    }
    private void BuildTable() {

        sqlcon.open();
        Cursor c = sqlcon.readEntry();
        int rows = c.getCount();
        int cols = c.getColumnCount();
        c.moveToFirst();
        // outer for loop
        for (int i = 0; i < rows; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            // inner for loop
            for (int j = 0; j < cols; j++) {
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                // tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);
                tv.setText(c.getString(j)+"       ");
                row.addView(tv);
            }
            c.moveToNext();
            table_layout.addView(row);
        }
        sqlcon.close();
    }

    private class MyAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            table_layout.removeAllViews();

            PD = new ProgressDialog(TableHistoryActivity.this);
            PD.setTitle("Please Wait..");
            PD.setMessage("Loading...");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            sqlcon.open();
            // BuildTable();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            BuildTable();
            PD.dismiss();
        }


    }
}
