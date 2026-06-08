package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.LblAviso;
import components.LblSubtitulo;
import models.Address;
import models.User;
import repository.AddressRepository;

public class Dirreccion extends JFrame {

	private JPanel contentPane;
	private JTextField txtColonia, txtCalle, txtReferencia;
	private JTextArea txtInstrucciones;
	
	private LblAviso lblAvisoColonia, lblAvisoCalle, lblAvisoReferencia, lblAvisoInstrucciones;
	private JButton btnGuardar;
	private JLabel lblCancelar;

	private User loggedUser; 

	public Dirreccion(User loggedUser) {
		this.loggedUser = loggedUser;
		
		setTitle("Saturnbucks - Agregar Dirección");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(450, 450); 
		setLocationRelativeTo(null);
		setResizable(true);

		try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				"NUEVA DIRECCIÓN DE ENVÍO",
				TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14), Color.WHITE);
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		generarComponentes();
		aplicarValidacionesEnTiempoReal();
	}

	private void generarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
		Font fontAviso = new Font("Arial", Font.ITALIC, 10);

		c.insets = new Insets(15, 5, 0, 5); c.gridy = 0; panel.add(new LblSubtitulo("Colonia:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 1; txtColonia = new JTextField(20); panel.add(txtColonia, c);
		lblAvisoColonia = new LblAviso(" "); lblAvisoColonia.setForeground(Color.RED); lblAvisoColonia.setFont(fontAviso);
		c.gridy = 2; panel.add(lblAvisoColonia, c);

		c.insets = new Insets(5, 5, 0, 5); c.gridy = 3; panel.add(new LblSubtitulo("Calle y Número:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 4; txtCalle = new JTextField(20); panel.add(txtCalle, c);
		lblAvisoCalle = new LblAviso(" "); lblAvisoCalle.setForeground(Color.RED); lblAvisoCalle.setFont(fontAviso);
		c.gridy = 5; panel.add(lblAvisoCalle, c);

		c.insets = new Insets(5, 5, 0, 5); c.gridy = 6; panel.add(new LblSubtitulo("Referencia (Ej. Portón negro):"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 7; txtReferencia = new JTextField(20); panel.add(txtReferencia, c);
		lblAvisoReferencia = new LblAviso(" "); lblAvisoReferencia.setForeground(Color.RED); lblAvisoReferencia.setFont(fontAviso);
		c.gridy = 8; panel.add(lblAvisoReferencia, c);

		c.insets = new Insets(5, 5, 0, 5); c.gridy = 9; panel.add(new LblSubtitulo("Instrucciones de entrega:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 10; 
		txtInstrucciones = new JTextArea(3, 20);
		txtInstrucciones.setLineWrap(true);
		txtInstrucciones.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(txtInstrucciones);
		panel.add(scrollDesc, c);
		lblAvisoInstrucciones = new LblAviso(" "); lblAvisoInstrucciones.setForeground(Color.RED); lblAvisoInstrucciones.setFont(fontAviso);
		c.gridy = 11; panel.add(lblAvisoInstrucciones, c);

		c.insets = new Insets(25, 5, 5, 5); c.gridy = 12;
		btnGuardar = new JButton("Guardar Dirección");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnGuardar.addActionListener(e -> save());
		panel.add(btnGuardar, c);

		c.insets = new Insets(10, 5, 10, 5); c.gridy = 13;
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>", JLabel.CENTER);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { 
				int option = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas cancelar? Se perderán los datos.", "Cancelar", JOptionPane.YES_NO_OPTION);
    			if(option == JOptionPane.YES_OPTION) {
    				new InicioView(loggedUser).setVisible(true);
    				dispose(); 
    			}
			}
		});
		panel.add(lblCancelar, c);

		JScrollPane mainScroll = new JScrollPane(panel);
		mainScroll.setBorder(null);
		mainScroll.setOpaque(false);
		mainScroll.getViewport().setOpaque(false);
		contentPane.add(mainScroll, BorderLayout.CENTER);
	}

	private void save() {
		boolean valido = true;
		
		if (txtColonia.getText().trim().isEmpty()) { lblAvisoColonia.setText("Requerido"); valido = false; } else { lblAvisoColonia.setText(" "); }
		if (txtCalle.getText().trim().isEmpty()) { lblAvisoCalle.setText("Requerido"); valido = false; } else { lblAvisoCalle.setText(" "); }
		if (txtReferencia.getText().trim().isEmpty()) { lblAvisoReferencia.setText("Requerido"); valido = false; } else { lblAvisoReferencia.setText(" "); }
		if (txtInstrucciones.getText().trim().isEmpty()) { lblAvisoInstrucciones.setText("Requerido"); valido = false; } else { lblAvisoInstrucciones.setText(" "); }

		if (!valido) return;

		try {
			Address nuevaAddr = new Address();
			if (loggedUser != null) {
				nuevaAddr.setUserId(loggedUser.getId());
			} else {
				nuevaAddr.setUserId(1); 
			}
			
			nuevaAddr.setNeighborhood(txtColonia.getText().trim());
			nuevaAddr.setStreet(txtCalle.getText().trim());
			nuevaAddr.setReference(txtReferencia.getText().trim());
			nuevaAddr.setInstructions(txtInstrucciones.getText().trim());
			
			AddressRepository repo = new AddressRepository();
			repo.save(nuevaAddr);
			
			JOptionPane.showMessageDialog(this, "¡Tu dirección de envío ha sido guardada con éxito!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
			new InicioView(loggedUser).setVisible(true);
			dispose();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Hubo un error al guardar tu dirección: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void aplicarValidacionesEnTiempoReal() {
		agregarDocumentListener(txtColonia, lblAvisoColonia);
		agregarDocumentListener(txtCalle, lblAvisoCalle);
		agregarDocumentListener(txtReferencia, lblAvisoReferencia);

		txtInstrucciones.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(" "); }
			public void removeUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(" "); }
			public void changedUpdate(DocumentEvent e) { lblAvisoInstrucciones.setText(" "); }
		});
	}

	private void agregarDocumentListener(JTextField textField, LblAviso label) {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { label.setText(" "); }
			public void removeUpdate(DocumentEvent e) { label.setText(" "); }
			public void changedUpdate(DocumentEvent e) { label.setText(" "); }
		});
	}
}