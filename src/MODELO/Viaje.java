package MODELO;

import java.io.Serializable;

public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L; // Recomendado para mantener la compatibilidad
    private String idViaje;
    private String placaBus;
    private String dniPasajero;
    private double costo;
    private String partida;
    private String destino;
    private String fecha;

    public Viaje(String idViaje, String placaBus, String dniPasajero, double costo, String partida, String destino, String fecha) {
        this.idViaje = idViaje;
        this.placaBus = placaBus;
        this.dniPasajero = dniPasajero;
        this.costo = costo;
        this.partida = partida;
        this.destino = destino;
        this.fecha = fecha;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public String getPlacaBus() {
        return placaBus;
    }

    public String getDniPasajero() {
        return dniPasajero;
    }

    public double getCosto() {
        return costo;
    }

    public String getPartida() {
        return partida;
    }

    public String getDestino() {
        return destino;
    }

    public String getFecha() {
        return fecha;
    }

}
