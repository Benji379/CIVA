package DATA;

import MODELO.Pasajero;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JTable;

public class dataPasajerero {

    private static final String URL_GUARDADO = "data//pasajeros.bin";

    // Cambiamos a LinkedHashMap para mantener el orden de inserción.
    private LinkedHashMap<String, Pasajero> hashPasajero;

    public dataPasajerero() {
        hashPasajero = new LinkedHashMap<>();
        cargarChoferDesdeArchivo(); // Método para cargar choferes al inicio desde el archivo .bin
    }

    public void registrar(Pasajero pasajero) {
        hashPasajero.put(pasajero.getDni(), pasajero);
        guardarEnArchivo();
    }

    public void actualizar(Pasajero pasajero) {
        hashPasajero.put(pasajero.getDni(), pasajero);
        guardarEnArchivo();
    }

    public void borrar(String dniPasajero) {
        hashPasajero.remove(dniPasajero);
        guardarEnArchivo();
    }

    public void consultar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpia el modelo para nuevos datos

        // Suponiendo que 'hashChofer' es tu LinkedHashMap y que mantienes el orden de inserción
        int i = 0;
        for (Pasajero pasajero : hashPasajero.values()) {
            // Los datos se agregarán en el modelo del JTable en el orden en que se iteran aquí
            i++;
            modelo.addRow(new Object[]{
                i,
                pasajero.getDni(),
                pasajero.getNombre(),
                pasajero.getApellido(),
                pasajero.getGenero(),
                pasajero.getFecha()
            });
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(URL_GUARDADO))) {
            out.writeObject(hashPasajero);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public ArrayList<Object> getConsultar(int columna) {
        ArrayList<Object> resultado = new ArrayList<>();
        for (Pasajero chofer : hashPasajero.values()) {
            switch (columna) {
                case 0:
                    resultado.add(chofer.getDni());
                    break;
                case 1:
                    resultado.add(chofer.getNombre());
                    break;
                case 3:
                    resultado.add(chofer.getApellido());
                    break;
                case 4:
                    resultado.add(chofer.getGenero());
                    break;
                case 5:
                    resultado.add(chofer.getFecha());
                    break;
            }
        }
        return resultado;
    }

    public boolean existeChofer(String dni) {
        return hashPasajero.containsKey(dni);
    }

    @SuppressWarnings("unchecked")
    private void cargarChoferDesdeArchivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(URL_GUARDADO))) {
            // Primero leemos el objeto como HashMap, ya que así fue originalmente guardado.
            Object readObject = in.readObject();
            if (readObject instanceof HashMap) {
                // Creamos un nuevo LinkedHashMap para mantener el orden de inserción.
                hashPasajero = new LinkedHashMap<>((HashMap<String, Pasajero>) readObject);
            } else {
                throw new IllegalArgumentException("Tipo de objeto no esperado en el archivo: " + readObject.getClass());
            }
        } catch (FileNotFoundException e) {
            hashPasajero = new LinkedHashMap<>(); // Si no se encuentra el archivo, inicializamos un nuevo LinkedHashMap
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            hashPasajero = new LinkedHashMap<>(); // En caso de otro error, también inicializamos un nuevo LinkedHashMap
        }
    }
}
