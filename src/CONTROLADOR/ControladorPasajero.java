package CONTROLADOR;

import DATA.dataPasajerero;
import MODELO.ConsultaDNI;
import MODELO.Pasajero;
import MODELO.UIController;
import VISTA.moduloPasajero;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorPasajero {

    moduloPasajero p;
    dataPasajerero data;

    public ControladorPasajero(moduloPasajero c) {
        this.p = c;
        data = new dataPasajerero();
    }

    public void initDiseño() {
        UIController.transparentarTxtField(p.txtDni, p.txtNombre, p.txtApellido);
        limpiar();
    }

    public void limpiar() {
        filaSeleccionada = -1;
        p.tablaPasajero.clearSelection();
        p.txtDni.setText("");
        p.txtNombre.setText("");
        p.txtApellido.setText("");
        p.comboGenero.setSelectedIndex(0);
        p.txtDni.setEditable(true);
        p.comboGenero.setEnabled(true);
        consultar();
    }

    public void consultar() {
        data.consultar(p.tablaPasajero);
    }

    public void exportar() {
        DefaultTableModel mol = (DefaultTableModel) p.tablaPasajero.getModel();
        UIController.exportarTablaXlsx(mol, "Pasajeros", new Color(0, 204, 204));
    }

    public void eliminar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar Eliminacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                String dni = UIController.datoFilaColumna(p.tablaPasajero, filaSeleccionada, "dni").toString();
                data.borrar(dni);
                limpiar();
            }
        }
    }

    public void registrar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar registro", "Confirmacion", msj);
        if (confi == 0) {
            if (!data.existeChofer(p.txtDni.getText())) {
                if (!p.txtDni.getText().equalsIgnoreCase("")) {
                    String dni = p.txtDni.getText();
                    ConsultaDNI cons = new ConsultaDNI(dni);
                    String nombre = cons.getNombres();
                    String apellido = cons.getApellidos();
                    String genero = p.comboGenero.getSelectedItem().toString();
                    String fecha = UIController.obtenerFechaActualEnFormato();

                    Pasajero pasajero = new Pasajero(dni, nombre, apellido, genero, fecha);
                    data.registrar(pasajero);
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(null, "Ingrese los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Este chofer ya esta registrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    int filaSeleccionada = -1;

    public void clickTabla() {
        filaSeleccionada = p.tablaPasajero.getSelectedRow();
        String dni = UIController.datoFilaColumna(p.tablaPasajero, filaSeleccionada, "dni").toString();
        String nombre = UIController.datoFilaColumna(p.tablaPasajero, filaSeleccionada, "nombre").toString();
        String apellido = UIController.datoFilaColumna(p.tablaPasajero, filaSeleccionada, "apellido").toString();
        String pasajero = UIController.datoFilaColumna(p.tablaPasajero, filaSeleccionada, "pasajero").toString();

        p.txtDni.setText(dni);
        p.txtNombre.setText(nombre);
        p.txtApellido.setText(apellido);
        p.comboGenero.setSelectedItem(pasajero);

        p.txtDni.setEditable(false);
        p.comboGenero.setEnabled(false);
    }

    public void txtDniKeyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ConsultaDNI consultDni = new ConsultaDNI(p.txtDni.getText());
            if (consultDni.getValido()) {
                p.txtNombre.setText(consultDni.getNombres());
                p.txtApellido.setText(consultDni.getApellidos());
            } else {
                JOptionPane.showMessageDialog(null, "Dni inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                p.txtNombre.setText("");
                p.txtApellido.setText("");
            }
        }
    }

}
