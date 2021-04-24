package com.example.peluqueraadomicilio.ClasesTablas;

import java.util.Date;

public class Turno {
    private Integer id;
    private String fecha;
    private String horario;
    private Integer id_perro;
    private Integer id_dueno;

    public Turno(Integer id, String fecha, String horario, Integer id_perro, Integer id_dueno) {
        this.id = id;
        this.fecha = fecha;
        this.horario = horario;
        this.id_perro = id_perro;
        this.id_dueno = id_dueno;
    }

    public Turno() {

    }

    public Integer getId() {
        return id;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        final String fecha = this.fecha;
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getId_perro() {
        return id_perro;
    }

    public void setId_perro(Integer id_perro) {
        this.id_perro = id_perro;
    }

    public Integer getId_dueno() {
        return id_dueno;
    }

    public void setId_dueno(Integer id_dueno) {
        this.id_dueno = id_dueno;
    }
}
