package Vista;

import controlador.BancoControlador;
import Modelo.Cuenta;

import javax.swing.*;
import java.awt.*;

public class CrearCuentaPanelView extends JPanel {
    private final BancoControlador ctrl;
    private final JTextField idField;
    private final JComboBox<String> tipoCombo;
    private final JTextField saldoField;
    private final JTextField limiteField;
    private final JButton crearButton;

    public CrearCuentaPanelView(BancoControlador bancoControlador) {
        this.ctrl = bancoControlador;
        setBorder(BorderFactory.createTitledBorder("Crear Cuenta"));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.anchor = GridBagConstraints.WEST;

        // --- Fila 0: ID ---
        c.gridx = 0; c.gridy = 0;
        add(new JLabel("ID:"), c);
        c.gridx = 1;
        idField = new JTextField(8);
        add(idField, c);

        // --- Fila 1: Tipo ---
        c.gridx = 0; c.gridy = 1;
        add(new JLabel("Tipo de cuenta:"), c);
        c.gridx = 1;
        tipoCombo = new JComboBox<>(new String[] {"Ahorro","Corriente"});
        tipoCombo.setPreferredSize(new Dimension(100, 24));  
        add(tipoCombo, c);

        // --- Fila 2: Saldo inicial ---
        c.gridx = 0;  c.gridy = 2;
        add(new JLabel("Saldo inicial:"), c);
        c.gridx = 1;
        saldoField = new JTextField(8);
        add(saldoField, c);

        // --- Fila 3: Límite (etiqueta + campo en un panel) ---
        JPanel limitePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        limitePanel.add(new JLabel("Límite:"));
        limiteField = new JTextField(8);
        limitePanel.add(limiteField);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        add(limitePanel, c);

        // Ocultar todo el panel si es Ahorro
        limitePanel.setVisible(false);
        tipoCombo.addActionListener(e -> {
            boolean mostrar = !"Ahorro".equals(tipoCombo.getSelectedItem());
            limitePanel.setVisible(mostrar);
            revalidate();
        });

        // --- Fila 4: Botón ---
        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        crearButton = new JButton("Crear Cuenta");
        add(crearButton, c);

        // Listener
 crearButton.addActionListener(e -> {
    int id;
    double saldo, limite = 0.0;
    String tipo = (String) tipoCombo.getSelectedItem();

    // 1) Validación y parseo de los datos
    try {
        id = Integer.parseInt(idField.getText().trim());
        saldo = Double.parseDouble(saldoField.getText().trim());
        if (!"Ahorro".equals(tipo)) {
            limite = Double.parseDouble(limiteField.getText().trim());
        }
    } catch (NumberFormatException nfex) {
        JOptionPane.showMessageDialog(
            this,
            "Por favor ingresa un ID y saldo (y límite) válidos",
            "Entrada inválida",
            JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    // 2) Llamada a la capa de negocio y persistencia
    try {
        Cuenta nueva = ctrl.crearCuenta(id, tipo, saldo, limite);
        JOptionPane.showMessageDialog(
            this,
            "Cuenta creada exitosamente:\n" + nueva,
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
        // Limpiar campos
        idField.setText("");
        saldoField.setText("");
        limiteField.setText("");
    } catch (Exception ex) {
        ex.printStackTrace();  // Mira la consola para detalles
        JOptionPane.showMessageDialog(
            this,
            "Ocurrió un error al guardar la cuenta. Comprueba permisos y formato del archivo.",
            "Error al guardar",
            JOptionPane.ERROR_MESSAGE
        );
    }
});

    }
}
