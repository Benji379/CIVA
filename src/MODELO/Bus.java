package MODELO;

import java.io.Serializable;

public class Bus implements Serializable{

    private static final long serialVersionUID = 1L; // Recomendado para mantener la compatibilidad
    private String placa;
    private String idChofer;
    private String tipo;
    private String estado;
    private String fecha;

    public Bus(String placa, String idChofer, String tipo, String estado, String fecha) {
        this.placa = placa;
        this.idChofer = idChofer;
        this.tipo = tipo;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getPlaca() {
        return placa;
    }

    public String getIdChofer() {
        return idChofer;
    }

    public String getTipo() {
        return tipo;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }
    
    
    
    
}
