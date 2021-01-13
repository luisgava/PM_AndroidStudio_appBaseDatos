package com.example.appbasedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Sirve de ayuda para crear la BD
 **/
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1; // constantes para usar en el constructor
    public static final String DATABASE_NAME = "dbMusica.db";

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos la tabla si no existe
        db.execSQL(EstructuraBD.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Borra la tabla y la crea de nuevo. No sería lo más correcto.
        db.execSQL(EstructuraBD.SQL_DELETE_ENTRIES);
        db.execSQL(EstructuraBD.SQL_CREATE_ENTRIES);
    }
}
