package DATA;

import MODELO.Viaje;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JTable;

public class dataViaje {

    private static final String URL_GUARDADO = "data//viajes.bin";

    // Cambiamos a LinkedHashMap para mantener el orden de inserción.
    private LinkedHashMap<String, Viaje> hashViaje;

    public dataViaje() {
        hashViaje = new LinkedHashMap<>();
        cargarChoferDesdeArchivo(); // Método para cargar choferes al inicio desde el archivo .bin
    }

    public void registrar(Viaje viaje) {
        hashViaje.put(viaje.getIdViaje(), viaje);
        guardarEnArchivo();
    }

    public void actualizar(Viaje viaje) {
        hashViaje.put(viaje.getIdViaje(), viaje);
        guardarEnArchivo();
    }

    public void borrar(String viaje) {
        hashViaje.remove(viaje);
        guardarEnArchivo();
    }

    public void consultar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpia el modelo para nuevos datos

        // Suponiendo que 'hashChofer' es tu LinkedHashMap y que mantienes el orden de inserción
        int i = 0;
        for (Viaje viaje : hashViaje.values()) {
            // Los datos se agregarán en el modelo del JTable en el orden en que se iteran aquí
            i++;
            modelo.addRow(new Object[]{
                i,
                viaje.getIdViaje(),
                viaje.getDniPasajero(),
                viaje.getCosto(),
                viaje.getPartida(),
                viaje.getDestino(),
                viaje.getPlacaBus(),
                viaje.getFecha()
            });
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(URL_GUARDADO))) {
            out.writeObject(hashViaje);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public ArrayList<Object> getConsultar(int columna) {
        ArrayList<Object> resultado = new ArrayList<>();
        for (Viaje viaje : hashViaje.values()) {
            switch (columna) {
                case 0:
                    resultado.add(viaje.getIdViaje());
                    break;
                case 1:
                    resultado.add(viaje.getDniPasajero());
                    break;
                case 2:
                    resultado.add(viaje.getCosto());
                    break;
                case 3:
                    resultado.add(viaje.getPartida());
                    break;
                case 4:
                    resultado.add(viaje.getDestino());
                    break;
                case 5:
                    resultado.add(viaje.getPlacaBus());
                    break;
                case 6:
                    resultado.add(viaje.getFecha());
                    break;
            }
        }
        return resultado;
    }

    public boolean existeChofer(String dni) {
        return hashViaje.containsKey(dni);
    }

    @SuppressWarnings("unchecked")
    private void cargarChoferDesdeArchivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(URL_GUARDADO))) {
            // Primero leemos el objeto como HashMap, ya que así fue originalmente guardado.
            Object readObject = in.readObject();
            if (readObject instanceof HashMap) {
                // Creamos un nuevo LinkedHashMap para mantener el orden de inserción.
                hashViaje = new LinkedHashMap<>((HashMap<String, Viaje>) readObject);
            } else {
                throw new IllegalArgumentException("Tipo de objeto no esperado en el archivo: " + readObject.getClass());
            }
        } catch (FileNotFoundException e) {
            hashViaje = new LinkedHashMap<>(); // Si no se encuentra el archivo, inicializamos un nuevo LinkedHashMap
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            hashViaje = new LinkedHashMap<>(); // En caso de otro error, también inicializamos un nuevo LinkedHashMap
        }
    }

}
