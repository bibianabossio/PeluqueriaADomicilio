package com.example.peluqueraadomicilio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;
import com.google.android.material.snackbar.Snackbar;

public class FormularioRegistro extends AppCompatActivity {
    //variables
    EditText nombreMascota;
    EditText raza;
    EditText kg;
    Button guardar;
    ImageView fotoPerro;
    Button galeria;
    String direccionUriImg;
    String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//se relacionan las variables con los id
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario__registro);
        nombreMascota = (EditText) findViewById(R.id.name_masc);
        raza = (EditText) findViewById(R.id.raza);
        kg = (EditText) findViewById(R.id.kg);
        guardar = (Button) findViewById(R.id.guardarbtn);
        fotoPerro = (ImageView) findViewById(R.id.foto_perro);
        galeria = (Button) findViewById(R.id.galeriabtn);
        guardar.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                if (validarMascota()){
                guardarMascota();
            } else {
                    Snackbar.make(findViewById(android.R.id.content), " " + error,
                            Snackbar.LENGTH_LONG)
                            .setDuration(3000)
                            .show();


                }
            }

        });
        galeria.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                guardarGaleria();
            }

        });

    }
    private boolean validarMascota() {
        String name = nombreMascota.getText().toString();
        String raze = raza.getText().toString();
        String kilo = kg.getText().toString();

        if (!name.equals("") && !raze.equals("") && !kilo.equals("")) {


                    return true;

            } else {
            error = "Completar todos los campos";
            return false;
        }
    }


    private void guardarGaleria() {
        Intent intento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intento.setType("image/");
        startActivityForResult(Intent.createChooser(intento, "Seleccionar imagen ;)"), 10);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri path = data.getData();
            direccionUriImg = path.toString();
            fotoPerro.setImageURI(path);
            Toast.makeText(this, "la imagen ya esta subida :" + path, Toast.LENGTH_LONG).show();
            //   textoET.setText(path.toString());
        }
    }

    private void guardarMascota() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioRegistro.this, "bd_perros", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID_DUENO, Utilidades.usaurioLog);
        values.put("nombre_mascota", nombreMascota.getText().toString());//el valor que se asigne se guarda en la bd
        values.put("raza", raza.getText().toString());
        values.put("kg", kg.getText().toString());
        values.put ("foto", direccionUriImg);
        Long idresultante = db.insert("perros", "id", values);
        Utilidades.perroLog = (Integer)idresultante.intValue();
        System.out.println("el valor de idresultante es:" + Utilidades.perroLog);
        Toast.makeText(this, "Mascota Ingresada" + idresultante, Toast.LENGTH_LONG).show();
        Intent intento3 = new Intent(FormularioRegistro.this, FormularioTurno.class); // configuro para que vaya a la otra pantalla
        startActivity(intento3); //con esto va a turno
        finish();



    }
}

