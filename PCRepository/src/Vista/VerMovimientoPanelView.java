package Vista;

import controlador.BancoControlador;
import Modelo.Cuenta;
import Modelo.Movimiento;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerMovimientoPanelView extends JPanel {
    private final BancoControlador ctrl;
    private final JComboBox<Cuenta> comboCuenta;
    private final JTextArea areaMov;
    private final JButton mostrarBtn;

    public VerMovimientoPanelView(BancoControlador bancoControlador) {
        this.ctrl = bancoControlador;
        setBorder(BorderFactory.createTitledBorder("Ver Movimientos"));
        setLayout(new BorderLayout(10,10));

        // Norte: selección de cuenta + botón
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        north.add(new JLabel("Cuenta:"));
        comboCuenta = new JComboBox<>();
        comboCuenta.setPreferredSize(new Dimension(150,25));
        north.add(comboCuenta);
        mostrarBtn = new JButton("Mostrar");
        north.add(mostrarBtn);
        add(north, BorderLayout.NORTH);

        // Centro: área de textos con scroll
        areaMov = new JTextArea();
        areaMov.setEditable(false);
        add(new JScrollPane(areaMov), BorderLayout.CENTER);

        recargarCombo();

        mostrarBtn.addActionListener(e -> {
            areaMov.setText("");
            Cuenta c = (Cuenta) comboCuenta.getSelectedItem();
            if (c!=null) {
                List<Movimiento> list = ctrl.verMovimientos(c.getId());
                for (Movimiento m : list) {
                    areaMov.append(String.format("%s – S/ %.2f%n",
                        m.getDescripcion(), m.getMonto()));
                }
            }
        });
    }

    public void recargarCombo() {
        comboCuenta.removeAllItems();
        for (Cuenta c : ctrl.listarCuentas()) {
            comboCuenta.addItem(c);
        }
    }
}
