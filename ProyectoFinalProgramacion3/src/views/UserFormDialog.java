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
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.BtnDirecion;
import components.LblAviso;
import components.LblSubtitulo;
import models.User;
import utils.Config;
import utils.PasswordUtils;

public class UserFormDialog extends JDialog{
    JPanel contentPane;
    JTextField txtName;
    JTextField txtLastName;
    JTextField txtCorreo;
    JPasswordField txtContrasena;
    JPasswordField txtConfirmarContrasena;
    
    JTextField txtTelefono;
    JTextField txtFechaNac;
    JComboBox<String> cmbGenero;
    JComboBox<String> cmbRol;
    
    LblAviso lblAvisoName;
    LblAviso lblAvisoLastName;
    LblAviso lblAvisoTelefono;
    LblAviso lblAvisoFechaNac;
    LblAviso lblAvisoCorreo;
    LblAviso lblAvisoContra;
    LblAviso lblAvisoConfirmar;
    LblAviso lblAvisoImage; 
    
    BtnDirecion btnConfirmar2;
    JPanel panelFormulario;
    JButton btnRegistrar;
    JLabel lblRegresar;
    
    private JButton btnSelectImage;
	private JLabel lblImagePreview;
	private JLabel lblImageName;
	private String selectedImagePath;
    
    private User user;
    private boolean saved = false;

    public UserFormDialog(JFrame parent, User user) {
    	super(parent, true);
    	this.user = user;
    	setTitle(user == null ? "Agregar usuario" : "Editar usuario");
    	setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 400, 600); 
        setResizable(true);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

        contentPane = new JPanel();
        contentPane.setBackground(new Color(15, 19, 9)); 
        contentPane.setLayout(new BorderLayout()); 

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border panelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                user == null ? "NUEVO CLIENTE" : "EDITAR CLIENTE", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);

        generarComponentes();
        aplicarEventoFocus();
        eventosCampos();
        resetearAvisos();
        loadData(); 
    }

    FocusAdapter efectoFocus = new FocusAdapter() {
        Color bordeActivo = new Color(0, 200, 120);   
        Color bordeInactivo = Color.BLACK; 

        @Override
        public void focusGained(FocusEvent e) { ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeActivo, 2)); }
        @Override
        public void focusLost(FocusEvent e) { ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeInactivo, 1)); }
    };
    
    private void aplicarEventoFocus(){
    		txtName.addFocusListener(efectoFocus);
    		txtLastName.addFocusListener(efectoFocus);
    		txtTelefono.addFocusListener(efectoFocus);
    		txtFechaNac.addFocusListener(efectoFocus);
    		txtCorreo.addFocusListener(efectoFocus);
    		txtContrasena.addFocusListener(efectoFocus);
    		txtConfirmarContrasena.addFocusListener(efectoFocus);
    }
    
	private void generarComponentes() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false); 

        generarCampos(panelFormulario);
        generarAvisos(panelFormulario);
        generarSeccionImagen(panelFormulario); 
        generarBotones(panelFormulario);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(panelFormulario, BorderLayout.NORTH); 
        
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); 
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void generarCampos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;

        c.insets = new Insets(2, 5, 0, 5); c.gridy = 1; panel.add(new LblSubtitulo("Nombre:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 2; txtName = new JTextField(20); panel.add(txtName, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 4; panel.add(new LblSubtitulo("Apellidos:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 5; txtLastName = new JTextField(20); panel.add(txtLastName, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 7; panel.add(new LblSubtitulo("Teléfono:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 8; txtTelefono = new JTextField(20); panel.add(txtTelefono, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 10; panel.add(new LblSubtitulo("Fecha de Nac. (AAAA-MM-DD):"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 11; txtFechaNac = new JTextField(20); panel.add(txtFechaNac, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 13; panel.add(new LblSubtitulo("Género:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 14; 
        String[] opciones = {"Seleccionar","Feminine", "Masculine"};
        cmbGenero = new JComboBox<>(opciones); panel.add(cmbGenero, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 15; panel.add(new LblSubtitulo("Rol:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 17; 
        String[] roles = {"Seleccionar"}; 
        cmbRol = new JComboBox<>(roles); 
        panel.add(cmbRol, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 18; panel.add(new LblSubtitulo("Correo electrónico:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 19; txtCorreo = new JTextField(20); panel.add(txtCorreo, c);

        c.insets = new Insets(2, 5, 0, 5); c.gridy = 21; panel.add(new LblSubtitulo("Contraseña:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 22; txtContrasena = new JPasswordField(20); panel.add(txtContrasena, c);
        
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 23; panel.add(new LblSubtitulo("Confirmar contraseña:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 24; txtConfirmarContrasena = new JPasswordField(20); panel.add(txtConfirmarContrasena, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.insets = new Insets(0, 5, 2, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 10); 

        lblAvisoName = new LblAviso(""); lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso); c.gridy = 3; panel.add(lblAvisoName, c);
        lblAvisoLastName = new LblAviso(""); lblAvisoLastName.setForeground(Color.RED); lblAvisoLastName.setFont(fontAviso); c.gridy = 6; panel.add(lblAvisoLastName, c);
        lblAvisoTelefono = new LblAviso(""); lblAvisoTelefono.setForeground(Color.RED); lblAvisoTelefono.setFont(fontAviso); c.gridy = 9; panel.add(lblAvisoTelefono, c);
        lblAvisoFechaNac = new LblAviso(""); lblAvisoFechaNac.setForeground(Color.RED); lblAvisoFechaNac.setFont(fontAviso); c.gridy = 12; panel.add(lblAvisoFechaNac, c);
        c.gridy = 15; panel.add(new JLabel(" "), c); 
        lblAvisoCorreo = new LblAviso(""); lblAvisoCorreo.setForeground(Color.RED); lblAvisoCorreo.setFont(fontAviso); c.gridy = 18; panel.add(lblAvisoCorreo, c);
        lblAvisoContra = new LblAviso(""); lblAvisoContra.setForeground(Color.RED); lblAvisoContra.setFont(fontAviso); c.gridy = 21; panel.add(lblAvisoContra, c);
        lblAvisoConfirmar = new LblAviso(""); lblAvisoConfirmar.setForeground(Color.RED); lblAvisoConfirmar.setFont(fontAviso); c.gridy = 24; panel.add(lblAvisoConfirmar, c);
    }
    
    private void generarSeccionImagen(JPanel panel) {
    	GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
        
        c.insets = new Insets(2, 5, 2, 5); c.gridy = 25; panel.add(new LblSubtitulo("Foto de perfil (Opcional):"), c);
        c.insets = new Insets(0, 5, 2, 5); c.gridy = 26;
        lblImagePreview = new JLabel(); lblImagePreview.setPreferredSize(new Dimension(75,75));
		lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblImagePreview.setHorizontalAlignment(JLabel.CENTER); panel.add(lblImagePreview, c);
		
		c.gridy = 27; btnSelectImage = new JButton("Seleccionar imagen");
		btnSelectImage.setBackground(new Color(210, 180, 140)); btnSelectImage.setForeground(Color.BLACK);
		btnSelectImage.setFocusPainted(false); btnSelectImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSelectImage.addActionListener(e -> chooseImage()); panel.add(btnSelectImage, c);
		
		c.gridy = 28; c.insets = new Insets(0, 5, 0, 5);
		lblImageName = new JLabel("Ninguna imagen seleccionada"); lblImageName.setForeground(Color.LIGHT_GRAY);
		lblImageName.setFont(new Font("Arial", Font.PLAIN, 10)); lblImageName.setHorizontalAlignment(JLabel.CENTER); panel.add(lblImageName, c);
		
		c.gridy = 29; lblAvisoImage = new LblAviso(""); lblAvisoImage.setForeground(Color.RED); lblAvisoImage.setFont(new Font("Arial", Font.ITALIC, 10)); panel.add(lblAvisoImage, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 30; c.insets = new Insets(10, 5, 5, 5); 

        btnRegistrar = new JButton("Guardar");
        btnRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnRegistrar.setBackground(new Color(48, 60, 26)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(new LineBorder(Color.GRAY, 3, true));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){ btnRegistrar.setBackground(new Color(152, 158, 141)); }
            public void mouseExited(MouseEvent e){ btnRegistrar.setBackground(new Color(48, 60, 26)); }
        });
        panel.add(btnRegistrar, c);
        
        lblRegresar = new JLabel("<html><u>Cancelar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 31; c.insets = new Insets(0, 1, 15, 1);
        panel.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
        	public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
        	public void mouseClicked(MouseEvent e){ dispose(); }
        });
        
        btnRegistrar.addActionListener(e -> save());
    }

    private boolean verificarName() { if(txtName.getText().trim().equals("")) { lblAvisoName.setText("Requerido"); return false; } return true; }
    private boolean verificarLastName() { if(txtLastName.getText().trim().equals("")) { lblAvisoLastName.setText("Requerido"); return false; } return true; }
    private boolean verificarCorreo() { if(txtCorreo.getText().trim().equals("")) { lblAvisoCorreo.setText("Requerido"); return false; } return true; }
    private boolean verificarTelefono() { if(txtTelefono.getText().trim().equals("")) { lblAvisoTelefono.setText("Requerido"); return false; } return true; }
    private boolean verificarFechaNac() { 
    	String f = txtFechaNac.getText().trim();
    	if(f.equals("")) { lblAvisoFechaNac.setText("Requerido"); return false; }
    	if(!f.matches("^\\d{4}-\\d{2}-\\d{2}$")) { lblAvisoFechaNac.setText("Formato YYYY-MM-DD"); return false; }
    	return true; 
    }
    private boolean verificarPassword() { if(new String(txtContrasena.getPassword()).trim().equals("")) { lblAvisoContra.setText("Requerido"); return false; } return true; }
    private boolean verificarConfirmarPassword() {
        if(new String(txtConfirmarContrasena.getPassword()).trim().equals("") && new String(txtConfirmarContrasena.getPassword()).equals(new String(txtContrasena.getPassword()))){
        	lblAvisoConfirmar.setText("Confirme su contraseña"); return false;
        } return true;
    }

    private void verificarInstaName() {
		lblAvisoName.setText("");
		if(txtName.getText().trim().equals("")) {
			lblAvisoName.setText("Nombres requerido");
			lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
		} else if(txtName.getText().matches(".*\\d.*")) {
			lblAvisoName.setText("No debe contener números");
			lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
		}
	}
	   
	private void verificarInstaLastName() {
		lblAvisoLastName.setText("");
		if(txtLastName.getText().trim().equals("")) {
			lblAvisoLastName.setText("Apellidos requerido");
			lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
		} else if(txtLastName.getText().matches(".*\\d.*")) {
			lblAvisoLastName.setText("No debe contener números");
			lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
		}
	}
	   
	private void verificarInstaTelefono() { lblAvisoTelefono.setText(""); }
	private void verificarInstaFechaNac() { lblAvisoFechaNac.setText(""); }
	   
	private void verificarInstaCorreo() {
		lblAvisoCorreo.setText(" ");
		if(txtCorreo.getText().trim().equals("") ) {
			lblAvisoCorreo.setText("Correo requerido");
			lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
		} else if(!txtCorreo.getText().isEmpty() && !txtCorreo.getText().contains("@")) {
			lblAvisoCorreo.setText("Correo Inválido");
			lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
		}
	}
	   
	private void verificarInstaPassword() {
		boolean mayuscula = false;
		boolean numeros = false;
		boolean longitud = false;
		lblAvisoContra.setText(" ");
		   
		String pass = new String(txtContrasena.getPassword());
		if(pass.trim().equals("")) {
			lblAvisoContra.setText("Contraseña requerida");
			lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10));
		} else {
			if(pass.startsWith("$2a$")) { return; } 
			for(int i = 0; i < pass.length(); i++) {
				if (Character.isUpperCase(pass.charAt(i))) { mayuscula = true; }
				if (pass.matches(".*\\d.*")) { numeros = true; }
				if (pass.trim().length() >= 8 ) { longitud = true; }
			}
			if(!mayuscula) { lblAvisoContra.setText("Se necesita al menos una mayúscula"); lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10)); }
			else if(!numeros) { lblAvisoContra.setText("Se necesita al menos un número"); lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10)); }
			else if(!longitud) { lblAvisoContra.setText("Debe contener al menos 8 caracteres"); lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10)); }
		}
	}
	   
	private void verificarInstaConfiPassword() {
		lblAvisoConfirmar.setText(" ");
		String pass = new String(txtContrasena.getPassword());
		String confirm = new String(txtConfirmarContrasena.getPassword());
		if(confirm.trim().equals("") || !(confirm.equals(pass))){
			lblAvisoConfirmar.setText("Las contraseñas no coinciden");
			lblAvisoConfirmar.setFont(new Font("Arial", Font.ITALIC, 10));  
		}
	}
    
    private void eventosCampos(){
		  txtName.getDocument().addDocumentListener(crearListener(() -> verificarInstaName()));
		  txtLastName.getDocument().addDocumentListener(crearListener(() -> verificarInstaLastName()));
		  txtTelefono.getDocument().addDocumentListener(crearListener(() -> verificarInstaTelefono()));
		  txtFechaNac.getDocument().addDocumentListener(crearListener(() -> verificarInstaFechaNac()));
		  txtCorreo.getDocument().addDocumentListener(crearListener(() -> verificarInstaCorreo()));
		  txtContrasena.getDocument().addDocumentListener(crearListener(() -> verificarInstaPassword()));
		  txtConfirmarContrasena.getDocument().addDocumentListener(crearListener(() -> verificarInstaConfiPassword()));

		  txtName.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) e.consume(); } });
		  txtLastName.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) e.consume(); } });
		  txtTelefono.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if(!Character.isDigit(e.getKeyChar())) e.consume(); } });
		  txtFechaNac.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if(!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '-') e.consume(); } });
		  txtCorreo.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if(e.getKeyChar() == ' ') e.consume(); } });
		  txtContrasena.addKeyListener(new KeyAdapter() { public void keyTyped(KeyEvent e) { if(e.getKeyChar() == ' ') e.consume(); } });
	}

    private DocumentListener crearListener(Runnable accion) {
		  return new DocumentListener() {
			@Override public void removeUpdate(DocumentEvent e) { accion.run(); }
			@Override public void insertUpdate(DocumentEvent e) { accion.run(); }
			@Override public void changedUpdate(DocumentEvent e) { accion.run(); }
		  };
	}

    public void chooseImage() {
		String lastDirectory = Config.get("registration.image.last.directory", System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(lastDirectory);
		chooser.setDialogTitle("Seleccionar imagen de perfil");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png");
		chooser.setFileFilter(filter);
		int option = chooser.showOpenDialog(this);
		if(option == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			selectedImagePath = file.getAbsolutePath();
			lastDirectory = file.getParent();
			Config.set("registration.image.last.directory", lastDirectory);
			lblImageName.setText(file.getName());
			ImageIcon icon = new ImageIcon(selectedImagePath);
			Image img = icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH); 
			lblImagePreview.setIcon(new ImageIcon(img));
			lblAvisoImage.setText(""); 
		}
	}
    
    private String saveImage() {
		if (selectedImagePath == null || selectedImagePath.isEmpty()) return null;
		if (selectedImagePath.contains("images" + File.separator) || selectedImagePath.contains("images/")) return selectedImagePath;
		try {
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
			ex.printStackTrace(); return null;
		}
	}
    
    public void resetearAvisos() {
        lblAvisoName.setText(""); lblAvisoLastName.setText(""); lblAvisoTelefono.setText("");
        lblAvisoFechaNac.setText(""); lblAvisoCorreo.setText(""); lblAvisoContra.setText("");
        lblAvisoConfirmar.setText(""); lblAvisoImage.setText("");
    }
    
    private void loadData() {
    	if(user != null) {
    		txtName.setText(user.getName());
            txtLastName.setText(user.getLastName());
            txtTelefono.setText(user.getTelefono());
            txtFechaNac.setText(user.getFechaNacimiento());
            if(user.getGenero() != null) cmbGenero.setSelectedItem(user.getGenero());
            txtCorreo.setText(user.getEmail());
            txtContrasena.setText(user.getPassword());
            txtConfirmarContrasena.setText(user.getPassword());
            
            selectedImagePath = user.getImagePath();
            if(selectedImagePath != null && !selectedImagePath.isEmpty()) {
            	File file = new File(selectedImagePath);
            	if(file.exists()) {
            		lblImageName.setText(file.getName());
            		ImageIcon icon = new ImageIcon(selectedImagePath);
        			Image img = icon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH); 
        			lblImagePreview.setIcon(new ImageIcon(img));
            	}
            }
    	}
    }
    
    private void save() {
    	resetearAvisos();
        boolean valido = true;
        if (!verificarName()) valido = false; 
        if (!verificarLastName()) valido = false; 
        if (!verificarTelefono()) valido = false;
        if (!verificarFechaNac()) valido = false;
        if (!verificarCorreo()) valido = false;
        if (!verificarPassword()) valido = false;
        if (!verificarConfirmarPassword()) valido = false;
        if (!valido) return;
    	
    	String password = new String(txtContrasena.getPassword());
    	if (!password.startsWith("$2a$")) { password = PasswordUtils.hashPassword(password); }
        
    	String finalImagePath = saveImage();
    	String genero = (String) cmbGenero.getSelectedItem();
    	
    	String rol = (String) cmbRol.getSelectedItem();
    	if (rol != null && rol.equals("Seleccionar")) {
    	    rol = null; 
    	}
    	
        if(user == null) {
        	user = new User(txtName.getText().trim(), txtLastName.getText().trim(), txtCorreo.getText().trim(), password, finalImagePath, txtTelefono.getText().trim(), genero, txtFechaNac.getText().trim(),rol);
        }else {
        	user.setName(txtName.getText().trim());
        	user.setLastName(txtLastName.getText().trim());
        	user.setTelefono(txtTelefono.getText().trim());
        	user.setFechaNacimiento(txtFechaNac.getText().trim());
        	user.setGenero(genero);
        	user.setEmail(txtCorreo.getText().trim());
        	user.setPassword(password);
        	if (finalImagePath != null) user.setImagePath(finalImagePath);
        	user.setRol(rol);
        }
        saved = true; dispose();
    }

    public boolean isSaved() { return saved; }
    public User getUser() { return user; }

	public JComboBox<String> getCmbRol() {return cmbRol;}
	public void setCmbRol(JComboBox<String> cmbRol) {this.cmbRol = cmbRol;}
    
    
}