package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import components.BtnDirecion;
import components.LblAviso;
import components.LblSubtitulo;
import controllers.RegistroController;
import config.Config;

public class RegistroView extends JFrame {

    JPanel contentPane;
    JTextField txtName;
    JTextField txtLastName;
    JTextField txtCorreo;
    JPasswordField txtContrasena;
    JPasswordField txtConfirmarContrasena;
    
    JTextField txtTelefono;
    JTextField txtFechaNac;
    JComboBox<String> cmbGenero;
    
    LblAviso lblAvisoName;
    LblAviso lblAvisoLastName;
    LblAviso lblAvisoCorreo;
    LblAviso lblAvisoContra;
    LblAviso lblAvisoConfirmar;
    LblAviso lblAvisoImage; 
    LblAviso lblAvisoTelefono;
    LblAviso lblAvisoFechaNac;
    
    BtnDirecion btnConfirmar2;
    JPanel panelFormulario;
    JButton btnRegistrar;
    JLabel lblRegresar;
    
    private JButton btnSelectImage;
	private JLabel lblImagePreview;
	private JLabel lblImageName;
	private String selectedImagePath;

    public static void main(String[] args) {
    	RegistroView vista = new RegistroView();
    	new RegistroController(vista);
    }

    public RegistroView() {
        setTitle("Saturnbucks - Registro");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 400, 600); 
        setResizable(true);
        setLocationRelativeTo(null);
        
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
                "REGISTRO", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);

        generarComponentes();
        aplicarEventoFocus();
        resetearAvisos();
        setVisible(true);
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
    		txtCorreo.addFocusListener(efectoFocus);
    		txtTelefono.addFocusListener(efectoFocus);
    		txtFechaNac.addFocusListener(efectoFocus);
    		txtContrasena.addFocusListener(efectoFocus);
    		txtConfirmarContrasena.addFocusListener(efectoFocus);
    }
    
	private void generarComponentes() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false); 
        
        generarTitulos(panelFormulario);
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

    private void generarTitulos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.gridy = 0; c.insets = new Insets(5, 5, 10, 5); 
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        lblTitulo.setForeground(Color.WHITE); lblTitulo.setHorizontalAlignment(JLabel.CENTER); panel.add(lblTitulo, c);
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
        String[] opcionesGenero = {"Seleccionar", "Fememenino", "Masculino"};
        cmbGenero = new JComboBox<>(opcionesGenero); panel.add(cmbGenero, c);

        c.insets = new Insets(2, 5, 0, 5); c.gridy = 16; panel.add(new LblSubtitulo("Correo electrónico:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 17; txtCorreo = new JTextField(20); panel.add(txtCorreo, c);

        c.insets = new Insets(2, 5, 0, 5); c.gridy = 19; panel.add(new LblSubtitulo("Contraseña:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 20; txtContrasena = new JPasswordField(20); panel.add(txtContrasena, c);

        c.insets = new Insets(2, 5, 0, 5); c.gridy = 22; panel.add(new LblSubtitulo("Confirmar contraseña:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 23; txtConfirmarContrasena = new JPasswordField(20); panel.add(txtConfirmarContrasena, c);
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
        
        c.insets = new Insets(2, 5, 2, 5); c.gridy = 25; panel.add(new LblSubtitulo("Foto de perfil:"), c);
        c.insets = new Insets(0, 5, 2, 5); c.gridy = 26;
        lblImagePreview = new JLabel(); lblImagePreview.setPreferredSize(new Dimension(75,75));
		lblImagePreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lblImagePreview.setHorizontalAlignment(JLabel.CENTER); panel.add(lblImagePreview, c);
		
		c.gridy = 27; btnSelectImage = new JButton("Seleccionar imagen");
		btnSelectImage.setBackground(new Color(210, 180, 140)); btnSelectImage.setForeground(Color.BLACK);
		btnSelectImage.setFocusPainted(false); btnSelectImage.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(btnSelectImage, c);
		
		c.gridy = 28; c.insets = new Insets(0, 5, 0, 5);
		lblImageName = new JLabel("Ninguna imagen seleccionada"); lblImageName.setForeground(Color.LIGHT_GRAY);
		lblImageName.setFont(new Font("Arial", Font.PLAIN, 10)); lblImageName.setHorizontalAlignment(JLabel.CENTER); panel.add(lblImageName, c);
		
		c.gridy = 29; lblAvisoImage = new LblAviso(""); lblAvisoImage.setForeground(Color.RED); lblAvisoImage.setFont(new Font("Arial", Font.ITALIC, 10));
		panel.add(lblAvisoImage, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 30; c.insets = new Insets(10, 5, 5, 5); 

        btnRegistrar = new JButton("Registrarme");
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
        
        lblRegresar = new JLabel("<html><u>Regresar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 31; c.insets = new Insets(0, 1, 15, 1);
        panel.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
        	public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
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
    
    public void resetearAvisos() {
        lblAvisoName.setText(""); lblAvisoLastName.setText(""); lblAvisoTelefono.setText("");
        lblAvisoFechaNac.setText(""); lblAvisoCorreo.setText(""); lblAvisoContra.setText("");
        lblAvisoConfirmar.setText(""); lblAvisoImage.setText(""); 
    }
 
	public JTextField getTxtName() {return txtName;}
	public JTextField getTxtLastName() {return txtLastName;}
	public JTextField getTxtCorreo() {return txtCorreo;}
	public JPasswordField getTxtContrasena() {return txtContrasena;}
	public JPasswordField getTxtConfirmarContrasena() {return txtConfirmarContrasena;}
	public BtnDirecion getBtnConfirmar2() {return btnConfirmar2;}
	public JButton getBtnRegistrar() {return btnRegistrar;}
	public LblAviso getLblAvisoName() {return lblAvisoName;}
	public LblAviso getLblAvisoLastName() {return lblAvisoLastName;}
	public LblAviso getLblAvisoCorreo() {return lblAvisoCorreo;}
	public LblAviso getLblAvisoContra() {return lblAvisoContra;}
	public LblAviso getLblAvisoConfirmar() {return lblAvisoConfirmar;}
	public JLabel getLblRegresar() {return lblRegresar;}
	public JButton getBtnSelectImage() { return btnSelectImage; }
	public String getSelectedImagePath() { return selectedImagePath; }
	public LblAviso getLblAvisoImage() { return lblAvisoImage; }
	
	public JTextField getTxtTelefono() { return txtTelefono; }
	public JTextField getTxtFechaNac() { return txtFechaNac; }
	public JComboBox<String> getCmbGenero() { return cmbGenero; }
	public LblAviso getLblAvisoTelefono() { return lblAvisoTelefono; }
	public LblAviso getLblAvisoFechaNac() { return lblAvisoFechaNac; }
}