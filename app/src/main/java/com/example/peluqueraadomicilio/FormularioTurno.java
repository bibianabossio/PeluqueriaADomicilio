package com.example.peluqueraadomicilio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;

import java.util.Calendar;


public class FormularioTurno extends AppCompatActivity {
TextView formulario;
Button calendario;
TextView horario;
Button reloj;
Button guardar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_turno);
        formulario = (TextView) findViewById(R.id.formularioTurno);
        calendario = (Button) findViewById(R.id.button);
        horario = (TextView) findViewById(R.id.horario);
        reloj = (Button) findViewById(R.id.button2);
        guardar = (Button) findViewById(R.id.guardabtn);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
        }
        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                guardarTurno();
            }

        });
    }

    public void abrirCalendario(View view) {
        Calendar cal= Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        int mes= cal.get (Calendar.MONTH);
        int dia= cal.get (Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd= new DatePickerDialog(FormularioTurno.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha=dayOfMonth + "/"+ (month +1) + "/" + year;
                formulario.setText("Se reservo turno el día:"+ fecha);

            }
        }, anio, mes, dia);
        dpd.show();
    }

    public void abrirHora(View view) {
        Calendar c= Calendar.getInstance();
        int hora= c.get(Calendar.HOUR_OF_DAY);
        int min= c.get(Calendar.MINUTE);

        TimePickerDialog tmd= new TimePickerDialog(FormularioTurno.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horario.setText("En el horario: "+hourOfDay + ":"+ minute);

            }
        }, hora, min,false);
        tmd.show();
    }

    private void guardarTurno() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioTurno.this, "bd_perros", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fecha", formulario.getText().toString());//el valor que se asigne se guarda en la bd
        values.put("horario", horario.getText().toString());
        values.put(Utilidades.CAMPO_ID_DE_PERRO, Utilidades.perroLog );
        values.put(Utilidades.CAMPO_DUENO_ID, Utilidades.usaurioLog );
        Long idresultante = db.insert("turnos", "id", values);
        try {   Toast.makeText(this, "Turno confirmado" + idresultante, Toast.LENGTH_LONG).show();//al usuario por app

        }catch (Exception e){
            Toast.makeText(this, "El error es:" + e, Toast.LENGTH_LONG).show();//al usuario por app
            System.out.println("////////////////////////////////////"+ e);
        }


        String mensaje= formulario.getText().toString() + horario.getText().toString();
        sendSMS(mensaje);//al peluquero por sms
        Intent intento3 = new Intent(FormularioTurno.this, Inicio.class); // configuro para que vaya a la otra pantalla
        startActivity(intento3); //con esto va a turno

        finish();
    }
    //para cuando pruebo con el celular
    protected void sendSMS( String mensaje) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address"  , new String ("+5491135164255"));
        smsIntent.putExtra("sms_body"  , mensaje);


            startActivity(smsIntent);
            finish();

        }

}



