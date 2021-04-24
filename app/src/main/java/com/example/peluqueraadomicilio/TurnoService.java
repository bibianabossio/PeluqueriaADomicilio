package com.example.peluqueraadomicilio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.peluqueraadomicilio.ClasesTablas.Turno;
import com.example.peluqueraadomicilio.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collection;

public class TurnoService {
    public void create (Turno turno, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();
        //String drop = "DROP TABLE "+Utilidades.TABLA_TURNO;
        //db.execSQL(drop);
        String insert="INSERT INTO "+ Utilidades.TABLA_TURNO+" ( " +Utilidades.CAMPO_FECHA+","+Utilidades.CAMPO_HORARIO+") " +
                "VALUES ('"+ turno.getFecha()+"' , '"+ turno.getHorario()+"' )";

        db.execSQL(insert);
        db.close();

    }
    public Collection<Turno> buscarTurnos (Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_TURNO,null);
        Collection <Turno> turnos = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                turnos.add(convertToTurn (cursor));
            } while (cursor.moveToNext());
        }
        return turnos;
    }

    private Turno convertToTurn(Cursor cursor) {
        Turno turno = new Turno();
        turno.setId(cursor.getInt(0));
        turno.setFecha(cursor.getString(1));
        turno.setHorario(cursor.getString(2));
        turno.setId_perro(cursor.getInt(3));
        turno.setId_dueno(cursor.getInt(4));
        return turno;
    }
}

