package MODELO;

import java.io.Serializable;

public class Chofer implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para mantener la compatibilidad
    private String dni;
    private String nombre;
    private String apellido;
    private String estado;
    private String fecha;

    public Chofer(String dni, String nombre, String apellido, String estado, String fecha) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }
}
