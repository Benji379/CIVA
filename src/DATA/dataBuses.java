package DATA;

import MODELO.Bus;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JTable;

public class dataBuses {

    private static final String URL_GUARDADO = "data//buses.bin";

    // Cambiamos a LinkedHashMap para mantener el orden de inserción.
    private LinkedHashMap<String, Bus> hashBuses;

    public dataBuses() {
        hashBuses = new LinkedHashMap<>();
        cargarChoferDesdeArchivo(); // Método para cargar choferes al inicio desde el archivo .bin
    }

    public void registrar(Bus bus) {
        hashBuses.put(bus.getPlaca(), bus);
        guardarEnArchivo();
    }

    public void actualizar(Bus bus) {
        hashBuses.put(bus.getPlaca(), bus);
        guardarEnArchivo();
    }

    public void borrar(String placaBus) {
        hashBuses.remove(placaBus);
        guardarEnArchivo();
    }

    public void consultar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpia el modelo para nuevos datos

        // Suponiendo que 'hashChofer' es tu LinkedHashMap y que mantienes el orden de inserción
        int i = 0;
        for (Bus bus : hashBuses.values()) {
            // Los datos se agregarán en el modelo del JTable en el orden en que se iteran aquí
            i++;
            modelo.addRow(new Object[]{
                i,
                bus.getPlaca(),
                bus.getTipo(),
                bus.getIdChofer(),
                bus.getEstado(),
                bus.getFecha()
            });
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(URL_GUARDADO))) {
            out.writeObject(hashBuses);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public ArrayList<Object> getConsultar(int columna) {
        ArrayList<Object> resultado = new ArrayList<>();
        for (Bus bus : hashBuses.values()) {
            switch (columna) {
                case 0:
                    resultado.add(bus.getPlaca());
                    break;
                case 1:
                    resultado.add(bus.getTipo());
                    break;
                case 3:
                    resultado.add(bus.getIdChofer());
                    break;
                case 4:
                    resultado.add(bus.getEstado());
                    break;
                case 5:
                    resultado.add(bus.getFecha());
                    break;
            }
        }
        return resultado;
    }

    public boolean existeChofer(String dni) {
        return hashBuses.containsKey(dni);
    }

    @SuppressWarnings("unchecked")
    private void cargarChoferDesdeArchivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(URL_GUARDADO))) {
            // Primero leemos el objeto como HashMap, ya que así fue originalmente guardado.
            Object readObject = in.readObject();
            if (readObject instanceof HashMap) {
                // Creamos un nuevo LinkedHashMap para mantener el orden de inserción.
                hashBuses = new LinkedHashMap<>((HashMap<String, Bus>) readObject);
            } else {
                throw new IllegalArgumentException("Tipo de objeto no esperado en el archivo: " + readObject.getClass());
            }
        } catch (FileNotFoundException e) {
            hashBuses = new LinkedHashMap<>(); // Si no se encuentra el archivo, inicializamos un nuevo LinkedHashMap
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            hashBuses = new LinkedHashMap<>(); // En caso de otro error, también inicializamos un nuevo LinkedHashMap
        }
    }

}
