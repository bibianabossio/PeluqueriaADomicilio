package com.example.peluqueraadomicilio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//Crea la base de datos

    }

    @Override
    public void onCreate(SQLiteDatabase db) {//crea la tabla
        db.execSQL(Utilidades.CREAR_TABLA_PERRO);
        db.execSQL(Utilidades.CREAR_TABLA_USUARIO);
        db.execSQL(Utilidades.CREAR_TABLA_TURNO);
          }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//verifica si existe una version anterior
        db.execSQL ("DROP TABLE IF EXISTS perros");
        db.execSQL ("DROP TABLE IF EXISTS usuarios");
        db.execSQL ("DROP TABLE IF EXISTS turnos");
        onCreate(db);
    }

}
