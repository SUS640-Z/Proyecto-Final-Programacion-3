package views;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import components.*;
import models.Address;
import models.User;

public class AddressFormDialog extends JDialog {
    private JPanel contentPane;
    private JTextField txtColonia, txtCalle, txtReferencia;
    private JTextArea txtInstrucciones;
    private JComboBox<String> cmbUsuarios;
    private LblAviso lblAvisoColonia, lblAvisoCalle, lblAvisoUsuario;
    private JButton btnGuardar;

    private Address address;
    private boolean saved = false;

    public AddressFormDialog(JFrame parent, Address address, List<User> usuarios) {
        super(parent, true);
        this.address = address;
        setTitle(address == null ? "Agregar Dirección" : "Editar Dirección");
        setSize(400, 550);
        setLocationRelativeTo(parent);
        setResizable(true);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);

        generarFormulario(usuarios);
        if (address != null) loadData(address);
        setVisible(true);
    }

    private void generarFormulario(List<User> usuarios) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;

        c.insets = new Insets(10, 5, 0, 5); c.gridy = 0; panel.add(new LblSubtitulo("Dueño (Usuario):"), c);
        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.addItem("Seleccionar Usuario");
        for(User u : usuarios) cmbUsuarios.addItem(u.getId() + " - " + u.getName());
        c.gridy = 1; panel.add(cmbUsuarios, c);
        lblAvisoUsuario = new LblAviso(""); lblAvisoUsuario.setForeground(Color.RED); c.gridy = 2; panel.add(lblAvisoUsuario, c);

        c.gridy = 3; panel.add(new LblSubtitulo("Colonia:"), c);
        c.gridy = 4; txtColonia = new JTextField(20); panel.add(txtColonia, c);
        lblAvisoColonia = new LblAviso(""); lblAvisoColonia.setForeground(Color.RED); c.gridy = 5; panel.add(lblAvisoColonia, c);

        c.gridy = 6; panel.add(new LblSubtitulo("Calle y Número:"), c);
        c.gridy = 7; txtCalle = new JTextField(20); panel.add(txtCalle, c);
        lblAvisoCalle = new LblAviso(""); lblAvisoCalle.setForeground(Color.RED); c.gridy = 8; panel.add(lblAvisoCalle, c);

        c.gridy = 9; panel.add(new LblSubtitulo("Referencia:"), c);
        c.gridy = 10; txtReferencia = new JTextField(20); panel.add(txtReferencia, c);

        c.gridy = 11; panel.add(new LblSubtitulo("Instrucciones:"), c);
        c.gridy = 12; txtInstrucciones = new JTextArea(3, 20); panel.add(new JScrollPane(txtInstrucciones), c);

        c.insets = new Insets(20, 5, 5, 5); c.gridy = 13;
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBackground(new Color(48, 60, 26)); btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> save());
        panel.add(btnGuardar, c);

        contentPane.add(new JScrollPane(panel), BorderLayout.CENTER);
    }

    private void loadData(Address addr) {
        txtColonia.setText(addr.getNeighborhood());
        txtCalle.setText(addr.getStreet());
        txtReferencia.setText(addr.getReference());
        txtInstrucciones.setText(addr.getInstructions());
        for(int i=0; i<cmbUsuarios.getItemCount(); i++) {
            if(cmbUsuarios.getItemAt(i).startsWith(addr.getUserId() + " -")) cmbUsuarios.setSelectedIndex(i);
        }
    }

    private void save() {
        if(cmbUsuarios.getSelectedIndex() == 0) { lblAvisoUsuario.setText("Requerido"); return; }
        if(txtColonia.getText().isEmpty()) { lblAvisoColonia.setText("Requerido"); return; }
        
        int userId = Integer.parseInt(((String)cmbUsuarios.getSelectedItem()).split(" ")[0]);
        if(address == null) address = new Address(txtColonia.getText(), txtCalle.getText(), txtReferencia.getText(), txtInstrucciones.getText(), userId);
        else {
            address.setNeighborhood(txtColonia.getText()); address.setStreet(txtCalle.getText());
            address.setReference(txtReferencia.getText()); address.setInstructions(txtInstrucciones.getText());
            address.setUserId(userId);
        }
        saved = true; dispose();
    }
    public boolean isSaved() { return saved; }
    public Address getAddress() { return address; }
}