package app;

import javax.swing.SwingUtilities;
import Vista.BancoVista;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BancoVista ventana = new BancoVista();
            ventana.setVisible(true);
        });
    }
}
