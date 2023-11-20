package CONTROLADOR;

import DATA.dataChofer;
import MODELO.Chofer;
import MODELO.ConsultaDNI;
import MODELO.UIController;
import VISTA.moduloChofer;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

public class ControladorChofer {

    moduloChofer c;
    dataChofer data;

    public ControladorChofer(moduloChofer c) {
        this.c = c;
        data = new dataChofer();
    }

    public void initDiseño() {
        UIController.transparentarTxtField(c.txtDni, c.txtNombre, c.txtApellido);
        limpiar();
    }

    public void limpiar() {
        filaSeleccionada = -1;
        c.tablaChofer.clearSelection();
        c.txtDni.setText("");
        c.txtNombre.setText("");
        c.txtApellido.setText("");
        c.comboEstado.setSelectedIndex(0);
        c.txtDni.setEditable(true);
        consultar();
    }

    public void consultar() {
        data.consultar(c.tablaChofer);
    }

    public void modificar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar Modificacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                String dni = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "dni").toString();
                String nombre = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "nombre").toString();
                String apellido = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "apellido").toString();
                String estado = c.comboEstado.getSelectedItem().toString();
                String fecha = UIController.obtenerFechaActualEnFormato();
                Chofer chofer = new Chofer(dni, nombre, apellido, estado, fecha);
                data.actualizar(chofer);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione una fila", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void eliminar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar Eliminacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                String dni = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "dni").toString();
                data.borrar(dni);
                limpiar();
            }
        }
    }

    public void registrar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar registro", "Confirmacion", msj);
        if (confi == 0) {
            if (!data.existeChofer(c.txtDni.getText())) {
                if (!c.txtDni.getText().equalsIgnoreCase("")) {
                    String dni = c.txtDni.getText();
                    ConsultaDNI cons = new ConsultaDNI(dni);
                    String nombre = cons.getNombres();
                    String apellido = cons.getApellidos();
                    String estado = c.comboEstado.getSelectedItem().toString();
                    String fecha = UIController.obtenerFechaActualEnFormato();

                    Chofer chofer = new Chofer(dni, nombre, apellido, estado, fecha);
                    data.registrar(chofer);
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
        filaSeleccionada = c.tablaChofer.getSelectedRow();
        String dni = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "dni").toString();
        String nombre = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "nombre").toString();
        String apellido = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "apellido").toString();
        String estado = UIController.datoFilaColumna(c.tablaChofer, filaSeleccionada, "estado").toString();

        c.txtDni.setText(dni);
        c.txtNombre.setText(nombre);
        c.txtApellido.setText(apellido);
        c.comboEstado.setSelectedItem(estado);

        c.txtDni.setEditable(false);
    }

    public void txtDniKeyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ConsultaDNI consultDni = new ConsultaDNI(c.txtDni.getText());
            if (consultDni.getValido()) {
                c.txtNombre.setText(consultDni.getNombres());
                c.txtApellido.setText(consultDni.getApellidos());
            } else {
                JOptionPane.showMessageDialog(null, "Dni inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                c.txtNombre.setText("");
                c.txtApellido.setText("");
            }
        }
    }

}
