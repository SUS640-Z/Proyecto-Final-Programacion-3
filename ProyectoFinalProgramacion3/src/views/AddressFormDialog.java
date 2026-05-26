package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import components.LblAviso;
import components.LblSubtitulo;
import models.Address;
import models.User;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;

public class AddressFormDialog extends JDialog {
	
	private JPanel contentPane;
	private JComboBox<String> cmbUsuarios;
	private JTextField txtColonia, txtCalle, txtReferencia;
	private JTextArea txtInstrucciones;
	
	private LblAviso lblAvisoUsuario, lblAvisoColonia, lblAvisoCalle, lblAvisoReferencia, lblAvisoInstrucciones;
	private JButton btnGuardar;
	private JLabel lblCancelar;

	private Address address;
	private List<User> listaUsuariosBD;
	private boolean saved = false;

	public AddressFormDialog(JFrame parent, Address address, List<User> usuarios) {
		super(parent, true);
		this.address = address;
		this.listaUsuariosBD = usuarios;
		
		setTitle(address == null ? "Agregar Dirección" : "Editar Dirección");
		setSize(450, 650); 
		setLocationRelativeTo(parent);
		setResizable(true);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				address == null ? "NUEVA DIRECCIÓN" : "EDITAR DIRECCIÓN",
				TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14), Color.WHITE);
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		generarComponentes();
		aplicarValidacionesEnTiempoReal(); // <--- AGREGA ESTA LÍNEA AQUÍ
		if (address != null) {
			loadData();
		}
	}

	private void generarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
		Font fontAviso = new Font("Arial", Font.ITALIC, 10);

		// SELECCIÓN DE USUARIO
		c.insets = new Insets(10, 5, 0, 5); c.gridy = 0; panel.add(new LblSubtitulo("Dueño de la dirección:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 1;
		cmbUsuarios = new JComboBox<>();
		cmbUsuarios.addItem("Seleccionar Usuario");
		for(User u : listaUsuariosBD) {
			cmbUsuarios.addItem(u.getId() + " - " + u.getName() + " " + u.getLastName());
		}
		panel.add(cmbUsuarios, c);
		lblAvisoUsuario = new LblAviso(""); lblAvisoUsuario.setForeground(Color.RED); lblAvisoUsuario.setFont(fontAviso);
		c.gridy = 2; panel.add(lblAvisoUsuario, c);

		// COLONIA
		c.insets = new Insets(5, 5, 0, 5); c.gridy = 3; panel.add(new LblSubtitulo("Colonia:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 4; txtColonia = new JTextField(20); panel.add(txtColonia, c);
		lblAvisoColonia = new LblAviso(""); lblAvisoColonia.setForeground(Color.RED); lblAvisoColonia.setFont(fontAviso);
		c.gridy = 5; panel.add(lblAvisoColonia, c);

		// CALLE
		c.insets = new Insets(5, 5, 0, 5); c.gridy = 6; panel.add(new LblSubtitulo("Calle y Número:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 7; txtCalle = new JTextField(20); panel.add(txtCalle, c);
		lblAvisoCalle = new LblAviso(""); lblAvisoCalle.setForeground(Color.RED); lblAvisoCalle.setFont(fontAviso);
		c.gridy = 8; panel.add(lblAvisoCalle, c);

		// REFERENCIA
		c.insets = new Insets(5, 5, 0, 5); c.gridy = 9; panel.add(new LblSubtitulo("Referencia (Ej. Portón negro):"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 10; txtReferencia = new JTextField(20); panel.add(txtReferencia, c);
		lblAvisoReferencia = new LblAviso(""); lblAvisoReferencia.setForeground(Color.RED); lblAvisoReferencia.setFont(fontAviso);
		c.gridy = 11; panel.add(lblAvisoReferencia, c);

		// INSTRUCCIONES
		c.insets = new Insets(5, 5, 0, 5); c.gridy = 12; panel.add(new LblSubtitulo("Instrucciones de entrega:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 13; 
		txtInstrucciones = new JTextArea(3, 20);
		txtInstrucciones.setLineWrap(true);
		txtInstrucciones.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(txtInstrucciones);
		panel.add(scrollDesc, c);
		lblAvisoInstrucciones = new LblAviso(""); lblAvisoInstrucciones.setForeground(Color.RED); lblAvisoInstrucciones.setFont(fontAviso);
		c.gridy = 14; panel.add(lblAvisoInstrucciones, c);

		// BOTÓN GUARDAR
		c.insets = new Insets(15, 5, 5, 5); c.gridy = 15;
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnGuardar.addActionListener(e -> save());
		panel.add(btnGuardar, c);

		// LABEL CANCELAR
		c.insets = new Insets(10, 5, 10, 5); c.gridy = 16;
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>", JLabel.CENTER);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { dispose(); }
		});
		panel.add(lblCancelar, c);

		// Añadir el panel dentro de un JScrollPane principal por si la pantalla es pequeña
		JScrollPane mainScroll = new JScrollPane(panel);
		mainScroll.setBorder(null);
		mainScroll.setOpaque(false);
		mainScroll.getViewport().setOpaque(false);
		contentPane.add(mainScroll, BorderLayout.CENTER);
	}

	private void loadData() {
		txtColonia.setText(address.getNeighborhood());
		txtCalle.setText(address.getStreet());
		txtReferencia.setText(address.getReference());
		txtInstrucciones.setText(address.getInstructions());
		
		for(int i = 1; i < cmbUsuarios.getItemCount(); i++) {
			if(cmbUsuarios.getItemAt(i).startsWith(address.getUserId() + " -")) {
				cmbUsuarios.setSelectedIndex(i);
				break;
			}
		}
	}

	private void save() {
		boolean valido = true;
		
		if (cmbUsuarios.getSelectedIndex() == 0) { lblAvisoUsuario.setText("Selecciona un usuario"); valido = false; } else { lblAvisoUsuario.setText(""); }
		if (txtColonia.getText().trim().isEmpty()) { lblAvisoColonia.setText("Requerido"); valido = false; } else { lblAvisoColonia.setText(""); }
		if (txtCalle.getText().trim().isEmpty()) { lblAvisoCalle.setText("Requerido"); valido = false; } else { lblAvisoCalle.setText(""); }
		if (txtReferencia.getText().trim().isEmpty()) { lblAvisoReferencia.setText("Requerido"); valido = false; } else { lblAvisoReferencia.setText(""); }
		if (txtInstrucciones.getText().trim().isEmpty()) { lblAvisoInstrucciones.setText("Requerido"); valido = false; } else { lblAvisoInstrucciones.setText(""); }

		if (!valido) return;

		String seleccion = (String) cmbUsuarios.getSelectedItem();
		int userId = Integer.parseInt(seleccion.split(" ")[0]);

		if (address == null) {
			address = new Address(txtColonia.getText().trim(), txtCalle.getText().trim(), 
					txtReferencia.getText().trim(), txtInstrucciones.getText().trim(), userId);
		} else {
			address.setNeighborhood(txtColonia.getText().trim());
			address.setStreet(txtCalle.getText().trim());
			address.setReference(txtReferencia.getText().trim());
			address.setInstructions(txtInstrucciones.getText().trim());
			address.setUserId(userId);
		}
		saved = true;
		dispose();
	}

	public boolean isSaved() { return saved; }
	public Address getAddress() { return address; }
	private void aplicarValidacionesEnTiempoReal() {
		// Evento para borrar el error al seleccionar un usuario del ComboBox
		cmbUsuarios.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED && cmbUsuarios.getSelectedIndex() > 0) {
				lblAvisoUsuario.setText("");
			}
		});

		// Eventos para borrar los errores al escribir en los TextFields
		agregarDocumentListener(txtColonia, lblAvisoColonia);
		agregarDocumentListener(txtCalle, lblAvisoCalle);
		agregarDocumentListener(txtReferencia, lblAvisoReferencia);

		// Evento para el JTextArea (Instrucciones)
		txtInstrucciones.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(""); }
			public void removeUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(""); }
			public void changedUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(""); }
		});
	}

	private void agregarDocumentListener(JTextField textField, LblAviso label) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { label.setText(""); }
			public void removeUpdate(DocumentEvent e) { label.setText(""); }
			public void changedUpdate(DocumentEvent e) { label.setText(""); }
		});
	}
}