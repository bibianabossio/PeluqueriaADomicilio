package com.example.peluqueraadomicilio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {
    TextView validacion;
    Button mascota;
    Button solicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(Inicio.this, "bd_perros", null, 1);
        validacion = (TextView) findViewById(R.id.validacion); //relaciono los campos
        mascota = (Button) findViewById(R.id.mascotabtn);
        solicitud = (Button) findViewById(R.id.solicitud_btn);

        Intent intento = getIntent();
        validacion.setText(intento.getStringExtra("user")); //validacion de usuario y contraseña


        mascota.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            public void onClick(View v) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(Inicio.this, "bd_usuarios", null, 1);
                Intent intento1 = new Intent(Inicio.this, FormularioLogin.class); // configuro para que vaya a la otra pantalla
                startActivity(intento1);//con esto va al formulario registro
            }
        });
        solicitud.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(Inicio.this, "bd_turnos", null, 1);
                Intent intento2 = new Intent(Inicio.this, FormularioTurno.class); // configuro para que vaya a la otra pantalla
                startActivity(intento2);//con esto va a turno

            }
        });


    }
}
