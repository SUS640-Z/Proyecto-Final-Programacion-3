package views;

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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
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
    LblAviso lblAvisoName;
    LblAviso lblAvisoLastName;
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
        setBounds(100, 100, 350, 740); 
        setResizable(false);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

        contentPane = new JPanel();
        contentPane.setBackground(new Color(15, 19, 9)); 

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border panelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                "REGISTRO", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

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
        public void focusGained(FocusEvent e) {
            ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeActivo, 2));
        }

        @Override
        public void focusLost(FocusEvent e) {
            ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeInactivo, 1));
        }
    };
    
    private void aplicarEventoFocus(){
    		txtName.addFocusListener(efectoFocus);
    		txtLastName.addFocusListener(efectoFocus);
    		txtCorreo.addFocusListener(efectoFocus);
    		txtContrasena.addFocusListener(efectoFocus);
    		txtConfirmarContrasena.addFocusListener(efectoFocus);
    }
    
	private void generarComponentes() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        contentPane.add(panelFormulario, gbc);

        generarTitulos(panelFormulario);
        generarCampos(panelFormulario);
        generarAvisos(panelFormulario);
        generarSeccionImagen(panelFormulario); 
        generarBotones(panelFormulario);
    }

    private void generarTitulos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 10, 5); 
        JLabel lblTitulo = new JLabel(user == null ? "Crear Cuenta" : "Editar Cuenta");
        lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo, c);
    }

    private void generarCampos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 1;
        LblSubtitulo lblName = new LblSubtitulo("Nombre:");
        panel.add(lblName, c);
        
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 2;
        txtName = new JTextField(20);
        panel.add(txtName, c);
        
        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 4;
        LblSubtitulo lblLastName = new LblSubtitulo("Apellidos:");
        panel.add(lblLastName, c);
        
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 5;
        txtLastName = new JTextField(20);
        panel.add(txtLastName, c);
        
        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 7;
        LblSubtitulo lblCorreo = new LblSubtitulo("Correo electronico:");
        panel.add(lblCorreo, c);
        
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 8;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, c);

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 10;
        LblSubtitulo lblContra = new LblSubtitulo("Contraseña:");
        panel.add(lblContra, c);
        
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 11;
        txtContrasena = new JPasswordField(20);
        panel.add(txtContrasena, c);
        
        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 13;
        LblSubtitulo lblConf = new LblSubtitulo("Confirmar contraseña:");
        panel.add(lblConf, c);
        
        c.insets = new Insets(0, 5, 5, 5);
        c.gridy = 14;
        txtConfirmarContrasena = new JPasswordField(20);
        panel.add(txtConfirmarContrasena, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 2, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 10);

        lblAvisoName = new LblAviso("");
        lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso);
        c.gridy = 3; panel.add(lblAvisoName, c);

        lblAvisoLastName = new LblAviso("");
        lblAvisoLastName.setForeground(Color.RED); lblAvisoLastName.setFont(fontAviso);
        c.gridy = 6; panel.add(lblAvisoLastName, c);
        
        lblAvisoCorreo = new LblAviso("");
        lblAvisoCorreo.setForeground(Color.RED); lblAvisoCorreo.setFont(fontAviso);
        c.gridy = 9; panel.add(lblAvisoCorreo, c);

        lblAvisoContra = new LblAviso("");
        lblAvisoContra.setForeground(Color.RED); lblAvisoContra.setFont(fontAviso);
        c.gridy = 12; panel.add(lblAvisoContra, c);
        
        lblAvisoConfirmar = new LblAviso("");
        lblAvisoConfirmar.setForeground(Color.RED); lblAvisoConfirmar.setFont(fontAviso);
        c.gridy = 15; panel.add(lblAvisoConfirmar, c);
    }
    
    private void generarSeccionImagen(JPanel panel) {
    	GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        
        c.insets = new Insets(2, 5, 2, 5);
        c.gridy = 16;
        LblSubtitulo lblFoto = new LblSubtitulo("Foto de perfil (Opcional):");
        panel.add(lblFoto, c);
        
        c.insets = new Insets(0, 5, 2, 5);
        c.gridy = 17;
        lblImagePreview = new JLabel();
		lblImagePreview.setPreferredSize(new Dimension(75,75));
		lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblImagePreview.setHorizontalAlignment(JLabel.CENTER);
		panel.add(lblImagePreview, c);
		
		c.gridy = 18;
		btnSelectImage = new JButton("Seleccionar imagen");
		btnSelectImage.setBackground(new Color(210, 180, 140)); 
		btnSelectImage.setForeground(Color.BLACK);
		btnSelectImage.setFocusPainted(false);
		btnSelectImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnSelectImage.addActionListener(e -> chooseImage());
		panel.add(btnSelectImage, c);
		
		c.gridy = 19;
		c.insets = new Insets(0, 5, 0, 5);
		lblImageName = new JLabel("Ninguna imagen seleccionada");
		lblImageName.setForeground(Color.LIGHT_GRAY);
		lblImageName.setFont(new Font("Arial", Font.PLAIN, 10));
		lblImageName.setHorizontalAlignment(JLabel.CENTER);
		panel.add(lblImageName, c);
		
		c.gridy = 20;
		lblAvisoImage = new LblAviso("");
		lblAvisoImage.setForeground(Color.RED);
		lblAvisoImage.setFont(new Font("Arial", Font.ITALIC, 10));
		panel.add(lblAvisoImage, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 21; 
        c.insets = new Insets(10, 5, 5, 5); 

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
        
        c.gridy = 22; 
        c.insets = new Insets(0, 1, 1, 1);
        panel.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
        	public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
        	public void mouseClicked(MouseEvent e){ dispose(); }
        });
        
        btnRegistrar.addActionListener(e -> save());
    }
    
    // --- VALIDACIONES ---
    private boolean verificarName() {
        if(txtName.getText().trim().equals("")) {
            lblAvisoName.setText("Nombre requerido");
            lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private boolean verificarLastName() {
        if(txtLastName.getText().trim().equals("")) {
        	lblAvisoLastName.setText("Apellido requerido");
        	lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }

    private boolean verificarCorreo() {
        if(txtCorreo.getText().trim().equals("")) {
        	lblAvisoCorreo.setText("Correo requerido");
        	lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private boolean verificarPassword() {
        if(new String(txtContrasena.getPassword()).trim().equals("")) {
        	lblAvisoContra.setText("Contraseña requerida");
        	lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }

    private boolean verificarConfirmarPassword() {
        if(new String(txtConfirmarContrasena.getPassword()).trim().equals("") && new String(txtConfirmarContrasena.getPassword()).equals(new String(txtContrasena.getPassword()))){
        	lblAvisoConfirmar.setText("Confirme su contraseña");
        	lblAvisoConfirmar.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private void verificarInstaName() {
    	lblAvisoName.setText("");
        if(txtName.getText().trim().equals("")) {
      	    lblAvisoName.setText("Nombres requerido");
      	    lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
        } else {
        	if(txtName.getText().matches(".*\\d.*")) {
        		lblAvisoName.setText("No debe contener números");
      	    }
        }
    }
    
    private void verificarInstaLastName() {
  	    lblAvisoLastName.setText("");
  	    if(txtLastName.getText().trim().equals("")) {
  	    	lblAvisoLastName.setText("Apellidos requerido");
  	    	lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
  	    } else {
    		if(txtLastName.getText().matches(".*\\d.*")) {
    			lblAvisoLastName.setText("No debe contener números");
   		    }
        }
    }
    
    private void verificarInstaCorreo() {
  	    lblAvisoCorreo.setText(" ");
  	    if(txtCorreo.getText().trim().equals("") ) {
  	    	lblAvisoCorreo.setText("Correo requerido");
  	    	lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
  	    }
  	    if(!txtCorreo.getText().isEmpty() && !txtCorreo.getText().contains("@")) {
  	    	lblAvisoCorreo.setText("Correo Inválido");
  	    	lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
  	    }
    }
    
    private void verificarInstaPassword() {
    	boolean mayuscula=false;
    	boolean numeros=false;
    	boolean longitud=false;
    	lblAvisoContra.setText(" ");
  	    
    	String pass = new String(txtContrasena.getPassword());
    	
    	if(pass.trim().equals("")) {
  	    	lblAvisoContra.setText("Contraseña requerida");
  	    	lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10));
  	    } else {
  	    	if(pass.startsWith("$2a$")) { return; }
  	    	
  		    for(int i = 0; i < pass.length(); i++) {
  			    if (Character.isUpperCase(pass.charAt(i))) { mayuscula=true; }
  			    if(pass.matches(".*\\d.*")) { numeros=true; }
  			    if(pass.trim().length() >= 8 ) { longitud=true; }
  		    }
  		   
  		    if(!mayuscula) { lblAvisoContra.setText("Se necesita al menos una mayúscula"); }
  		    if(!numeros) { lblAvisoContra.setText("Se necesita al menos un número"); }
  		    if(!longitud) { lblAvisoContra.setText("Debe contener al menos 8 caracteres"); }
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
  	    txtName.getDocument().addDocumentListener(new DocumentListener() {
  			@Override public void removeUpdate(DocumentEvent e) { verificarInstaName(); }
  			@Override public void insertUpdate(DocumentEvent e) { verificarInstaName(); }
  			@Override public void changedUpdate(DocumentEvent e) { verificarInstaName(); }
  		});
  		
  	    txtName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
					e.consume();
				}
				if(txtName.getText().length() >= 25) { e.consume(); }
			}
		});
  		
  		txtLastName.getDocument().addDocumentListener(new DocumentListener() {
  			@Override public void removeUpdate(DocumentEvent e) { verificarInstaLastName(); }
  			@Override public void insertUpdate(DocumentEvent e) { verificarInstaLastName(); }
  			@Override public void changedUpdate(DocumentEvent e) { verificarInstaLastName(); }
  		});
  		
  		txtLastName.addKeyListener(new KeyAdapter() {
  			public void keyTyped(KeyEvent e) {
  				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
  					e.consume();
  				}
  				if(txtLastName.getText().length() >= 25) { e.consume(); }
  			}
  		});

  		txtCorreo.getDocument().addDocumentListener(new DocumentListener() {
  			@Override public void removeUpdate(DocumentEvent e) { verificarInstaCorreo(); }
  			@Override public void insertUpdate(DocumentEvent e) { verificarInstaCorreo(); }
  			@Override public void changedUpdate(DocumentEvent e) { verificarInstaCorreo(); }
  		});
  		
  		txtCorreo.addKeyListener(new KeyAdapter() {
  			public void keyTyped(KeyEvent e) {
  				if(e.getKeyChar() == ' ') { e.consume(); }
  				if(txtCorreo.getText().length() >= 50) { e.consume(); }
  			}
  		});
    
  	    txtContrasena.getDocument().addDocumentListener(new DocumentListener() {
  			@Override public void removeUpdate(DocumentEvent e) { verificarInstaPassword(); }
  			@Override public void insertUpdate(DocumentEvent e) { verificarInstaPassword(); }
  			@Override public void changedUpdate(DocumentEvent e) { verificarInstaPassword(); }
  		});
  	    
  	    txtContrasena.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == ' ') { e.consume(); }
				if((new String(txtContrasena.getPassword())).length() >= 70) { e.consume(); } 
			}
		});
  	  
  	    txtConfirmarContrasena.getDocument().addDocumentListener(new DocumentListener() {
  			@Override public void removeUpdate(DocumentEvent e) { verificarInstaConfiPassword(); }
  			@Override public void insertUpdate(DocumentEvent e) { verificarInstaConfiPassword(); }
  			@Override public void changedUpdate(DocumentEvent e) { verificarInstaConfiPassword(); }
  		});
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
		
		if (selectedImagePath.contains("images" + File.separator) || selectedImagePath.contains("images/")) {
			return selectedImagePath;
		}

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
			ex.printStackTrace();
			return null;
		}
	}
    
    public void resetearAvisos() {
        lblAvisoName.setText("");
        lblAvisoCorreo.setText("");
        lblAvisoContra.setText("");
        lblAvisoConfirmar.setText("");
        lblAvisoImage.setText("");
    }
    
    private void loadData() {
    	if(user != null) {
    		txtName.setText(user.getName());
            txtLastName.setText(user.getLastName());
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
        if (!verificarCorreo()) valido = false;
        if (!verificarPassword()) valido = false;
        if (!verificarConfirmarPassword()) valido = false;
        
        if (!valido) {
        	return;
        }
    	
    	String name = txtName.getText().trim();
    	String lastName = txtLastName.getText().trim();
    	String correo = txtCorreo.getText().trim();
    	String password = new String(txtContrasena.getPassword());
    	if (!password.startsWith("$2a$")) { 
    		password = PasswordUtils.hashPassword(password);
    	}
        
    	String finalImagePath = saveImage();
    	
        if(user == null) {
        	user = new User(name, lastName, correo, password, finalImagePath);
        }else {
        	user.setName(name);
        	user.setLastName(lastName);
        	user.setEmail(correo);
        	user.setPassword(password);
        	
        	if (finalImagePath != null) {
        		user.setImagePath(finalImagePath);
        	}
        }
        
        saved = true;
        dispose();
    }

    public boolean isSaved() { return saved; }
    public User getUser() { return user; }
}