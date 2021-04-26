package com.example.peluqueraadomicilio.ClasesTablas;

public class Usuario {
    private Integer id;
    private String usuario;
    private String contrasena;
    private String nombre_dueno;
    private String mail;
    private String domicilio;
    private String localidad;
    private String celular;

    public Usuario(Integer id,  String usuario, String contrasena, String nombre_dueno, String mail, String domicilio, String localidad, String celular) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombre_dueno = nombre_dueno;
        this.mail = mail;
        this.domicilio = domicilio;
        this.localidad = localidad;
        this.celular = celular;
    }

    public Usuario() {

    }
/*    public boolean isNull(){
        if(usuario.equals("")&&contrasena.equals("")&&nombre_dueno.equals("")&&mail.equals("")&&domicilio.equals("")&&localidad.equals("")&&celular.equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", contrasena=" + contrasena +
                ", nombre_dueno='" + nombre_dueno + '\'' +
                ", mail='" + mail + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", localidad='" + localidad + '\'' +
                ", celular='" + celular + '\'' +
                '}';
    }
*/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre_dueno() {
        return nombre_dueno;
    }

    public void setNombre_dueno(String nombre_dueno) {
        this.nombre_dueno = nombre_dueno;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
