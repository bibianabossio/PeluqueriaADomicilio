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
import android.os.Handler;
import android.provider.CalendarContract;
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
import java.util.GregorianCalendar;


public class FormularioTurno extends AppCompatActivity {
    TextView formulario;
    Button calendario;
    TextView horario;
    Button reloj;
    Button guardar;
    TextView confirmar;
    TextView cancelar;
    Button cancela;
    java.util.Date fechaSeleccionada;
    java.util.Date fechaHoy;
    Boolean errorFecha=false;
    String fecha;



    java.util.Date horaSeleccionada;
    java.util.Date horaAhora;
    Boolean errorHora=false;
    String hora1;
    boolean mismoDia=false;


    //variables para guardar calendario
    int anio=0;
    int mes= 0;
    int dia=0;

    int anioSeleccionada=0;
    int mesSeleccionada= 0;
    int diaSeleccionada=0;

    int horaParaCal=0;
    int minParaCal=0;


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
        reloj.setEnabled(false);
        cancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(FormularioTurno.this);//lo creo
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("?? Desea cancelar la carga del turno ?");
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
                    if(errorFecha==true || errorHora==true){
                        String mensaje ="Seleccione datos correctos";
                        tiempo(mensaje);
                    }else {
                        guardarTurno();
                    }

                }else{
                    String mensaje= "Para registrar un turno todos los campos deben estar completos";
                    tiempo(mensaje);


                }
            }


        });
    }

    public void abrirCalendario(View view) throws ParseException {

        Calendar cal= Calendar.getInstance();
        anio= cal.get(Calendar.YEAR);
        mes= cal.get (Calendar.MONTH);
        dia= cal.get (Calendar.DAY_OF_MONTH);
        String fechaActual=dia + "-"+ (mes +1) + "-" + anio;//almaceno la fecha de hoy

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        fechaHoy = format.parse(fechaActual);
        DatePickerDialog dpd= new DatePickerDialog(FormularioTurno.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        anioSeleccionada=year;
                        mesSeleccionada=month;
                        diaSeleccionada=dayOfMonth;
                        fecha=dayOfMonth + "-"+ (month +1) + "-" + year;//fecha seleccionada por el usuario lo conv en string

                        try {
                            fechaSeleccionada = format.parse(fecha);//convierto el string en fecha
                            validarFecha();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
                },anio,mes,dia);
        dpd.show();

    }
    void validarFecha(){
        if(fechaHoy.compareTo(fechaSeleccionada)>=0){
            String mensaje= "Los turnos se asignan a partir del siguiente d??a";
            tiempo(mensaje);
            errorFecha=true;
            reloj.setEnabled(false);

        }else{
            formulario.setText("Se reservo turno el d??a:"+ fecha);
            errorFecha=false;
            reloj.setEnabled(true);
        }
    }

    public void abrirHora(View view) throws ParseException {

        Calendar c= Calendar.getInstance();

        int hora= c.get(Calendar.HOUR_OF_DAY);
        int min= c.get(Calendar.MINUTE);


        String horaActual=(hora+1) + ":"+ min;//almaceno lahora de ahora

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        horaAhora = format.parse(horaActual);//captura la hora actual

        TimePickerDialog tmd= new TimePickerDialog(FormularioTurno.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaParaCal=hourOfDay;
                minParaCal=minute;
                hora1=hourOfDay+":"+minute;
                try {
                    horaSeleccionada = format.parse(hora1);//captura la hora seleccionada
                    validarHora();
                    hora1= format.format(horaSeleccionada);
                    horario.setText("Horario seleccionado: "+ hora1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, hora, min,false);
        tmd.show();
    }
    void validarHora() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        java.util.Date horarioInicio=format.parse("09:00");
        java.util.Date horarioFinal=format.parse("18:00");

        if (horaSeleccionada.after(horarioInicio) && horaSeleccionada.before(horarioFinal)){
            errorHora=false;
        }else {
            String mensaje= "Debe seleccionar un horario de 09:00 a 18:00";
            tiempo(mensaje);
            errorHora=true;
        }
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

                agregarEventoCalendario();

                if (idresultante>0){//si no lo lleg?? a guardar
                    guardar.setEnabled(false);//deshabilito para que no lo vuelva a poner
                }
                else{
                    guardar.setEnabled(true);
                }
               // finish();

                String mensaje= formulario.getText().toString() +" "+ horario.getText().toString();
                sendSMS(mensaje);//al peluquero por sms
                // Intent intento3 = new Intent(FormularioTurno.this, Inicio.class); // configuro para que vaya a la otra pantalla
                //startActivity(intento3); //con esto va a turno
                 finish();// cuando lo ejecuto en el celular tengo que sacar este finish
            }
        }.start();



    }

    public void tiempo(String mensaje){
        AlertDialog.Builder dialog = new AlertDialog.Builder(FormularioTurno.this);
        dialog.setCancelable(false);
        dialog.setTitle("??Atenci??n!");
        dialog.setMessage(mensaje);
        final AlertDialog alert = dialog.create();
        alert.show();  //Muestra dialogo.

        //Crea handler, en 4  segundos cierra el dialogo.
        new Handler().postDelayed(new Runnable(){
            public void run(){
                if (alert.isShowing()) {
                    alert.dismiss();
                }
            }
        }, 4000);
    }

    public void agregarEventoCalendario(){
        Intent calIntent = new Intent(Intent.ACTION_INSERT);//crea intento de insertar
        calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, "Turno Peluqueria Canina");
        calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Local Av Santa Fe 1212");
        calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "El turno se encuentra reservado , favor de ser puntual");

        Calendar horaInicio = Calendar.getInstance();//obtengo la instancia
        horaInicio.set(anioSeleccionada, mesSeleccionada, diaSeleccionada,horaParaCal,minParaCal);//creo variable del turno
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);//aclara que el evento no del dia entero

        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, horaInicio.getTimeInMillis());//inserto los datos en el calendario

        Calendar horaFin = Calendar.getInstance();//genero la hora del fin del turno
        horaFin.set(anioSeleccionada, mesSeleccionada, diaSeleccionada,horaParaCal+1,minParaCal);//asumo que el turno es de 1 hora
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,horaFin.getTimeInMillis());

        calIntent.putExtra(Intent.EXTRA_EMAIL, "rolon2702@gmail.com");//due??a de la peluqueria para que le llegue mail
        startActivity(calIntent);

    }

    //para cuando pruebo con el celular
    protected void sendSMS( String mensaje) {
//nueva forma de enviar mensaje con el share
        Intent intentoObj=new Intent();
        intentoObj.setAction(Intent.ACTION_SEND);
        intentoObj.putExtra(Intent.EXTRA_TEXT,mensaje);
        intentoObj.setType("text/plain");
        Intent shareInt=Intent.createChooser(intentoObj,null);
        startActivity(shareInt);

        ///antigua fornma de enviar mensaje
      /*
      Intent smsIntent = new Intent(Intent.ACTION_VIEW);

      smsIntent.setData(Uri.parse("smsto:"));//deja preparado el mensaje para que lo envie
      smsIntent.setType("vnd.android-dir/mms-sms");
      smsIntent.putExtra("address"  , new String ("+5491135164255"));
      smsIntent.putExtra("sms_body"  , mensaje);
      //tengo que encontrar como enviar el mensaje.

      startActivity(smsIntent);

       */
        //finish();

    }
}

















