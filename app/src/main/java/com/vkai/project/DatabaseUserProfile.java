package com.vkai.project;

/**
 * Created by Ria on 4/6/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DatabaseUserProfile extends SQLiteOpenHelper {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_AGE = "USER_AGE";
    public static final String USER_HGT = "USER_HGT";
    public static final String USER_WGT = "USER_WGT";
    public static final String USER_GENDER = "USER_GENDER";
    public static final String USER_BMR = "USER_BMR";
    public static final String TABLE_NAME = "userInfo";
    public static final String DATABASE_NAME = "user.DB";
    public static final int database_version=5;
    private static final String CREATE_QUERY = "CREATE TABLE "+ TABLE_NAME+"("+ USER_ID+ " INTEGER,"+USER_NAME+" TEXT,"+USER_AGE+" INTEGER,"+USER_HGT+" FLOAT,"+USER_WGT+" FLOAT,"+USER_GENDER+" TEXT,"+USER_BMR+" DOUBLE);";

    public DatabaseUserProfile(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database Operations", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb){
        sdb.execSQL(CREATE_QUERY);
        Log.d("Database Operations", "Table Created");

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
