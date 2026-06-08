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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import components.LblAviso;
import components.LblSubtitulo;
import models.Rol;

public class RolFormDialog extends JDialog {
	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtDescription;
	private LblAviso lblAvisoName;
	private LblAviso lblAvisoDescription;
	private JButton btnGuardar;
	private JLabel lblCancelar;

	private Rol rol;
	private boolean saved = false;

	public RolFormDialog(JFrame parent, Rol rol) {
		super(parent, true);
		this.rol = rol;
		setTitle(rol == null ? "Agregar Rol" : "Editar Rol");
		setSize(380, 300);
		
		setLocationRelativeTo(parent);
		setResizable(false);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				rol == null ? "NUEVO ROL" : "EDITAR ROL",
				TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14), Color.WHITE);
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		generarComponentes();
		loadData();
	}

	private void generarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;

		c.insets = new Insets(10, 5, 0, 5); c.gridy = 0;
		panel.add(new LblSubtitulo("Nombre del Rol:"), c);
		
		c.insets = new Insets(0, 5, 5, 5); c.gridy = 1;
		txtName = new JTextField(20); panel.add(txtName, c);
		
		lblAvisoName = new LblAviso(""); lblAvisoName.setForeground(Color.RED);
		lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
		c.gridy = 2; panel.add(lblAvisoName, c);

		c.insets = new Insets(10, 5, 0, 5); c.gridy = 3;
		panel.add(new LblSubtitulo("Descripción:"), c);
		
		c.insets = new Insets(0, 5, 5, 5); c.gridy = 4;
		txtDescription = new JTextField(20); panel.add(txtDescription, c);
		
		lblAvisoDescription = new LblAviso(""); lblAvisoDescription.setForeground(Color.RED);
		lblAvisoDescription.setFont(new Font("Arial", Font.ITALIC, 10));
		c.gridy = 5; panel.add(lblAvisoDescription, c);

		c.insets = new Insets(20, 5, 5, 5); c.gridy = 6;
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26));
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnGuardar.addActionListener(e -> save());
		panel.add(btnGuardar, c);

		c.insets = new Insets(10, 5, 5, 5); c.gridy = 7;
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>", JLabel.CENTER);
		lblCancelar.setForeground(Color.WHITE);
		lblCancelar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { dispose(); }
		});
		panel.add(lblCancelar, c);

		contentPane.add(panel, BorderLayout.CENTER);
	}

	private void loadData() {
		if (rol != null) {
			txtName.setText(rol.getName());
			txtDescription.setText(rol.getDescription());
		}
	}

	private void save() {
		lblAvisoName.setText("");
		lblAvisoDescription.setText("");
		boolean valido = true;

		if (txtName.getText().trim().isEmpty()) {
			lblAvisoName.setText("Requerido");
			valido = false;
		}
		if (txtDescription.getText().trim().isEmpty()) {
			lblAvisoDescription.setText("Requerido");
			valido = false;
		}
		if (!valido) return;

		if (rol == null) {
			rol = new Rol(txtName.getText().trim(), txtDescription.getText().trim());
		} else {
			rol.setName(txtName.getText().trim());
			rol.setDescription(txtDescription.getText().trim());
		}
		saved = true;
		dispose();
	}

	public boolean isSaved() { return saved; }
	public Rol getRol() { return rol; }
}