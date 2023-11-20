package MODELO;

import java.io.Serializable;

public class Ruta implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para mantener la compatibilidad
    private String codigoRuta;
    private String partida;
    private String destino;
    private double costo;

    public Ruta(String codigoRuta, String partida, String destino, double costo) {
        this.codigoRuta = codigoRuta;
        this.partida = partida;
        this.destino = destino;
        this.costo = costo;
    }

    public String getCodigoRuta() {
        return codigoRuta;
    }

    public String getPartida() {
        return partida;
    }

    public String getDestino() {
        return destino;
    }

    public double getCosto() {
        return costo;
    }
    
    
    
}
