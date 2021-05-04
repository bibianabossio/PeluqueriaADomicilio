package com.example.peluqueraadomicilio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class FormularioLogin extends AppCompatActivity {
    //variables
    EditText usuario;
    TextView errorUs;
    EditText contrasena;
    TextView errorCo;
    EditText dueno;
    TextView errorDu;
    EditText mail;
    TextView errorMa;
    EditText domicilio;
    EditText localidad;
    EditText celular;
    TextView errorCe;
    Button guardar;
    String error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_login);
        dueno = (EditText) findViewById(R.id.duenologin);
        errorDu = (TextView)  findViewById (R.id.errorDueno);
        usuario = (EditText) findViewById(R.id.usuariologin);
        errorUs= (TextView)  findViewById (R.id.errorUsuario);
        contrasena = (EditText) findViewById(R.id.contrasenalogin);
        errorCo= (TextView)  findViewById (R.id.errorContrasena);
        mail = (EditText) findViewById(R.id.maillogin);
        errorMa= (TextView)  findViewById (R.id.errorMail);
        domicilio = (EditText) findViewById(R.id.domiciliologin);
        localidad = (EditText) findViewById(R.id.localidadlogin);
        celular = (EditText) findViewById(R.id.celularlogin);
        errorCe= (TextView)  findViewById (R.id.errorCelular);
        guardar = (Button) findViewById(R.id.GuardarLogin);
        guardar.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                errorDu.setVisibility(View.GONE);
                errorUs.setVisibility(View.GONE);
                errorCo.setVisibility(View.GONE);
                errorMa.setVisibility(View.GONE);
                errorCe.setVisibility(View.GONE);
                if (validacion()) {
                  //  guardar_login();
                } else {
                    Snackbar.make(findViewById(android.R.id.content), " " + error,
                            Snackbar.LENGTH_LONG)
                            .setDuration(3000)
                            .show();


                }
            }
        });


    }

    private boolean validarUsuario(String usu) {
        String dbUsuario;
        if (usu.length()>3){
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioLogin.this, "bd_perros", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  usuarios", null);
        int count = 0;
        while (cursor.moveToNext()) {
            dbUsuario = cursor.getString(1);
            if (usu.equals(dbUsuario)) {
                count++;
            }
        }
        if (count > 0) {
            errorUs.setVisibility(View.VISIBLE);
            return false;

        } else {
            return true;
        }
        }else {
            errorUs.setVisibility(View.VISIBLE);
            return false;
        }
    }


    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;//LIBRERIA QUE VALIDA LOS MAILS
        if (!pattern.matcher(email).matches()){
            
            error= "La Contraseña debe tener al menos: 6 caracteres, 1 minúscula, 1 mayúscula, 1 número y 1 signo";
        }
        return pattern.matcher(email).matches();
    }


    private boolean validarContrasena (String contrasena){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{6,}$");
        if (!contrasena.matches(String.valueOf(pattern))){
            error= "El mail debe respetar formato con @ y .com";
        }

        return contrasena.matches(String.valueOf(pattern));
    }



    private boolean validacion() {
        String duen = dueno.getText().toString();
        String usu = usuario.getText().toString();
        String contra = contrasena.getText().toString();
        String ml = mail.getText().toString();
        String dom = domicilio.getText().toString();
        String loc = localidad.getText().toString();
        String cel = celular.getText().toString();

        if (!duen.equals("")) {
          if (!usu.equals("")&& validarUsuario(usu)) {
              if (!contra.equals("")&& validarUsuario(contra)) {
                  if (!ml.equals("")&& validarUsuario(ml)) {
                      if (!cel.equals("")&& validarUsuario(cel)){

                      }else{
                          errorCe.setVisibility(View.VISIBLE);
                          return false;
                      }

                  }else{
                      errorMa.setVisibility(View.VISIBLE);
                      return false;
                  }

              }else{
                  errorCo.setVisibility(View.VISIBLE);
                  return false;

              }

          }else{
              errorUs.setVisibility(View.VISIBLE);
              return false;
          }

        } else {
            errorDu.setVisibility(View.VISIBLE);
            return false;
        }
     return true;
    }


   /* private void guardar_login() {
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
        Utilidades.usaurioLog = (Integer) idresultante.intValue();
        System.out.println("el valor de idresultante es:" + Utilidades.usaurioLog);
        Toast.makeText(this, "Usuario Ingresado" + idresultante, Toast.LENGTH_LONG).show();
        Intent intento2 = new Intent(FormularioLogin.this, FormularioRegistro.class); // configuro para que vaya a la otra pantalla
        startActivity(intento2);//con esto va a turno

        finish();


    }*/


}




