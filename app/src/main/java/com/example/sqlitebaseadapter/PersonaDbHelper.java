package com.example.sqlitebaseadapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sqlitebaseadapter.PersonaContract.PersonaEntry;

public class PersonaDbHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 2;
    public static String DATABASE_NAME = "Personas.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PersonaEntry.TABLE_NAME + " (" +
                    PersonaEntry._ID + " INTEGER PRIMARY KEY," +
                    PersonaEntry.COLUMN_NAME_NAME + " TEXT," +
                    PersonaEntry.COLUMN_NAME_PHONE + " TEXT," +
                    PersonaEntry.COLUMN_NAME_SEX + " INTEGER DEFAULT -1" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonaEntry.TABLE_NAME;


    public PersonaDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_ADD_SEX = "ALTER TABLE " + PersonaEntry.TABLE_NAME + " ADD " + PersonaEntry.COLUMN_NAME_SEX + " INTEGER DEFAULT -1";
        db.execSQL(SQL_ADD_SEX);
    }
}
