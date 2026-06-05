package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.AvatarCircular;
import utils.Session;

public class ProfileEditDialog extends JDialog {
	
	private JTextField txtTelefono;
	private JButton btnGuardar;
	private JLabel lblCancelar;
	private JButton btnCambiarFoto;
	
	private String rutaNuevaImagen = null;
	private boolean saved = false;

	public ProfileEditDialog() {
		setTitle("Actualizar Datos");
		setSize(350, 450); 
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		contentPane.setBackground(new Color(20, 25, 13));
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

		JLabel lblTitulo = new JLabel("Editar Perfil");
		lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(210, 180, 140));
		lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(lblTitulo);
		contentPane.add(Box.createRigidArea(new Dimension(0, 20)));

		AvatarCircular avatar = new AvatarCircular(Session.getName(), 90);
		String rutaFoto = Session.getCurrentUser().getImagePath();
		if (rutaFoto != null && !rutaFoto.trim().isEmpty()) {
			try {
				java.awt.Image imgPerfil = javax.imageio.ImageIO.read(new java.io.File(rutaFoto));
				avatar.setImagenPerfil(imgPerfil);
			} catch (Exception ex) {
				System.out.println("No se pudo cargar la foto de perfil: " + ex.getMessage());
			}
		}
		
		avatar.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(avatar);
		contentPane.add(Box.createRigidArea(new Dimension(0, 10)));

		btnCambiarFoto = new JButton("Seleccionar Imagen");
		btnCambiarFoto.setBackground(new Color(48, 60, 26));
		btnCambiarFoto.setForeground(Color.WHITE);
		btnCambiarFoto.setAlignmentX(CENTER_ALIGNMENT);
		btnCambiarFoto.addActionListener(e -> seleccionarImagen());
		contentPane.add(btnCambiarFoto);
		
		contentPane.add(Box.createRigidArea(new Dimension(0, 25)));

		JLabel lblTel = new JLabel("Teléfono Móvil:");
		lblTel.setForeground(Color.WHITE);
		lblTel.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(lblTel);
		
		txtTelefono = new JTextField(Session.getPhone());
		txtTelefono.setMaximumSize(new Dimension(200, 30));
		txtTelefono.setAlignmentX(CENTER_ALIGNMENT);
		contentPane.add(txtTelefono);
		
		contentPane.add(Box.createRigidArea(new Dimension(0, 30)));

		btnGuardar = new JButton("Guardar Cambios");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26)); 
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.setAlignmentX(CENTER_ALIGNMENT);
		btnGuardar.addActionListener(e -> {
			saved = true;
			dispose();
		});
		contentPane.add(btnGuardar);

		contentPane.add(Box.createRigidArea(new Dimension(0, 10)));
		
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.setAlignmentX(CENTER_ALIGNMENT);
		lblCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { dispose(); }
		});
		contentPane.add(lblCancelar);

		add(contentPane);
	}

	private void seleccionarImagen() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setFileFilter(new FileNameExtensionFilter("Imágenes JPG/PNG", "jpg", "png", "jpeg"));
	    
	    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	        try {
	            File source = chooser.getSelectedFile();
	            String original = source.getAbsolutePath();
	            String extension = original.substring(original.lastIndexOf("."));
	            
	            String newName = UUID.randomUUID() + extension;

	            String folder = "images"; 
	            File directory = new File(folder);
	            if(!directory.exists()) { directory.mkdir(); }

	            Path destination = Paths.get(folder, newName);

	            Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
	            rutaNuevaImagen = destination.toAbsolutePath().toString();
	            
	            btnCambiarFoto.setText("Imagen Cargada ✓");
	            System.out.println("DEBUG: Imagen guardada en: " + rutaNuevaImagen);
	            
	        } catch(Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error al procesar la imagen: " + ex.getMessage());
	        }
	    }
	}

	public boolean isSaved() { return saved; }
	public String getNuevoTelefono() { return txtTelefono.getText().trim(); }
	public String getRutaNuevaImagen() { return rutaNuevaImagen; }
}