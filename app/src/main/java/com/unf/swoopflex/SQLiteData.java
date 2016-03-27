package com.unf.swoopflex;

import android.provider.BaseColumns;

/**
 * Created by Ricky on 2/7/2016.
 */
public class SQLiteData {

    public SQLiteData(){


    }

    public static abstract class SQLiteUserTableInfo implements BaseColumns{

        public static final String DATABASE_NAME = "SQLiteDB";
        public static final String TABLE_NAME = "User";
        public static final String USER_HEIGHT = "height";
        public static final String USER_WEIGHT = "weight";
        public static final String USER_AGE = "age";
        public static final String USER_GENDER = "gender";
        public static final String USER_BMI = "bmi";


    }
}
