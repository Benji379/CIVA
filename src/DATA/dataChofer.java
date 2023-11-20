package DATA;

import MODELO.Chofer;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JTable;

public class dataChofer {

    private static final String URL_GUARDADO = "data//chofer.bin";

    // Cambiamos a LinkedHashMap para mantener el orden de inserción.
    private LinkedHashMap<String, Chofer> hashChofer;

    public dataChofer() {
        hashChofer = new LinkedHashMap<>();
        cargarChoferDesdeArchivo(); // Método para cargar choferes al inicio desde el archivo .bin
    }

    public void registrar(Chofer chofer) {
        hashChofer.put(chofer.getDni(), chofer);
        guardarEnArchivo();
    }

    public void actualizar(Chofer chofer) {
        hashChofer.put(chofer.getDni(), chofer);
        guardarEnArchivo();
    }

    public void borrar(String dniChofer) {
        hashChofer.remove(dniChofer);
        guardarEnArchivo();
    }

    public void consultar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0); // Limpia el modelo para nuevos datos

        // Suponiendo que 'hashChofer' es tu LinkedHashMap y que mantienes el orden de inserción
        int i = 0;
        for (Chofer chofer : hashChofer.values()) {
            // Los datos se agregarán en el modelo del JTable en el orden en que se iteran aquí
            i++;
            modelo.addRow(new Object[]{
                i,
                chofer.getDni(),
                chofer.getNombre(),
                chofer.getApellido(),
                chofer.getEstado(),
                chofer.getFecha()
            });
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(URL_GUARDADO))) {
            out.writeObject(hashChofer);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public ArrayList<Object> getConsultar(int columna) {
        ArrayList<Object> resultado = new ArrayList<>();
        for (Chofer chofer : hashChofer.values()) {
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
                    resultado.add(chofer.getEstado());
                    break;
                case 5:
                    resultado.add(chofer.getFecha());
                    break;
            }
        }
        return resultado;
    }

    public boolean existeChofer(String dni) {
        return hashChofer.containsKey(dni);
    }

    @SuppressWarnings("unchecked")
    private void cargarChoferDesdeArchivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(URL_GUARDADO))) {
            // Primero leemos el objeto como HashMap, ya que así fue originalmente guardado.
            Object readObject = in.readObject();
            if (readObject instanceof HashMap) {
                // Creamos un nuevo LinkedHashMap para mantener el orden de inserción.
                hashChofer = new LinkedHashMap<>((HashMap<String, Chofer>) readObject);
            } else {
                throw new IllegalArgumentException("Tipo de objeto no esperado en el archivo: " + readObject.getClass());
            }
        } catch (FileNotFoundException e) {
            hashChofer = new LinkedHashMap<>(); // Si no se encuentra el archivo, inicializamos un nuevo LinkedHashMap
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            hashChofer = new LinkedHashMap<>(); // En caso de otro error, también inicializamos un nuevo LinkedHashMap
        }
    }

}
