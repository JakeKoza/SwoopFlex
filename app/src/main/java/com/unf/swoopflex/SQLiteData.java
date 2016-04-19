package com.unf.swoopflex;

import android.provider.BaseColumns;

/**
 * Class holds info about the SQLite local Database
 */
public class SQLiteData {

    public SQLiteData(){


    }

    /**
     * Used to hold User Table info
     */
    public static abstract class SQLiteUserTableInfo implements BaseColumns{

        public static final String DATABASE_NAME = "SQLiteDB";
        public static final String TABLE_NAME = "User";
        public static final String USER_HEIGHT = "height";
        public static final String USER_WEIGHT = "weight";
        public static final String USER_AGE = "age";
        public static final String USER_GENDER = "gender";
        public static final String USER_BMI = "bmi";


    }

    /**
     * Used to hold Tracking table info
     */
    public static abstract class SQLiteTrackingTableInfo implements BaseColumns{

        public static final String DATABASE_NAME = "SQLiteDB";
        public static final String TABLE_NAME = "Tracking";
        public static final String Track_Id = "track_Id";
        public static final String Track_Time = "track_Time";
        public static final String Track_Date = "track_Date";
        public static final String Track_Cal = "track_Cal";



    }
}
