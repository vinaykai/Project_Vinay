package com.vkai.project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by HOME on 4/7/2016.
 */


public class DatabaseRunHistory extends SQLiteOpenHelper {

    public static final String USER_ID = "USER_ID";
    public static final String RUN_DATE = "RUN_DATE";
    public static final String START_TIME = "START_TIME";
    public static final String STOP_TIME = "STOP_TIME";
    public static final String RUN_DIST = "RUN_DIST";
    public static final String CALORIES = "CALORIES";

    public static final String TABLE_NAME = "runInfo";
    public static final String DATABASE_NAME = "run.DB";
    public static final int database_version=5;
    private static final String CREATE_QUERY = "CREATE TABLE "+ TABLE_NAME+"("+ USER_ID+ " INTEGER,"+RUN_DATE+" DATE,"+START_TIME+" TIME,"+STOP_TIME+" TIME,"+RUN_DIST+" FLOAT,"+CALORIES+" DOUBLE);";

    public DatabaseRunHistory(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database RunHistory", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb){
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database RunHistory", "Table Created");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int old_version,int new_version){
        Log.w("TaskDBAdapter", "Upgrading from version " +
                old_version + " to " +
                new_version + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
