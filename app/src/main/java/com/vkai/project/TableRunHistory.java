package com.vkai.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HOME on 4/7/2016.
 */
public class TableRunHistory {
    private DatabaseRunHistory dbo;
    private Context ourcontext;
    private SQLiteDatabase database;

    public TableRunHistory(Context c) {
        ourcontext = c;
    }
    public TableRunHistory open() throws SQLException {
        dbo = new DatabaseRunHistory(ourcontext);
        database = dbo.getWritableDatabase();
        return this;
    }

    public void close() {
        dbo.close();
    }

    public void insertData(int id,String date,String start_time,String stop_time,float dist,double calories ) {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseRunHistory.USER_ID, 1);
        cv.put(DatabaseRunHistory.RUN_DATE, "04/09/2015");
        cv.put(DatabaseRunHistory.START_TIME, "12:12:12");
        cv.put(DatabaseRunHistory.STOP_TIME, "12:40:15");
        cv.put(DatabaseRunHistory.RUN_DIST, 10);
        cv.put(DatabaseRunHistory.CALORIES, 352);
/*        cv.put(DatabaseRunHistory.USER_ID, id);//must be removed later
        cv.put(DatabaseRunHistory.RUN_DATE, date);
        cv.put(DatabaseRunHistory.START_TIME, start_time);
        cv.put(DatabaseRunHistory.STOP_TIME, stop_time);
        cv.put(DatabaseRunHistory.RUN_DIST, dist);
        cv.put(DatabaseRunHistory.CALORIES, calories);*/
        database.insert(DatabaseRunHistory.TABLE_NAME, null, cv);

    }


    public Cursor readEntry() {

        String[] allColumns = new String[] {DatabaseRunHistory.USER_ID, DatabaseRunHistory.RUN_DATE,DatabaseRunHistory.START_TIME,DatabaseRunHistory.STOP_TIME ,DatabaseRunHistory.RUN_DIST,DatabaseRunHistory.CALORIES};

        Cursor c = database.query(DatabaseRunHistory.TABLE_NAME, allColumns, null, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public Cursor selectOneItem(int id) {                     //Returns record with user_id=id
        String strQuery = String.format(
                "SELECT * FROM %s WHERE %s = %s", DatabaseRunHistory.TABLE_NAME,
                DatabaseRunHistory.USER_ID, id);
        Cursor cur = database.rawQuery(strQuery , null);
        return cur;
    }
}
