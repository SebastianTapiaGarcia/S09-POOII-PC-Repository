package Vista;

import controlador.BancoControlador;
import Modelo.Cuenta;

import javax.swing.*;
import java.awt.*;

public class TransferenciaPanelView extends JPanel {
    private final BancoControlador ctrl;
    private final JComboBox<Cuenta> comboOrigen, comboDestino;
    private final JTextField montoField;
    private final JButton transferirBtn;

    public TransferenciaPanelView(BancoControlador bancoControlador) {
        this.ctrl = bancoControlador;
        setBorder(BorderFactory.createTitledBorder("Transferencia"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        // Origen
        c.gridx=0; c.gridy=0;
        add(new JLabel("Cuenta Origen:"), c);
        c.gridx=1;
        comboOrigen = new JComboBox<>();
        comboOrigen.setPreferredSize(new Dimension(150, 25));
        add(comboOrigen, c);

        // Destino
        c.gridx=0; c.gridy=1;
        add(new JLabel("Cuenta Destino:"), c);
        c.gridx=1;
        comboDestino = new JComboBox<>();
        comboDestino.setPreferredSize(new Dimension(150, 25));
        add(comboDestino, c);

        // Monto
        c.gridx=0; c.gridy=2;
        add(new JLabel("Monto:"), c);
        c.gridx=1;
        montoField = new JTextField(8);
        add(montoField, c);

        // Botón
        c.gridx=0; c.gridy=3; c.gridwidth=2;
        c.anchor = GridBagConstraints.CENTER;
        transferirBtn = new JButton("Transferir");
        add(transferirBtn, c);

        // Llenar combos
        recargarCombos();

        transferirBtn.addActionListener(e -> {
            try {
                Cuenta o = (Cuenta) comboOrigen.getSelectedItem();
                Cuenta d = (Cuenta) comboDestino.getSelectedItem();
                double m = Double.parseDouble(montoField.getText().trim());
                if (ctrl.transferir(o.getId(), d.getId(), m)) {
                    JOptionPane.showMessageDialog(this,
                        "Transferencia exitosa",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No se pudo realizar la transferencia",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
                montoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Ingrese un monto válido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void recargarCombos() {
        comboOrigen.removeAllItems();
        comboDestino.removeAllItems();
        for (Cuenta c : ctrl.listarCuentas()) {
            comboOrigen.addItem(c);
            comboDestino.addItem(c);
        }
    }
}
