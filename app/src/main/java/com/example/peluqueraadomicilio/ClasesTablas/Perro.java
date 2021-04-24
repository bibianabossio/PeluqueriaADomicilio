package com.example.peluqueraadomicilio.ClasesTablas;

public class Perro {
    private Integer id;
    private Integer id_dueno;
    private String nombre_mascota;
    private String raza;
    private Integer kg;
    private String foto;


    public Perro(Integer id, Integer id_dueno, String nombre_mascota, String raza, Integer kg, String foto) {
        this.id = id;
        this.id_dueno= id_dueno;
        this.nombre_mascota = nombre_mascota;
        this.raza = raza;
        this.kg = kg;
        this.foto = foto;
    }

    public Perro() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_dueno() {
        return id;
    }

    public void setId_dueno(Integer id) {
        this.id = id;
    }


    public String getNombre_mascota() {
        return nombre_mascota;
    }

    public void setNombre_mascota(String nombre_mascota) {
        this.nombre_mascota = nombre_mascota;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Integer getKg() {
        return kg;
    }

    public void setKg(Integer kg) {
        this.kg = kg;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
