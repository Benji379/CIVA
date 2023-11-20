package CONTROLADOR;

import MODELO.UIController;
import VISTA.frmPrincipal;
import VISTA.moduloViaje;
import java.awt.Cursor;
import javax.swing.JPanel;

public class ControladorFrmPrincipal {

    frmPrincipal p;

    public ControladorFrmPrincipal(frmPrincipal p) {
        this.p = p;
    }

    public void initDiseño() {
        p.setLocationRelativeTo(null);
        p.setIconImage(java.awt.Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.png")));
        UIController.ScrollInvisible(p.jScrollPane2);
        UIController.removeWhiteBorder(p.jScrollPane2);
        initPanels(p.buses, p.chofer, p.pasajero, p.perfil, p.rutas, p.viaje);
        UIController.removeWhiteBorder(p.jScrollPane1);
        p.logo.setCursor(new Cursor(12));
        irPanel(new moduloViaje());
    }

    private void initPanels(JPanel... panel) {
        for (JPanel pa : panel) {
            UIController.setPanelBackgroundColors(pa, "#7f7c7c", "#666363");
        }
    }

    public void irPanel(JPanel panel) {
        UIController.scrollToTop(p.jScrollPane1);
        UIController.MostrarPanel(p.CONTENEDOR, panel);
    }

}
