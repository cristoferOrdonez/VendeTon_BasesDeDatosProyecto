package com.example.vendeton.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbLocalVendeton extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=4;
    private static final String DATABASE_NOMBRE = "datos.db";

    public static final String TABLE_INFO_SESION="t_sesion";


    public DbLocalVendeton(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INFO_SESION + "(" +
                "idUsuario INTEGER PRIMARY KEY," + //0
                "tipoSesion INTEGER NOT NULL, " + //1
                "identificacionSesion TEXT NOT NULL)"); //2

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO_SESION);
        onCreate(sqLiteDatabase);
    }


}
