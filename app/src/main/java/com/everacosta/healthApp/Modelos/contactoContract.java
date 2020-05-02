package com.everacosta.healthApp.Modelos;

public class contactoContract {


    public static final String TABLE_NAME = "registros";
    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String APELLIDO = "apellido";
    public static final String TELEFONO = "telefono";
    public static final String CUMPLEAÑOS = "cumpleaños";
    static final String CREAR_TABLA_CONTACTO = "CREATE TABLE "+TABLE_NAME+"(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NOMBRE + " TEXT," + APELLIDO +
            " TEXT," + TELEFONO + " TEXT," + CUMPLEAÑOS + " TEXT)";
}
