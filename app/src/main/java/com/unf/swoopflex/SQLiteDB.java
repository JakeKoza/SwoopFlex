package com.unf.swoopflex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class used to communicate with SQLite local Database
 */
public class SQLiteDB extends SQLiteOpenHelper {
    private static final int database_version = 1;

    //String used to create User Table
    private String CREATE_QUERY_User = "CREATE TABLE "+ SQLiteData.SQLiteUserTableInfo.TABLE_NAME +"( "+ SQLiteData.SQLiteUserTableInfo.USER_HEIGHT +
            " INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_WEIGHT +" INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_AGE +" INTEGER, "+
            SQLiteData.SQLiteUserTableInfo.USER_GENDER +" INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_BMI +" REAL);";

    //String used to create Tracking Table
    private String CREATE_QUERY_Tracking =  "CREATE TABLE "+ SQLiteData.SQLiteTrackingTableInfo.TABLE_NAME +"( " + SQLiteData.SQLiteTrackingTableInfo.Track_Time +" REAL, "+ SQLiteData.SQLiteTrackingTableInfo.Track_Date +" REAL, "+
             SQLiteData.SQLiteTrackingTableInfo.Track_Cal +" REAL);";

    public SQLiteDB(Context context) {
        super(context, SQLiteData.SQLiteUserTableInfo.DATABASE_NAME, null, database_version);
        Log.d("SQLiteDB", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY_User);
        sdb.execSQL(CREATE_QUERY_Tracking);
        Log.d("SQLiteDB", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    /**
     * Method used to insert data into local DB User Table
     * Only supports storing 1 user
     * @param db
     * @param height
     * @param weight
     * @param age
     * @param gender
     * @param bmi
     */
    public void SQLiteUserInsert(SQLiteDB db, int height, int weight, int age, int gender, double bmi){

        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SQLiteData.SQLiteUserTableInfo.USER_HEIGHT, height);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_WEIGHT, weight);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_AGE, age);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_GENDER, gender);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_BMI, bmi);

        if(!(SQLiteCheckDB(db))) {
            SQ.insert(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, null, cv);
            Log.d("SQLiteDB", "Info Inserted");
        }else{
            SQ.update(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, cv, "rowid=1", null);
            Log.d("SQLiteDB", "Info Updated");
        }

    }


    /**
     * Method used to insert data into local DB Tracking Table
     * @param db
     * @param track_time
     * @param track_date
     * @param track_cal
     */
    public void SQLiteTrackingInsert(SQLiteDB db, double track_time, double track_date, double track_cal){

        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SQLiteData.SQLiteTrackingTableInfo.Track_Time, track_time);
        cv.put(SQLiteData.SQLiteTrackingTableInfo.Track_Date, track_date);
        cv.put(SQLiteData.SQLiteTrackingTableInfo.Track_Cal, track_cal);

        SQ.insert(SQLiteData.SQLiteTrackingTableInfo.TABLE_NAME, null, cv);
        Log.d("SQLiteDB", "Info Inserted");


    }

    /**
     * Method for retrieving tracking Information from local database
     * @param db
     * @return
     */
    public Cursor SQLiteTrackingData(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteTrackingTableInfo.TABLE_NAME, SQLiteData.SQLiteTrackingTableInfo.Track_Time, SQLiteData.SQLiteTrackingTableInfo.Track_Date, SQLiteData.SQLiteTrackingTableInfo.Track_Cal};
        //Table name, columns, selectionWhere, selectionArg, groupby, having, orderby
        Cursor CR = SQ.query(SQLiteData.SQLiteTrackingTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    /**
     * Method used to retrieve Cal tracking data from local database
     * @param db
     * @return
     */
    public Cursor SQLiteTrackingCal(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteTrackingTableInfo.Track_Cal};
        //Table name, columns, selectionWhere, selectionArg, groupby, having, orderby
        Cursor CR = SQ.query(SQLiteData.SQLiteTrackingTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    /**
     * Method for retrieving User Information from local database
     * @param db
     * @return
     */
    public Cursor SQLiteUserData(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteUserTableInfo.USER_HEIGHT, SQLiteData.SQLiteUserTableInfo.USER_WEIGHT, SQLiteData.SQLiteUserTableInfo.USER_AGE,
                SQLiteData.SQLiteUserTableInfo.USER_GENDER};
                            //Table name, columns, selectionWhere, selectionArg, groupby, having, orderby
        Cursor CR = SQ.query(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    /**
     * Method for retrieving BMI from local database
     * @param db
     * @return
     */
    public Cursor SQLiteUserGetBMI(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteUserTableInfo.USER_BMI};

        Cursor CR = SQ.query(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    /**
     * Method for retrieving Weight from local database
     * @param db
     * @return
     */
    public Cursor SQLiteUserGetWeight (SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteUserTableInfo.USER_WEIGHT};

        Cursor CR = SQ.query(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    /**
     * Method used to check to see if the DB exist
     * @param db
     * @return
     */
    public boolean SQLiteCheckDB(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();
        String query = "SELECT * from " + SQLiteData.SQLiteUserTableInfo.TABLE_NAME;
        Cursor cr = SQ.rawQuery(query, null);
        if(cr.getCount()==1){
            return true;
        }else{
        return false;
        }
    }
}
