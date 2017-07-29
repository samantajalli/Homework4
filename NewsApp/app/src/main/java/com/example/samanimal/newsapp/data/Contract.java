package com.example.samanimal.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by Samanimal on 7/23/17.
 */

public class Contract {
    public static class TABLE_TODO  implements BaseColumns {
        public static final String TABLE_NAME = "databasetable";

        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DUE_DATE = "duedate";
    }
}
