package CONTROLADOR;

import DATA.dataRuta;
import MODELO.Ruta;
import MODELO.UIController;
import VISTA.moduloRutas;
import javax.swing.JOptionPane;

public class ControladorRutas {

    moduloRutas r;
    dataRuta data;

    public ControladorRutas(moduloRutas r) {
        this.r = r;
        data = new dataRuta();
        System.out.println(IdActual());
    }

    public void initDiseño() {
        UIController.transparentarTxtField(r.txtCodigo, r.txtCosto);
        limpiar();
    }

    public void limpiar() {
        consultar();
        r.comboDestino.setSelectedIndex(0);
        r.comboPartida.setSelectedIndex(0);
        r.txtCodigo.setText(IdActual());
        r.txtCosto.setText("");
        r.tablaCodigo.clearSelection();
        filaSeleccionada = -1;
        r.txtCodigo.setText(IdActual());
    }

    public void registrar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar registro", "Confirmacion", msj);
        if (confi == 0) {
            String partida = r.comboPartida.getSelectedItem().toString();
            String destino = r.comboDestino.getSelectedItem().toString();
            if (!partida.equals(destino)) {
                try {
                    double precio = Double.parseDouble(r.txtCosto.getText());
                    Ruta ruta = new Ruta(IdActual(), partida, destino, precio);
                    data.registrar(ruta);
                    limpiar();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione otro destino", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void modificar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar modificacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                try {
                    String partida = r.comboPartida.getSelectedItem().toString();
                    String destino = r.comboDestino.getSelectedItem().toString();
                    double precio = Double.parseDouble(r.txtCosto.getText());
                    String codigo = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "cod.").toString();
                    Ruta ruta = new Ruta(codigo, partida, destino, precio);
                    data.actualizar(ruta);
                    limpiar();
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: " + e.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Selecione una fila", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void eliminar() {
        int msj = JOptionPane.YES_NO_OPTION;
        int confi = JOptionPane.showConfirmDialog(null, "Confirmar eliminacion", "Confirmacion", msj);
        if (confi == 0) {
            if (filaSeleccionada >= 0) {
                String codigo = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "cod.").toString();
                data.borrar(codigo);
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Selecione una fila", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void clickTabla() {
        filaSeleccionada = r.tablaCodigo.getSelectedRow();
        String codigo = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "cod.").toString();
        String partida = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "partida").toString();
        String destino = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "destino").toString();
        String precio = UIController.datoFilaColumna(r.tablaCodigo, filaSeleccionada, "costo").toString();
        r.txtCodigo.setText(codigo);
        r.comboPartida.setSelectedItem(partida);
        r.comboDestino.setSelectedItem(destino);
        r.txtCosto.setText(precio);
    }

    int filaSeleccionada = -1;

    public void consultar() {
        try {
            data.consultar(r.tablaCodigo);
        } catch (NullPointerException e) {
        }
    }

    private String IdActual() {
        try {
            int posicionUltimo = data.getConsultarr(0).size() - 1;
            int ultimoValor = Integer.parseInt(data.getConsultarr(0).get(posicionUltimo).toString());
            int valorActualId = ultimoValor + 1;
            String nuevaID = String.format("%08d", valorActualId);
            return nuevaID;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return "00000001";
        }
    }
}
