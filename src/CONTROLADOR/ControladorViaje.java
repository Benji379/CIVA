package CONTROLADOR;

import DATA.dataBuses;
import DATA.dataPasajerero;
import DATA.dataRuta;
import DATA.dataViaje;
import MODELO.UIController;
import MODELO.Viaje;
import VISTA.moduloViaje;
import javax.swing.JOptionPane;

public class ControladorViaje {

    moduloViaje v;
    dataViaje data;

    public ControladorViaje(moduloViaje v) {
        this.v = v;
        data = new dataViaje();
    }

    public void initDiseño() {
        limpiar();
    }

    int filaSeleccionada = -1;

    public void limpiar() {
        consultar();
        filaSeleccionada = -1;
        v.txtCosto.setText("Costo: S/" + 0.0);
    }

    public void consultar() {
        data.consultar(v.tablaViaje);
        dataRuta dataR = new dataRuta();
//        v.comboDestino.removeAllItems();
        v.comboPartida.removeAllItems();
        for (Object in : dataR.getConsultar(1)) {
            v.comboPartida.addItem(in);
        }
        v.comboPasajero.removeAllItems();
        dataPasajerero dataP = new dataPasajerero();
        for (Object pas : dataP.getConsultar(0)) {
            v.comboPasajero.addItem(pas);
        }
        v.comboPlacaAuto.removeAllItems();
        dataBuses dataB = new dataBuses();
        for (Object bus : dataB.getConsultar(0)) {
            v.comboPlacaAuto.addItem(bus);
        }
        comboPartidaAction();
        comboDetinoAction();
    }

    public void insertar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar registro", "Confirmacion", msj);
        if (confi == 0) {
            try {
                String diViaje = IdActual();
                String placaAuto = v.comboPlacaAuto.getSelectedItem().toString();
                String dniPasajero = v.comboPasajero.getSelectedItem().toString();
                String partida = v.comboPartida.getSelectedItem().toString();
                String destino = v.comboDestino.getSelectedItem().toString();
                double costo = getCostoPasaje();
                String fecha = UIController.obtenerFechaActualEnFormato();
                Viaje viaje = new Viaje(diViaje, placaAuto, dniPasajero, costo, partida, destino, fecha);
                data.registrar(viaje);
                limpiar();
            } catch (NullPointerException e) {

            }
        }

    }

    public void modificar() {
        if (filaSeleccionada >= 0) {
            int msj = JOptionPane.YES_NO_OPTION;
            int confi = JOptionPane.showConfirmDialog(null, "Confirmar Modificacion", "Confirmacion", msj);
            if (confi == 0) {
                String diViaje = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "idViaje").toString();
                String placaAuto = v.comboPlacaAuto.getSelectedItem().toString();
                String dniPasajero = v.comboPasajero.getSelectedItem().toString();
                String partida = v.comboPartida.getSelectedItem().toString();
                String destino = v.comboDestino.getSelectedItem().toString();
                double costo = getCostoPasaje();
                String fecha = UIController.obtenerFechaActualEnFormato();
                Viaje viaje = new Viaje(diViaje, placaAuto, dniPasajero, costo, partida, destino, fecha);
                data.actualizar(viaje);
                limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una fila", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminar() {
        if (filaSeleccionada >= 0) {
            int msj = JOptionPane.YES_NO_OPTION;
            int confi = JOptionPane.showConfirmDialog(null, "Confirmar Eliminacion", "Confirmacion", msj);
            if (confi == 0) {
                String idViaje = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "idViaje").toString();
                data.borrar(idViaje);
                limpiar();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione una fila", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void clickTabla() {
        filaSeleccionada = v.tablaViaje.getSelectedRow();
        try {
            String dniPasajero = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "pasajero").toString();
            String placaAuto = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "placaBus").toString();
            String partida = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "partida").toString();
            String destino = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "destino").toString();
            String costo = UIController.datoFilaColumna(v.tablaViaje, filaSeleccionada, "costo").toString();
            v.comboPasajero.setSelectedItem(dniPasajero);
            v.comboPlacaAuto.setSelectedItem(placaAuto);
            v.comboPartida.setSelectedItem(partida);
            v.comboDestino.setSelectedItem(destino);
            v.txtCosto.setText("Costo: S/" + costo);
        } catch (NullPointerException e) {
        }
    }

    public void comboPartidaAction() {
        try {
            String partida = v.comboPartida.getSelectedItem().toString();
            dataRuta dataR = new dataRuta();
            v.comboDestino.removeAllItems();
            for (Object des : dataR.buscarDestino(partida)) {
                v.comboDestino.addItem(des);
            }
        } catch (NullPointerException e) {
        }
    }

    public void comboDetinoAction() {
        v.txtCosto.setText("Costo: S/" + getCostoPasaje());
    }

    private double getCostoPasaje() {
        try {
            String partida = v.comboPartida.getSelectedItem().toString();
            String destino = v.comboDestino.getSelectedItem().toString();
            dataRuta dataR = new dataRuta();
            return dataR.buscarCostoPorPartidaYDestino(partida, destino);
        } catch (NullPointerException e) {
            return 0.0;
        }
    }

    private String IdActual() {
        try {
            int posicionUltimo = data.getConsultar(0).size() - 1;
            int ultimoValor = Integer.parseInt(data.getConsultar(0).get(posicionUltimo).toString());
            int valorActualId = ultimoValor + 1;
            String nuevaID = String.format("%08d", valorActualId);
            return nuevaID;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return "00000001";
        }
    }
}
