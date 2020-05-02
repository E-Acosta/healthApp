package com.everacosta.healthApp.Modelos;

public class contacto {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String cumpleaños;

    public contacto(int id,String nombre, String apellido, String telefono, String cumpleaños) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.cumpleaños = cumpleaños;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCumpleaños() {
        return cumpleaños;
    }

}
