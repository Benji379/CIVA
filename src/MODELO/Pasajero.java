package MODELO;

import java.io.Serializable;

public class Pasajero implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para mantener la compatibilidad
    private String dni;
    private String nombre;
    private String apellido;
    private String genero;
    private String fecha;

    public Pasajero(String dni, String nombre, String apellido, String genero, String fecha) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.genero = genero;
        this.fecha = fecha;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getGenero() {
        return genero;
    }

    public String getFecha() {
        return fecha;
    }

}
