package MODELO;

import COMPONET.CustomFileChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class UIController {

    public static void removeWhiteBorder(JScrollPane scrollPane) {
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public static void transparentarTxtField(JTextField... txtField) {
        for (JTextField txt : txtField) {
            txt.setBackground(new java.awt.Color(0, 0, 0, 0));
            txt.setBorder(null);
        }
    }

    public static void setPanelBackgroundColors(JPanel panel, String pressedColor, String rolloverColor) {
        Color normalColor = panel.getBackground();

        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.decode(pressedColor));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panel.setBackground(normalColor);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.decode(rolloverColor));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(normalColor);
            }
        });
    }

    public static void ScrollInvisible(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0)); // Hace invisible el palito del scroll
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0)); // Hace invisible el palito del scroll
        scrollPane.getViewport().setOpaque(false); // Hace invisible el fondo del JScrollPane
    }

    public static void MostrarPanel(JPanel contenedor, JPanel panel) {
        int ancho = panel.getSize().width;
        int largo = panel.getSize().height;
        panel.setSize(ancho, largo);
        panel.setLocation(0, 0);

        contenedor.removeAll();
        contenedor.add(panel, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();
    }

    public static void scrollToTop(JScrollPane scrollPane) {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
    }

    public static void limitacionNumeros(JTextField txtField, KeyEvent evt, int CantNumeros) {
        int key = evt.getKeyChar();
        boolean numeros = key >= 48 && key <= 57;
        boolean espacio = key == KeyEvent.VK_SPACE;
        if (!numeros || espacio) {
            evt.consume();
        } else {
            if (txtField.getText().length() >= CantNumeros) {
                evt.consume();
            }
        }
    }

    public static void limitacionNumerosDecimales(JTextField txtField, KeyEvent evt, int CantNumeros) {
        char key = evt.getKeyChar();
        String text = txtField.getText();
        boolean tienePunto = text.contains(".");

        if (!Character.isDigit(key) && key != '.' || (tienePunto && key == '.') || (key == '.' && text.indexOf('.') != text.lastIndexOf('.'))) {
            evt.consume();
        } else {
            if (text.length() >= CantNumeros) {
                evt.consume();
            }
        }
    }

    public static void limitacionCaracteres(JTextField txtField, KeyEvent evt, int cantNumeros, boolean conEspacio) {
        if (conEspacio) {
            if (txtField.getText().length() >= cantNumeros) {
                evt.consume();
            }
        } else {
            int key = evt.getKeyChar();
            boolean espacio = key == KeyEvent.VK_SPACE;
            if (espacio) {
                evt.consume();
            } else {
                if (txtField.getText().length() >= cantNumeros) {
                    evt.consume();
                }
            }
        }
    }

    public static boolean ComprobarTxtVacio(JTextField... txt) {
        boolean vacio;
        for (JTextField text : txt) {
            vacio = text.getText().equals("");
            if (vacio) {
                return vacio;
            }
        }
        return false;
    }

    public static boolean ComprobarTxtVacio(String... txt) {
        boolean vacio;
        for (String text : txt) {
            vacio = text.equals("");
            if (vacio) {
                return vacio;
            }
        }
        return false;
    }

    public static Object datoFilaColumna(JTable tabla, int fila, String nombreColumna) {
        int columnaCodigo = 0;
        Object dato;
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            if (tabla.getColumnName(i).equalsIgnoreCase(nombreColumna)) {
                columnaCodigo = i;
                break;
            }
        }
        dato = tabla.getValueAt(fila, columnaCodigo);
        return dato;
    }

    public static String obtenerFechaActualEnFormato() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return fechaActual.format(formato);
    }

    public static void exportarTablaXlsx(DefaultTableModel modeloTabla, String nombreArchivo, Color color) {
        String defaultFileName = nombreArchivo;
        String fileExtension = "xlsx";
        String selectedFilePath = CustomFileChooser.chooseExcelFile(defaultFileName, fileExtension);
        if (selectedFilePath == null) {
        } else {
            ExportarExcel.exportToExcel(modeloTabla, selectedFilePath, color);
            JOptionPane.showMessageDialog(null, defaultFileName + "." + fileExtension + " descargado");
        }
    }
}
