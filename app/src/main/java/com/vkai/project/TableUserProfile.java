package com.vkai.project;

/**
 * Created by Ria on 4/6/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public  class TableUserProfile {
    private DatabaseUserProfile dbo;
    private Context ourcontext;
    private SQLiteDatabase database;

    public TableUserProfile(Context c) {
        ourcontext = c;
    }
    public TableUserProfile open() throws SQLException {
        dbo = new DatabaseUserProfile(ourcontext);
        database = dbo.getWritableDatabase();
        return this;
    }

    public void close() {
        dbo.close();
    }
/*
    public void insertData(int id,String name,String dob,String gender ) {

        ContentValues cv = new ContentValues();
        cv.put(DatabaseOperations.USER_ID, id);
        cv.put(DatabaseOperations.USER_NAME, name);
        cv.put(DatabaseOperations.USER_GENDER, gender);
        cv.put(DatabaseOperations.USER_DOB, dob);
        database.insert(DatabaseOperations.TABLE_NAME, null, cv);

    }
    */

    public Cursor readEntry() {

        String[] allColumns = new String[] {DatabaseUserProfile.USER_ID, DatabaseUserProfile.USER_NAME,DatabaseUserProfile.USER_AGE,DatabaseUserProfile.USER_HGT ,DatabaseUserProfile.USER_WGT,DatabaseUserProfile.USER_GENDER,DatabaseUserProfile.USER_BMR};

        Cursor c = database.query(DatabaseUserProfile.TABLE_NAME, allColumns, null, null, null,
                null, null);

        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }

    public Cursor selectOneItem(int id) {                     //Returns record with user_id=id
        String strQuery = String.format(
                "SELECT * FROM %s WHERE %s = %s", DatabaseUserProfile.TABLE_NAME,
                DatabaseUserProfile.USER_ID, id);
        Cursor cur = database.rawQuery(strQuery , null);
        return cur;
    }

    public void insertData(int id,String name,int age,float hgt,float wgt,String gender,double bmr) {     //Insert/Update values in DB for user_id=id

        ContentValues cv = new ContentValues();
        cv.put(DatabaseUserProfile.USER_ID, id);
        cv.put(DatabaseUserProfile.USER_NAME, name);
        cv.put(DatabaseUserProfile.USER_AGE, age);
        cv.put(DatabaseUserProfile.USER_HGT, hgt);
        cv.put(DatabaseUserProfile.USER_WGT, wgt);
        cv.put(DatabaseUserProfile.USER_GENDER, gender);
        cv.put(DatabaseUserProfile.USER_BMR, bmr);

        Cursor cur = selectOneItem(id);

        if (cur == null || cur.getCount() == 0) {
            database.insert(DatabaseUserProfile.TABLE_NAME, null, cv);
        } else {
            String[] args = {id+""};
            database.update(DatabaseUserProfile.TABLE_NAME, cv,DatabaseUserProfile.USER_ID + "=?", args);
        }
        database.close();

    }
}
