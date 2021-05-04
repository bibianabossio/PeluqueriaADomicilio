package com.example.peluqueraadomicilio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peluqueraadomicilio.ClasesTablas.Usuario;
import com.example.peluqueraadomicilio.Utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {
EditText usuario;
EditText contrasena;
Button inicio;
Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
           usuario =  (EditText) findViewById(R.id.usuario);//relaciono los campos. Instancias
           contrasena = (EditText) findViewById(R.id.contrasena);
           inicio = (Button)  findViewById(R.id.iniciobtn);
           registro = (Button) findViewById(R.id.registrobtn);

        inicio.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                //consulta a la base, comparar los datos de la base con lo cargado en el linea 21 y 22
                String dueno = usuario.getText().toString();
                String pass = contrasena.getText().toString();
                if (!dueno.equals("") && !pass.equals("")) {//compruebo que no este vacio y entro a la base de datos.
                    ConexionSQLiteHelper conn = new ConexionSQLiteHelper(MainActivity.this, "bd_perros", null, 1);
                    SQLiteDatabase objSQLdb = conn.getReadableDatabase();//crea un objeto de base de datos para hacerlo legible.
                    Usuario objetoUsuario = new Usuario();
                    Cursor objetoCursor = objSQLdb.rawQuery("SELECT * FROM " + Utilidades.TABLA_USUARIO + " WHERE " + Utilidades.CAMPO_USUARIO + "='" + dueno + "'", null);
                    if (objetoCursor != null) {
                        while (objetoCursor.moveToNext()) {

                            objetoUsuario.setUsuario(objetoCursor.getString(objetoCursor.getColumnIndex("usuario")));
                            objetoUsuario.setContrasena(objetoCursor.getString(objetoCursor.getColumnIndex("contrasena")));

                        }

                    }
                    try {
                        if (pass.equals(objetoUsuario.getContrasena().toString())) {
                            Intent intento = new Intent(MainActivity.this, Inicio.class);//adonde estoy y adonde quiero ir
                            intento.putExtra("user", dueno);
                            startActivity(intento);
                        } else {

                            Toast.makeText(MainActivity.this, "ERROR: La contraseña no es valida:\nDebe contener al menos: 6 caracteres, 1 minúscula, 1 mayúscula, 1 numero y 1 signo", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "ERROR: Datos Incorrectos", Toast.LENGTH_SHORT).show();
                    }

                } else if (dueno.equals("") && pass.equals("")) {
                    Toast.makeText(MainActivity.this, "ERROR: Ingresar Usuario y Contraseña", Toast.LENGTH_SHORT).show();
                }

            }
         });
        registro.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                Intent intento1 = new Intent(MainActivity.this, FormularioLogin.class);//adonde estoy y adonde quiero ir
                startActivity(intento1);
            }
        });

    }
}