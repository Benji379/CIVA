package DATA;

import MODELO.Ruta;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.JTable;

import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class dataRuta {

    private static final String URL_GUARDADO = "data//rutas.bin";
    private LinkedHashMap<String, Ruta> hashRutas;

    public dataRuta() {
        hashRutas = new LinkedHashMap<>();
        cargarRutasDesdeArchivo(); // Método para cargar rutas al inicio desde el archivo .bin
    }

    public void registrar(Ruta ruta) {
        hashRutas.put(ruta.getCodigoRuta(), ruta);
        guardarEnArchivo();
    }

    public void actualizar(Ruta ruta) {
        hashRutas.put(ruta.getCodigoRuta(), ruta); // Reemplaza la ruta existente con la nueva
        guardarEnArchivo();
    }

    public void borrar(String codigoRutaBorrar) {
        hashRutas.remove(codigoRutaBorrar);
        guardarEnArchivo();
    }

    public void consultar(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        modelo.setRowCount(0);
        int i = 0;
        for (Ruta ruta : hashRutas.values()) {
            i++;
            modelo.addRow(new Object[]{
                i,
                ruta.getCodigoRuta(),
                ruta.getPartida(),
                ruta.getDestino(),
                ruta.getCosto()}
            );
        }
    }

    private void guardarEnArchivo() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(URL_GUARDADO))) {
            out.writeObject(hashRutas);
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public ArrayList<Object> getConsultar(int columna) {
        Set<Object> resultSet = new HashSet<>(); // Usamos un HashSet para evitar duplicados
        for (Ruta ruta : hashRutas.values()) {
            switch (columna) {
                case 0: // Suponiendo que 0 es para el código de la ruta
                    resultSet.add(ruta.getCodigoRuta());
                    break;
                case 1: // Suponiendo que 1 es para la partida
                    resultSet.add(ruta.getPartida());
                    break;
                case 2: // Suponiendo que 2 es para el destino
                    resultSet.add(ruta.getDestino());
                    break;
                case 3: // Suponiendo que 3 es para el costo
                    resultSet.add(ruta.getCosto());
                    break;
                default:
                    // Manejo de un número de columna inválido
                    throw new IllegalArgumentException("Número de columna inválido");
            }
        }
        return new ArrayList<>(resultSet); // Convertimos el Set en ArrayList para mantener el tipo de retorno
    }

    public ArrayList<Object> getConsultarr(int columna) {
        ArrayList<Object> resultado = new ArrayList<>();
        for (Ruta ruta : hashRutas.values()) {
            switch (columna) {
                case 0: // Suponiendo que 0 es para el código de la ruta
                    resultado.add(ruta.getCodigoRuta());
                    break;
                case 1: // Suponiendo que 1 es para la partida
                    resultado.add(ruta.getPartida());
                    break;
                case 2: // Suponiendo que 2 es para el destino
                    resultado.add(ruta.getDestino());
                    break;
                case 3: // Suponiendo que 3 es para el costo
                    resultado.add(ruta.getCosto());
                    break;
                default:
                    // Manejo de un número de columna inválido
                    throw new IllegalArgumentException("Número de columna inválido");
            }
        }
        return resultado; // Convertimos el Set en ArrayList para mantener el tipo de retorno
    }

    public ArrayList<String> buscarDestino(String partidaBuscada) {
        ArrayList<String> destinosEncontrados = new ArrayList<>();
        // Recorremos todas las rutas en el LinkedHashMap
        for (Ruta ruta : hashRutas.values()) {
            // Comparamos la partida de cada ruta con la partida buscada
            if (ruta.getPartida().equalsIgnoreCase(partidaBuscada)) {
                // Si coincide, añadimos el destino a la lista
                destinosEncontrados.add(ruta.getDestino());
            }
        }
        // Devolvemos la lista de destinos encontrados
        return destinosEncontrados;
    }

    public Double buscarCostoPorPartidaYDestino(String partida, String destino) {
        for (Ruta ruta : hashRutas.values()) {
            // Comprobamos si tanto la partida como el destino coinciden
            if (ruta.getPartida().equalsIgnoreCase(partida) && ruta.getDestino().equalsIgnoreCase(destino)) {
                // Si es así, retornamos el costo asociado
                return ruta.getCosto();
            }
        }
        // Si no encontramos ninguna ruta que coincida, podemos retornar null o lanzar una excepción
        return null;
    }

    @SuppressWarnings("unchecked")
    private void cargarRutasDesdeArchivo() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(URL_GUARDADO))) {
            // Primero leemos el objeto como HashMap, ya que así fue originalmente guardado.
            Object readObject = in.readObject();
            if (readObject instanceof HashMap) {
                // Creamos un nuevo LinkedHashMap para mantener el orden de inserción.
                hashRutas = new LinkedHashMap<>((HashMap<String, Ruta>) readObject);
            } else {
                throw new IllegalArgumentException("Tipo de objeto no esperado en el archivo: " + readObject.getClass());
            }
        } catch (FileNotFoundException e) {
            hashRutas = new LinkedHashMap<>(); // Si no se encuentra el archivo, inicializamos un nuevo LinkedHashMap
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            hashRutas = new LinkedHashMap<>(); // En caso de otro error, también inicializamos un nuevo LinkedHashMap
        }
    }
}

// La clase para el nodo del árbol
class TreeNode {

    Ruta ruta;
    TreeNode left;
    TreeNode right;

    TreeNode(Ruta ruta) {
        this.ruta = ruta;
        this.left = null;
        this.right = null;
    }
}

// La clase para el árbol de búsqueda binario
class BinarySearchTree {

    private TreeNode root;

    public void insert(Ruta ruta) {
        root = insertRec(root, ruta);
    }

    private TreeNode insertRec(TreeNode root, Ruta ruta) {
        if (root == null) {
            root = new TreeNode(ruta);
            return root;
        }
        if (ruta.getPartida().compareTo(root.ruta.getPartida()) < 0) {
            root.left = insertRec(root.left, ruta);
        } else if (ruta.getPartida().compareTo(root.ruta.getPartida()) > 0) {
            root.right = insertRec(root.right, ruta);
        }
        return root;
    }

    /**
     * *
     *
     * @param partida En el método buscarDestino de la clase BinarySearchTree,
     * la cola se utiliza para realizar una búsqueda en amplitud (BFS) a través
     * del árbol binario.
     * @return
     */
    public ArrayList<String> buscarDestino(String partida) {
        ArrayList<String> destinos = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root); // Añadimos la raíz al principio de la cola

        // Mientras la cola no esté vacía, seguimos buscando en el árbol
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll(); // Extraemos el elemento frontal de la cola
            if (current != null) {
                // Si la partida del nodo actual coincide con la buscada, añadimos su destino
                if (current.ruta.getPartida().equals(partida)) {
                    destinos.add(current.ruta.getDestino());
                }
                // Añadimos el hijo izquierdo a la cola si existe
                if (current.left != null) {
                    queue.add(current.left);
                }
                // Añadimos el hijo derecho a la cola si existe
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
        }
        return destinos; // Devolvemos la lista de destinos encontrados
    }

}
