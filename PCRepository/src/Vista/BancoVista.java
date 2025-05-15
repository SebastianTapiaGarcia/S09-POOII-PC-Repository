package Vista;

import controlador.BancoControlador;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class BancoVista extends JFrame {
    private final BancoControlador ctrl;
    private final TransferenciaPanelView transfPane;
    private final VerMovimientoPanelView verPane;

    public BancoVista() {
        super("Sistema Bancario PeruBank");
        ctrl = new BancoControlador();

        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear pestañas
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("1. Crear Cuenta", new CrearCuentaPanelView(ctrl));

        transfPane = new TransferenciaPanelView(ctrl);
        tabs.addTab("2. Transferencia", transfPane);

        verPane = new VerMovimientoPanelView(ctrl);
        tabs.addTab("3. Ver Movimientos", verPane);

        // Pestaña Salir
        JPanel salir = new JPanel(new GridBagLayout());
        JButton btnSalir = new JButton("Salir");
        btnSalir.addActionListener(e -> System.exit(0));
        salir.add(btnSalir);
        tabs.addTab("4. Salir", salir);

        // Al cambiar de pestaña, recargar los combos necesarios
        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int idx = tabs.getSelectedIndex();
                String title = tabs.getTitleAt(idx);
                if (title.equals("2. Transferencia")) {
                    transfPane.recargarCombos();
                }
                if (title.equals("3. Ver Movimientos")) {
                    verPane.recargarCombo();
                }
            }
        });

        add(tabs, BorderLayout.CENTER);
    }
}
