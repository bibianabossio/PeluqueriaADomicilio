
package com.example.peluqueraadomicilio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peluqueraadomicilio.Utilidades.Utilidades;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class FormularioRegistro extends AppCompatActivity {
    //variables
    EditText nombreMascota;
    EditText raza;
    EditText kg;
    Button guardar;
    ImageView fotoPerro;
    Button galeria;
    Button camara;
    private Uri imagenUri;//uri es un formato para almacenar las fotos.
    int foto;
    private Bitmap imgToStorage;//otra forma de almacenar las fotos.
    String direccionUriImg;//almacena la dire donde guardo la imagen
    String error;
    TextView pesoerror;

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
        camara = (Button) findViewById(R.id.camarabtn);
        pesoerror=(TextView)findViewById(R.id.errorpeso);

        guardar.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                if (validarMascota()){
                    guardarMascota();
                } else {

                    pesoerror.setVisibility(View.VISIBLE);
                    pesoerror.setText(error);
                }
            }

        });
        galeria.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                guardarGaleria();
            }

        });

        camara.setOnClickListener(new View.OnClickListener() {// espera cuando el usuario hace click
            @Override
            public void onClick(View v) {
                AbrirCamara();
            }

        });

    }
    private boolean validarMascota() {
        String name = nombreMascota.getText().toString();
        String raze = raza.getText().toString();
        String kilo = kg.getText().toString();

        if (!name.equals("") && !raze.equals("") && !kilo.equals("")) {
            if(parseInt(kilo)>0 && parseInt(kilo)<111){
                return true;
            } else{
                error = "El peso debe ser entre 1 y 110kgs";
                return false;
            }
        }else {
            error = "Completar todos los campos";
            return false;
        }
    }


    private void guardarGaleria() {
        Intent intento = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intento.setType("image/");
        foto =10;//numero que se le asigna a la galeria
        startActivityForResult(Intent.createChooser(intento, "Seleccionar imagen ;)"), foto);

    }


    //funcion abrir camara para sacar las fotos
    public void AbrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//abre la camara
        File imagenArchivo = null;
        try {
            imagenArchivo = CrearImagen();// intento asignar los datos de la foto(nombre y donde se guarda)
        } catch (IOException ex) {
            Log.e("Error", ex.toString());// si no se genero el archivo por un error que me imprima
        }
        if (imagenArchivo != null) {//si tiene foto sacada, la guarda
            imagenUri = FileProvider.getUriForFile(this, "com.example.peluqueraadomicilio.fileprovider", imagenArchivo);
            foto =100;//numero que se asigna para la camara
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imagenUri);//guarda la imagen y lo manda a la linea siguiente
            startActivityForResult(intent, foto);

        }
    }
    //funcion crear imagen (nombre de la foto)
    private File CrearImagen() throws IOException {
        String nombreImagen = "Foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);// crea o guarda en la carpeta la foto
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);//concatena el nombre de la foto, donde se guarda y el formato
        direccionUriImg = imagen.getAbsolutePath();//asignamos a la variable la direccion adonde esta la foto guardada
        return imagen;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && foto ==10) { //cuando uso la galeria vale 10, cuando es camara vale 100
            Uri path = data.getData();
            direccionUriImg = path.toString();
            fotoPerro.setImageURI(path);
            Toast.makeText(this, "la imagen ya esta subida :" + path, Toast.LENGTH_LONG).show();
            //   textoET.setText(path.toString());
        }else if (resultCode == RESULT_OK && foto ==100){
            File imagenfile=new File(direccionUriImg);
            imgToStorage=BitmapFactory.decodeFile(imagenfile.getAbsolutePath());
            fotoPerro.setImageBitmap(imgToStorage);
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
        if (idresultante>0){//si no lo llegó a guardar
            guardar.setEnabled(false);//deshabilito para que no lo vuelva a poner
        }
        else{
            guardar.setEnabled(true);
        }
        Utilidades.perroLog = (Integer)idresultante.intValue();
        System.out.println("el valor de idresultante es:" + Utilidades.perroLog);
        Toast.makeText(this, "Mascota Ingresada" + idresultante, Toast.LENGTH_LONG).show();
        Intent intento3 = new Intent(FormularioRegistro.this, FormularioTurno.class); // configuro para que vaya a la otra pantalla
        startActivity(intento3); //con esto va a turno
        finish();



    }
}



