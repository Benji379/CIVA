package CONTROLADOR;

import DATA.dataBuses;
import DATA.dataChofer;
import MODELO.Bus;
import MODELO.UIController;
import VISTA.moduloBus;
import javax.swing.JOptionPane;

public class ControladorBuses {

    moduloBus b;
    dataBuses data;

    public ControladorBuses(moduloBus c) {
        this.b = c;
        data = new dataBuses();
    }

    public void initDiseño() {
        UIController.transparentarTxtField(b.txtPlaca);
        limpiar();
    }

    public void limpiar() {
        filaSeleccionada = -1;
        b.tablaBus.clearSelection();
        b.txtPlaca.setText("");
//        b.comboChofer.setSelectedIndex(0);
        b.comboTipo.setSelectedIndex(0);
        b.comboEstado.setSelectedIndex(0);
        b.txtPlaca.setEditable(true);
        consultar();
    }

    public void consultar() {
        data.consultar(b.tablaBus);
        dataChofer dataChofer = new dataChofer();
        for (Object item : dataChofer.getConsultar(0)) {
            b.comboChofer.addItem(item);
        }
    }

    public void modificar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar Modificacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                String placa = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "placa").toString();
                String tipo = b.comboTipo.getSelectedItem().toString();
                String chofer = b.comboChofer.getSelectedItem().toString();
                String estado = b.comboEstado.getSelectedItem().toString();
                String fecha = UIController.obtenerFechaActualEnFormato();
                Bus bus = new Bus(placa, chofer, tipo, estado, fecha);
                data.actualizar(bus);
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
                String placa = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "placa").toString();
                data.borrar(placa);
                limpiar();
            }
        }
    }

    public void registrar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar registro", "Confirmacion", msj);
        if (confi == 0) {
            if (!data.existeChofer(b.txtPlaca.getText())) {
                if (!b.txtPlaca.getText().equalsIgnoreCase("")) {
                    String placa = b.txtPlaca.getText();
                    String tipo = b.comboTipo.getSelectedItem().toString();
                    String chofer = b.comboChofer.getSelectedItem().toString();
                    String estado = b.comboEstado.getSelectedItem().toString();
                    String fecha = UIController.obtenerFechaActualEnFormato();
                    Bus bus = new Bus(placa, chofer, tipo, estado, fecha);
                    data.registrar(bus);
                    limpiar();
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
        filaSeleccionada = b.tablaBus.getSelectedRow();
        String placa = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "placa").toString();
        String tipo = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "tipo").toString();
        String chofer = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "chofer").toString();
        String estado = UIController.datoFilaColumna(b.tablaBus, filaSeleccionada, "estado").toString();

        b.txtPlaca.setText(placa);
        b.comboTipo.setSelectedItem(tipo);
        b.comboChofer.setSelectedItem(chofer);
        b.comboEstado.setSelectedItem(estado);

        b.txtPlaca.setEditable(false);
    }

}
