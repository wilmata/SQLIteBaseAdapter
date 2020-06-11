package com.example.sqlitebaseadapter;

import android.provider.BaseColumns;

public class PersonaContract {

    public static class PersonaEntry implements BaseColumns{
        public static final String COLUMN_NAME_NAME = "nombre";
        public static final String COLUMN_NAME_PHONE = "telefono";
        public static final String COLUMN_NAME_SEX = "sexo";
        public static final String TABLE_NAME = "tblPersona";
    }
}
