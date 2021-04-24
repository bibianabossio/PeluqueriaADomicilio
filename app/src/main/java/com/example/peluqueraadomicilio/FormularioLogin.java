package com.example.peluqueraadomicilio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;
import com.google.android.material.snackbar.Snackbar;

public class FormularioLogin extends AppCompatActivity {
    //variables
    EditText usuario;
    EditText contrasena;
    EditText dueno;
    EditText mail;
    EditText domicilio;
    EditText localidad;
    EditText celular;
    Button guardar;
    String error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_login);
        dueno = (EditText) findViewById(R.id.duenologin);
        usuario = (EditText) findViewById(R.id.usuariologin);
        contrasena = (EditText) findViewById(R.id.contrasenalogin);
        mail = (EditText) findViewById(R.id.maillogin);
        domicilio = (EditText) findViewById(R.id.domiciliologin);
        localidad = (EditText) findViewById(R.id.localidadlogin);
        celular = (EditText) findViewById(R.id.celularlogin);
        guardar = (Button) findViewById(R.id.GuardarLogin);
        guardar.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                if (validacion()){
                    guardar_login();
                }
                else {
                    Snackbar.make(findViewById(android.R.id.content), " "+ error,
                                    Snackbar.LENGTH_LONG)
                                    .setDuration(3000)
                                    .show();



                }
            }
        });


    }

    private boolean validacion() {
        String duen = dueno.getText().toString();
        String usu = usuario.getText().toString();
        String contra = contrasena.getText().toString();
        String ml = mail.getText().toString();
        String dom = domicilio.getText().toString();
        String loc = localidad.getText().toString();
        String cel = celular.getText().toString();

        if (!usu.equals("") && !contra.equals("") && !duen.equals("")&& !ml.equals("")&& !dom.equals("")&& !loc.equals("")&& !cel.equals("")){
            if (usu.length()>3) {
                if (contra.length() > 5) {
                    if (ml.contains("@") && ml.contains(".com")) {
                        if (cel.length() < 9) {
                            return true;
                        }
                        else {
                            error="Celular minimo 10 digitos";
                            return false;
                        }
                    }
                    else {
                        error= "mail con @ y .com";
                        return false;
                    }
                }
                else {
                    error= "Contraseña minima 6 digitos";
                    return false;
                }
            }
            else {
                error= "Usuario minimo 4 letras";
                return false;
            }
        }
        else {
            return false;
        }
    }

    private void guardar_login () {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioLogin.this, "bd_perros", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("usuario", usuario.getText().toString());//el valor que se asigne se guarda en la bd
            values.put("contrasena", contrasena.getText().toString());
            values.put("nombre_dueno", dueno.getText().toString());
            values.put("mail", mail.getText().toString());
            values.put("domicilio", domicilio.getText().toString());
            values.put("localidad", localidad.getText().toString());
            values.put("celular", celular.getText().toString());
            Long idresultante = db.insert("usuarios", "id", values);
            Utilidades.usaurioLog = (Integer)idresultante.intValue();
            System.out.println("el valor de idresultante es:" + Utilidades.usaurioLog);
            Toast.makeText(this, "Usuario Ingresado" + idresultante, Toast.LENGTH_LONG).show();
            Intent intento2 = new Intent(FormularioLogin.this, FormularioRegistro.class); // configuro para que vaya a la otra pantalla
            startActivity(intento2);//con esto va a turno

            finish();


        }


}




