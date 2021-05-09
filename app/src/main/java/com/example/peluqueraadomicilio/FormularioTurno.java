package com.example.peluqueraadomicilio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FormularioTurno extends AppCompatActivity {
    TextView formulario;
    Button calendario;
    TextView horario;
    Button reloj;
    Button guardar;
    TextView confirmar;
    TextView cancelar;
    Button cancela;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_turno);
        formulario = (TextView) findViewById(R.id.formularioTurno);
        calendario = (Button) findViewById(R.id.button);
        horario = (TextView) findViewById(R.id.horario);
        reloj = (Button) findViewById(R.id.button2);
        guardar = (Button) findViewById(R.id.guardabtn4);
        confirmar = (TextView) findViewById(R.id.confirm);
        cancelar = (TextView) findViewById(R.id.cancel);
        cancela = (Button) findViewById(R.id.borrarbtn);
        cancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(FormularioTurno.this);//lo creo
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Desea cancelar la carga del turno ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(FormularioTurno.this, "Cancelado", Toast.LENGTH_SHORT).show();
                        new CountDownTimer(2000, 1000) {
                            public void onTick(long millisUntilFinished) {
                                cancelar.setVisibility(View.VISIBLE);
                            }

                            public void onFinish() {
                                finish();
                            }
                        }.start();


                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(FormularioTurno.this, "cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                dialogo1.show();
            }
        });


//para solicitar los permisos de mensaje de texto
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
                if(!formulario.getText().equals("") && !horario.getText().equals("")){
                    guardarTurno();
                }else{
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(FormularioTurno.this);//lo creo
                    dialogo1.setTitle("Importante!");
                    dialogo1.setMessage("Para registrar un turno todos los campos deben estar completo");
                    dialogo1.setCancelable(true);
                    dialogo1.show();

                }
            }


        });
    }

    public void abrirCalendario(View view) throws ParseException {

        Calendar cal= Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        int mes= cal.get (Calendar.MONTH);
        int dia= cal.get (Calendar.DAY_OF_MONTH);


        DatePickerDialog dpd= new DatePickerDialog(FormularioTurno.this,
                new DatePickerDialog.OnDateSetListener() {
                    String fechaActual=dia + "-"+ (mes +1) + "-" + anio;//almaceno la fecha de hoy

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date fechaHoy = format.parse(fechaActual);
                    java.util.Date fechaSeleccionada;
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String fecha=dayOfMonth + "-"+ (month +1) + "-" + year;//fecha seleccionada por el usuario lo conv en string
                        try {
                            fechaSeleccionada = format.parse(fecha);//convierto el string en fecha
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        formulario.setText("Se reservo turno el día:"+ fecha);

                    }

                },
                anio,
                mes,
                dia);
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

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
                confirmar.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(FormularioTurno.this, "bd_perros", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("fecha", formulario.getText().toString());//el valor que se asigne se guarda en la bd
                values.put("horario", horario.getText().toString());
                values.put(Utilidades.CAMPO_ID_DE_PERRO, Utilidades.perroLog);
                values.put(Utilidades.CAMPO_DUENO_ID, Utilidades.usaurioLog);
                Long idresultante = db.insert("turnos", "id", values);
                if (idresultante>0){//si no lo llegó a guardar
                    guardar.setEnabled(false);//deshabilito para que no lo vuelva a poner
                }
                else{
                    guardar.setEnabled(true);
                }

                String mensaje= formulario.getText().toString() + horario.getText().toString();
                      /* sendSMS(mensaje);//al peluquero por sms
                       Intent intento3 = new Intent(FormularioTurno.this, Inicio.class); // configuro para que vaya a la otra pantalla
                       startActivity(intento3); //con esto va a turno*/
                finish();// cuando lo ejecuto en el celular tengo que sacar este finish
            }
        }.start();



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







