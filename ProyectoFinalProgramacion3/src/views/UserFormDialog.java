package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;

import components.LblAviso;
import components.LblSubtitulo;
import models.User;
import config.Config;
import utils.PasswordUtils;

public class UserFormDialog extends JDialog {
	
	private JPanel contentPane;
	private JTextField txtName, txtLastName, txtTelefono, txtFechaNac, txtCorreo;
	private JPasswordField txtContrasena, txtConfirmarContrasena;
	private JComboBox<String> cmbGenero, cmbRol;
	
	private LblAviso lblAvisoName, lblAvisoLastName, lblAvisoTelefono, lblAvisoFechaNac;
	private LblAviso lblAvisoCorreo, lblAvisoContra, lblAvisoConfirmar, lblAvisoImage;
	
	private JLabel lblImagePreview, lblImageName, lblCancelar;
	private JButton btnSelectImage, btnGuardar;
	private String selectedImagePath;

	private User user;
	private boolean saved = false;

	public UserFormDialog(JFrame parent, User user) {
		super(parent, true);
		this.user = user;
		setTitle(user == null ? "Agregar Usuario" : "Editar Usuario");
		setSize(450, 750); // Tamaño un poco más alto para que quepa todo sin apretarse
		setLocationRelativeTo(parent);
		setResizable(true);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				user == null ? "NUEVO USUARIO" : "EDITAR USUARIO",
				TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14), Color.WHITE);
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		generarComponentes();
		if (user != null) {
			loadData();
		}
	}

	private void generarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
		Font fontAviso = new Font("Arial", Font.ITALIC, 10);

		// NOMBRES
		c.insets = new Insets(10, 5, 0, 5); c.gridy = 0; panel.add(new LblSubtitulo("Nombre:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 1; txtName = new JTextField(20); panel.add(txtName, c);
		lblAvisoName = new LblAviso(""); lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso);
		c.gridy = 2; panel.add(lblAvisoName, c);

		// APELLIDOS
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 3; panel.add(new LblSubtitulo("Apellidos:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 4; txtLastName = new JTextField(20); panel.add(txtLastName, c);
		lblAvisoLastName = new LblAviso(""); lblAvisoLastName.setForeground(Color.RED); lblAvisoLastName.setFont(fontAviso);
		c.gridy = 5; panel.add(lblAvisoLastName, c);

		// TELÉFONO
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 6; panel.add(new LblSubtitulo("Teléfono:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 7; txtTelefono = new JTextField(20); panel.add(txtTelefono, c);
		lblAvisoTelefono = new LblAviso(""); lblAvisoTelefono.setForeground(Color.RED); lblAvisoTelefono.setFont(fontAviso);
		c.gridy = 8; panel.add(lblAvisoTelefono, c);

		// FECHA DE NACIMIENTO
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 9; panel.add(new LblSubtitulo("Fecha de Nac. (AAAA-MM-DD):"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 10; txtFechaNac = new JTextField(20); panel.add(txtFechaNac, c);
		lblAvisoFechaNac = new LblAviso(""); lblAvisoFechaNac.setForeground(Color.RED); lblAvisoFechaNac.setFont(fontAviso);
		c.gridy = 11; panel.add(lblAvisoFechaNac, c);

		// GÉNERO (Actualizado a la BD)
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 12; panel.add(new LblSubtitulo("Género:"), c);
		c.insets = new Insets(0, 5, 5, 5); c.gridy = 13;
		String[] opcionesGen = {"Seleccionar", "Feminine", "Masculine"};
		cmbGenero = new JComboBox<>(opcionesGen); panel.add(cmbGenero, c);

		// ROL (AQUÍ ESTÁ LA INTEGRACIÓN DEL EQUIPO)
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 14; panel.add(new LblSubtitulo("Rol:"), c);
		c.insets = new Insets(0, 5, 5, 5); c.gridy = 15;
		String[] roles = {"Seleccionar"};
		cmbRol = new JComboBox<>(roles); panel.add(cmbRol, c);

		// CORREO
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 16; panel.add(new LblSubtitulo("Correo electrónico:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 17; txtCorreo = new JTextField(20); panel.add(txtCorreo, c);
		lblAvisoCorreo = new LblAviso(""); lblAvisoCorreo.setForeground(Color.RED); lblAvisoCorreo.setFont(fontAviso);
		c.gridy = 18; panel.add(lblAvisoCorreo, c);

		// CONTRASEÑA
		c.insets = new Insets(2, 5, 0, 5); c.gridy = 19; panel.add(new LblSubtitulo(user == null ? "Contraseña:" : "Nueva Contraseña (dejar en blanco para no cambiar):"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 20; txtContrasena = new JPasswordField(20); panel.add(txtContrasena, c);
		lblAvisoContra = new LblAviso(""); lblAvisoContra.setForeground(Color.RED); lblAvisoContra.setFont(fontAviso);
		c.gridy = 21; panel.add(lblAvisoContra, c);

		// IMAGEN DE PERFIL
		c.insets = new Insets(5, 5, 2, 5); c.gridy = 22; panel.add(new LblSubtitulo("Foto de perfil:"), c);
		c.insets = new Insets(0, 5, 2, 5); c.gridy = 23;
		lblImagePreview = new JLabel(); lblImagePreview.setPreferredSize(new Dimension(50, 50));
		lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblImagePreview.setHorizontalAlignment(JLabel.CENTER); panel.add(lblImagePreview, c);
		
		c.gridy = 24; btnSelectImage = new JButton("Seleccionar imagen");
		btnSelectImage.setBackground(new Color(210, 180, 140)); btnSelectImage.setForeground(Color.BLACK);
		btnSelectImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSelectImage.addActionListener(e -> chooseImage());
		panel.add(btnSelectImage, c);
		
		lblAvisoImage = new LblAviso(""); lblAvisoImage.setForeground(Color.RED); lblAvisoImage.setFont(fontAviso);
		c.gridy = 25; panel.add(lblAvisoImage, c);

		// BOTONES FINALES
		c.insets = new Insets(15, 5, 5, 5); c.gridy = 26;
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26)); btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.addActionListener(e -> verificarYGuardar());
		panel.add(btnGuardar, c);

		c.insets = new Insets(10, 5, 10, 5); c.gridy = 27;
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>", JLabel.CENTER);
		lblCancelar.setForeground(Color.WHITE); lblCancelar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { dispose(); } });
		panel.add(lblCancelar, c);

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(null);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	private void loadData() {
		txtName.setText(user.getName());
		txtLastName.setText(user.getLastName());
		txtTelefono.setText(user.getTelefono());
		txtFechaNac.setText(user.getFechaNacimiento());
		txtCorreo.setText(user.getEmail());
		
		if(user.getGenero() != null) cmbGenero.setSelectedItem(user.getGenero());
		
		// Mostrar imagen si tiene
		if(user.getImagePath() != null && !user.getImagePath().isEmpty()) {
			selectedImagePath = user.getImagePath();
			ImageIcon icon = new ImageIcon(selectedImagePath);
			Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
			lblImagePreview.setIcon(new ImageIcon(img));
		}
	}

	private void chooseImage() {
		String lastDirectory = Config.get("registration.image.last.directory", System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(lastDirectory);
		chooser.setDialogTitle("Seleccionar imagen");
		chooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png"));
		int option = chooser.showOpenDialog(this);
		
		if(option == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			selectedImagePath = file.getAbsolutePath();
			Config.set("registration.image.last.directory", file.getParent());
			ImageIcon icon = new ImageIcon(selectedImagePath);
			Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
			lblImagePreview.setIcon(new ImageIcon(img));
		}
	}

	private String saveImage() {
		try {
			if(selectedImagePath == null) return (user != null) ? user.getImagePath() : null;
			if(user != null && selectedImagePath.equals(user.getImagePath())) return selectedImagePath;
			
			File source = new File(selectedImagePath);
			String extension = selectedImagePath.substring(selectedImagePath.lastIndexOf("."));
			String newName = UUID.randomUUID() + extension;
			String folder = "." + File.separator + "images";
			File directory = new File(folder);
			if(!directory.exists()) directory.mkdir(); 
			
			Path destination = Paths.get(folder, newName);
			Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
			return destination.toString();
		} catch(Exception ex) {
			return null;
		}
	}

	private void verificarYGuardar() {
		// Validaciones rápidas
		boolean valido = true;
		if(txtName.getText().trim().isEmpty()) { lblAvisoName.setText("Requerido"); valido = false; } else lblAvisoName.setText("");
		if(txtLastName.getText().trim().isEmpty()) { lblAvisoLastName.setText("Requerido"); valido = false; } else lblAvisoLastName.setText("");
		if(txtCorreo.getText().trim().isEmpty()) { lblAvisoCorreo.setText("Requerido"); valido = false; } else lblAvisoCorreo.setText("");
		
		String pass = new String(txtContrasena.getPassword());
		if(user == null && pass.isEmpty()) { lblAvisoContra.setText("Requerido para nuevos"); valido = false; } else lblAvisoContra.setText("");

		if(!valido) return;

		// Lógica del Equipo para recolectar el Rol y Género
		String finalImagePath = saveImage();
		String genero = cmbGenero.getSelectedIndex() == 0 ? null : (String) cmbGenero.getSelectedItem();
		
		String rol = (String) cmbRol.getSelectedItem();
		if (rol != null && rol.equals("Seleccionar")) {
			rol = null; 
		}
		
		String finalPassword = (user != null && pass.isEmpty()) ? user.getPassword() : PasswordUtils.hashPassword(pass);

		if(user == null) {
			user = new User(txtName.getText().trim(), txtLastName.getText().trim(), txtCorreo.getText().trim(), 
					finalPassword, finalImagePath, txtTelefono.getText().trim(), genero, txtFechaNac.getText().trim(), rol);
		} else {
			user.setName(txtName.getText().trim());
			user.setLastName(txtLastName.getText().trim());
			user.setEmail(txtCorreo.getText().trim());
			user.setPassword(finalPassword);
			user.setTelefono(txtTelefono.getText().trim());
			user.setFechaNacimiento(txtFechaNac.getText().trim());
			user.setGenero(genero);
			user.setImagePath(finalImagePath);
			user.setRol(rol);
		}
		saved = true; 
		dispose();
	}

	// GETTERS OBLIGATORIOS PARA EL CONTROLADOR DEL EQUIPO
	public boolean isSaved() { return saved; }
	public User getUser() { return user; }
	public JComboBox<String> getCmbRol() { return cmbRol; }
	public void setCmbRol(JComboBox<String> cmbRol) { this.cmbRol = cmbRol; }
}