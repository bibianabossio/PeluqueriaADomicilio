package com.example.peluqueraadomicilio.Utilidades;

public class Utilidades {
    public static Integer usaurioLog;
    public static Integer perroLog;


    public static final String TABLA_USUARIO="usuarios";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_USUARIO="usuario";
    public static final String CAMPO_CONTRASENA="contrasena";
    public static final String CAMPO_NOMBRE_DUENO= "nombre_dueno";
    public static final String CAMPO_MAIL="mail";
    public static final String CAMPO_DOMICILIO="domicilio";
    public static final String CAMPO_LOCALIDAD="localidad";
    public static final String CAMPO_CELULAR="celular";

    public static final String TABLA_PERRO="perros";
    public static final String CAMPO_PERRO_ID="id";
    public static final String CAMPO_ID_DUENO="id_dueno";
    public static final String CAMPO_NOMBRE_MASCOTA= "nombre_mascota";
    public static final String CAMPO_RAZA= "raza";
    public static final String CAMPO_KG= "kg";
    public static final String CAMPO_FOTO= "foto";

    public static final String TABLA_TURNO="turnos";
    public static final String CAMPO_TURNO_ID="id";
    public static final String CAMPO_FECHA="fecha";
    public static final String CAMPO_HORARIO= "horario";
    public static final String CAMPO_ID_DE_PERRO="id_perro";;
    public static final String CAMPO_DUENO_ID="id_dueno";


   public static final  String CREAR_TABLA_PERRO = "CREATE TABLE " + TABLA_PERRO+ "(" + CAMPO_PERRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CAMPO_ID_DUENO +" INTEGER," + CAMPO_NOMBRE_MASCOTA +" TEXT," + CAMPO_RAZA + " TEXT," +CAMPO_KG+ " INTEGER," +CAMPO_FOTO+ " TEXT)";
   public static final String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_USUARIO+ " (" + CAMPO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "  + CAMPO_USUARIO +" TEXT, " + CAMPO_CONTRASENA + " INTEGER," + CAMPO_NOMBRE_DUENO + " TEXT," + CAMPO_MAIL + " TEXT," + CAMPO_DOMICILIO + " TEXT, " + CAMPO_LOCALIDAD + " TEXT, " + CAMPO_CELULAR +  " TEXT)";
   public static final String CREAR_TABLA_TURNO = "CREATE TABLE "+ TABLA_TURNO+ " (" +CAMPO_TURNO_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_FECHA +"   TEXT, " + CAMPO_HORARIO + " TEXT, " + CAMPO_ID_DE_PERRO + " INTEGER," +CAMPO_DUENO_ID +" INTEGER)";

}

