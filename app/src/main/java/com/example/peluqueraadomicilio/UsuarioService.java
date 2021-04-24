package com.example.peluqueraadomicilio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.peluqueraadomicilio.ClasesTablas.Usuario;
import com.example.peluqueraadomicilio.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Collection;

public class UsuarioService {
    public void create (Usuario usuario, Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);

        SQLiteDatabase db=conn.getWritableDatabase();
        //String drop = "DROP TABLE "+Utilidades.TABLA_USUARIO;
        //db.execSQL(drop);
        String insert="INSERT INTO "+ Utilidades.TABLA_USUARIO+" ( "+Utilidades.CAMPO_USUARIO+"," +Utilidades.CAMPO_CONTRASENA+","+Utilidades.CAMPO_NOMBRE_DUENO+","+Utilidades.CAMPO_MAIL+","+Utilidades.CAMPO_DOMICILIO+","+Utilidades.CAMPO_LOCALIDAD+","+Utilidades.CAMPO_CELULAR+")" +
                "VALUES ('"+usuario.getUsuario()+"' , '"+usuario.getContrasena()+"' , '"+usuario.getNombre_dueno()+"' , '"+usuario.getMail()+"' , '"+usuario.getDomicilio()+"' , '"+usuario.getLocalidad()+"' , '"+usuario.getCelular()+"')";



        db.execSQL(insert);
        db.close();

    }
    public Collection<Usuario> buscarUsuarios (Context context){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(context,"bd_perros",null, 1);
        SQLiteDatabase db=conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO,null);
        Collection <Usuario> usuarios = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                usuarios.add(convertToUser (cursor));
            } while (cursor.moveToNext());
        }
        return usuarios;
    }

    private Usuario convertToUser(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setId(cursor.getInt(0));
        usuario.setUsuario(cursor.getString(1));
        usuario.setContrasena(cursor.getInt(2));
        usuario.setNombre_dueno(cursor.getString(3));
        usuario.setMail(cursor.getString(4));
        usuario.setDomicilio(cursor.getString(5));
        usuario.setLocalidad(cursor.getString(6));
        usuario.setCelular(cursor.getString(7));
        return usuario;
    }
}

