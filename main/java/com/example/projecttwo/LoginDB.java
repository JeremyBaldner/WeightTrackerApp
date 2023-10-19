package com.example.projecttwo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LoginDB extends SQLiteOpenHelper{
        private Context context;
        private static final String DATABASE_NAME = "Login.db";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "login";
        private static final String COLUMN_ID = "_id";
        private static final String COLUMN_NAME = "username";
        private static final String COLUMN_PASSWORD = "password";
        public LoginDB(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        // create db
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " +
                    TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " INTEGER);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        // add a new account to the db
        void addAccount(String username, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COLUMN_NAME, username);
            cv.put(COLUMN_PASSWORD, password);
            long result = db.insert(TABLE_NAME, null, cv);
            if(result == -1) {
                Toast.makeText(context, "Failed to Upload", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }

        // get login db info
        Cursor readLoginData() {
            String query = "SELECT * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = null;
            if(db != null) {
                cursor = db.rawQuery(query, null);
            }
            return cursor;
        }
}
