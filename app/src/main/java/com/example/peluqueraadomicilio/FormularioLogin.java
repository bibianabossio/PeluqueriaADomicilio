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
    TextView errorNo;



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
                limpiarCampos();
                if (validacion()) {//ejecuta la funcion validacion
                     guardar_login();

                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Faltan completar campos ",
                            Snackbar.LENGTH_LONG)
                            .setDuration(3000)
                            .show();


                }
            }
        });


    }
    public void limpiarCampos(){
        errorDu.setVisibility(View.GONE);
        errorUs.setVisibility(View.GONE);
        errorCo.setVisibility(View.GONE);
        errorMa.setVisibility(View.GONE);
        errorCe.setVisibility(View.GONE);

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
                errorUs.setText("Usuario existente");
                errorUs.setVisibility(View.VISIBLE);
                return false;

            } else {
                return true;
            }
        }else {
            errorUs.setText("El usuario debe contener al menos 4 digitos");
            errorUs.setVisibility(View.VISIBLE);
            return false;
        }
    }



    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;//LIBRERIA QUE VALIDA LOS MAILS
        if (!pattern.matcher(email).matches()){

            error= "El mail debe respetar formato con @ y .com";

        }
        return pattern.matcher(email).matches();
    }


    private boolean validarContrasena (String contrasena){//controla que la estructura de mail tenga los campos necesarios
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!.])(?=\\S+$).{6,}$");
        if (!contrasena.matches(String.valueOf(pattern))){
            errorCo.setText("La Contraseña debe tener al menos: 6 caracteres, 1 minúscula, 1 mayúscula, 1 número y 1 signo");
            errorCo.setVisibility(View.VISIBLE);

        }
        return contrasena.matches(String.valueOf(pattern));
    }



    private boolean validacion() {
        String duen = dueno.getText().toString();
        String usu = usuario.getText().toString();
        String contra = contrasena.getText().toString();
        String ml = mail.getText().toString();
        String cel = celular.getText().toString();
        validarTotales();

        if (!duen.equals("")&& validarNombre(duen)) {
            if (validarUsuario(usu)) {
                if (!contra.equals("")&& validarContrasena(contra)) {
                    if (!ml.equals("")&& validarEmail(ml)) {
                        if (!cel.equals("")&& cel.length()>=10){

                        }else{
                            errorCe.setText("El campo celular debe contener al menos 10 numeros");
                            errorCe.setVisibility(View.VISIBLE);
                            return false;
                        }

                    }else{
                        errorMa.setVisibility(View.VISIBLE);
                        return false;
                    }

                }else{

                    return false;

                }

            }else{
                return false;
            }

        } else {
            errorDu.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }



    private boolean validarNombre (String nombre){//controla que la estructura de nombre tenga solo letras
        Pattern pattern = Pattern.compile("[a-zA-Z-ZñÑáéíóúÁÉÍÓÚ]+\\s[a-zA-Z-ZñÑáéíóúÁÉÍÓÚ\\s]+");
        if (!nombre.matches(String.valueOf(pattern))){
            errorDu.setText("Debe cargar Nombre y Apellido y solo se permiten letras");
            errorDu.setVisibility(View.VISIBLE);

        }
        return nombre.matches(String.valueOf(pattern));
    }

    public void validarTotales(){
        String duen = dueno.getText().toString();
        String usu = usuario.getText().toString();
        String contra = contrasena.getText().toString();
        String ml = mail.getText().toString();
        String cel = celular.getText().toString();
        //aca consultamos con todos los campos no esten vacios
        if (duen.equals("")){
            errorDu.setText("El campo Nombre y Apellido no puede estar vacío");
            errorDu.setVisibility(View.VISIBLE);
        }
        if (usu.equals("")){
            errorUs.setText("El Usuario no puede estar vacío");
            errorUs.setVisibility(View.VISIBLE);
        }
        if (contra.equals("")){
            errorCo.setText("La contraseña no puede estar vacío");
            errorCo.setVisibility(View.VISIBLE);
        }
        if (ml.equals("")){
            errorMa.setText("El campo mail no puede estar vacío");
            errorMa.setVisibility(View.VISIBLE);
        }
        if (cel.equals("")){
            errorCe.setText("El campo celular no puede estar vacío");
            errorCe.setVisibility(View.VISIBLE);
        }







    }


  private void guardar_login() {
       ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioLogin.this, "bd_perros", null, 1);
       SQLiteDatabase db = conn.getWritableDatabase();
       ContentValues values = new ContentValues();
       values.put("usuario", usuario.getText().toString().toLowerCase());//el valor que se asigne se guarda en la bd en minuscula
       values.put("contrasena", contrasena.getText().toString());
       values.put("nombre_dueno", dueno.getText().toString());
       values.put("mail", mail.getText().toString());
       values.put("domicilio", domicilio.getText().toString());
       values.put("localidad", localidad.getText().toString());
       values.put("celular", celular.getText().toString());
       Long idresultante = db.insert("usuarios", "id", values);//me tiene que devolver el id de la bd
       if (idresultante>0){//si no lo llegó a guardar
           guardar.setEnabled(false);//deshabilito para que no lo vuelva a poner
       }
       else{
           guardar.setEnabled(true);
       }
       Utilidades.usaurioLog = (Integer) idresultante.intValue();
       System.out.println("el valor de idresultante es:" + Utilidades.usaurioLog);
       Toast.makeText(this, "Usuario Ingresado" + idresultante, Toast.LENGTH_LONG).show();
       Intent intento2 = new Intent(FormularioLogin.this, FormularioRegistro.class); // configuro para que vaya a la otra pantalla
       startActivity(intento2);//con esto va a form registro

       finish();

   }


}





