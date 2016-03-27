package com.unf.swoopflex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ricky on 2/7/2016.
 */
public class SQLiteDB extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE "+ SQLiteData.SQLiteUserTableInfo.TABLE_NAME +"( "+ SQLiteData.SQLiteUserTableInfo.USER_HEIGHT +
            " INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_WEIGHT +" INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_AGE +" INTEGER, "+
            SQLiteData.SQLiteUserTableInfo.USER_GENDER +" INTEGER, "+ SQLiteData.SQLiteUserTableInfo.USER_BMI +" REAL);";

    public SQLiteDB(Context context) {
        super(context, SQLiteData.SQLiteUserTableInfo.DATABASE_NAME, null, database_version);
        Log.d("SQLiteDB", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        sdb.execSQL(CREATE_QUERY);
        Log.d("SQLiteDB", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void SQLiteUserInsert(SQLiteDB db, int height, int weight, int age, int gender, double bmi){

        SQLiteDatabase SQ = db.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SQLiteData.SQLiteUserTableInfo.USER_HEIGHT, height);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_WEIGHT, weight);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_AGE, age);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_GENDER, gender);
        cv.put(SQLiteData.SQLiteUserTableInfo.USER_BMI, bmi);

        if(!(SQLiteCheckDB(db))) {
            long k = SQ.insert(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, null, cv);
            Log.d("SQLiteDB", "Info Inserted");
        }else{
            long k = SQ.update(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, cv, "rowid=1", null);
            Log.d("SQLiteDB", "Info Updated");
        }

    }

    public Cursor SQLiteUserData(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteUserTableInfo.USER_HEIGHT, SQLiteData.SQLiteUserTableInfo.USER_WEIGHT, SQLiteData.SQLiteUserTableInfo.USER_AGE,
                SQLiteData.SQLiteUserTableInfo.USER_GENDER};
                            //Table name, columns, selectionWhere, selectionArg, groupby, having, orderby
        Cursor CR = SQ.query(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

    public Cursor SQLiteUserGetBMI(SQLiteDB db){

        SQLiteDatabase SQ = db.getReadableDatabase();

        String[] columns = {SQLiteData.SQLiteUserTableInfo.USER_BMI};

        Cursor CR = SQ.query(SQLiteData.SQLiteUserTableInfo.TABLE_NAME, columns, null, null, null, null, null);

        return CR;
    }

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
