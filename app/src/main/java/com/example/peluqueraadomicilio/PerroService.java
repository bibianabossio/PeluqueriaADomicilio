package com.example.peluqueraadomicilio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.peluqueraadomicilio.ClasesTablas.Perro;
import com.example.peluqueraadomicilio.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collection;

public class PerroService {
    public void create (Perro perro, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();
        //String drop = "DROP TABLE "+Utilidades.TABLA_PERRO;
        //db.execSQL(drop);
        String insert="INSERT INTO "+ Utilidades.TABLA_PERRO+" ( "+Utilidades.CAMPO_NOMBRE_MASCOTA+"," +Utilidades.CAMPO_RAZA+","+Utilidades.CAMPO_KG+","+Utilidades.CAMPO_FOTO+") " +
                "VALUES ('"+perro.getNombre_mascota()+"' , '"+perro.getRaza()+"' , '"+perro.getKg()+"' , '"+perro.getFoto()+"' )";


        db.execSQL(insert);
        db.close();

    }
    public Collection<Perro> buscarPerros (Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_PERRO,null);
        Collection <Perro> perros = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                perros.add(convertToDog (cursor));
            } while (cursor.moveToNext());
        }
        return perros;
    }

    private Perro convertToDog(Cursor cursor) {
        Perro perro = new Perro();
        perro.setId(cursor.getInt(0));
        perro.setId_dueno(cursor.getInt(1));
        perro.setNombre_mascota(cursor.getString(2));
        perro.setRaza(cursor.getString(3));
        perro.setKg(cursor.getInt(4));
        perro.setFoto(cursor.getString(5));

        return perro;
    }
}


