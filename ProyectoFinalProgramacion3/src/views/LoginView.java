package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;

import components.BordePanel;
import components.LblAviso;
import components.LblSubtitulo;

public class LoginView extends JFrame{
	
	public LoginView() {
		//setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SaturnBucks");
		//setLocation(100,100);
		setBounds(200,100,600,640); // cordenadas y tamaño
		setResizable(false);
		setLocationRelativeTo(null);// Colocar la ventana el centro
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/img/SATURN_BUCKS_3.png");
		setIconImage(icono);
		//Panel panelito = new Panel();
		//add(panelito);
		BordePanel login = new BordePanel("LOG IN",Color.white);
		login.setBackground(new Color(15, 19, 9));
		generarComponentes();
		add(login);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	
	JTextField txtUsuario;
	JPasswordField txtContrasena;
	
	LblAviso lblErrorIngresarDatos;
	LblAviso lblUsuarioRequerido;
	LblAviso lblContraseaRequerida;
	
	JLabel lblIniciarSesion;
	LblSubtitulo lblUsuario;
	LblSubtitulo lblContasena;
	
	JLabel lblCafeImg;
	
	
	private ImageIcon cargarIcono(String ruta, int ancho, int largo) {

		try {
			Image icono = ImageIO.read(getClass().getResource(ruta));
			icono = icono.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
			return new ImageIcon(icono);
		}catch(Exception ex) {
			System.out.println("No está la imagen del ícono");
		}
		
		return null;
	}
	
	public void paintComponent(Graphics g) {
		paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Image fondo = null;
		try {
			fondo=ImageIO.read(new File(""));
			
			g2.drawImage(fondo, 0, 0,getWidth(),getHeight(),null);
			
		}catch (IOException ex) {
			System.out.println("La imagen no existe");
		
		}
	}
	
	private void generarBotones() {
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(143,300, 316, 40);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));	
		add(btnConfimar);
		
		btnConfimar.addActionListener( e -> validarForm());
		
		JLabel lblCrearCuenta = new JLabel("<html><u>Crea una cuenta</u></html>");
		lblCrearCuenta.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		lblCrearCuenta.setForeground(Color.WHITE);
		lblCrearCuenta.setBounds(240,350, 180, 30);
		lblCrearCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lblCrearCuenta.setAlignmentX(JLabel.CENTER);
		add(lblCrearCuenta);

		
		lblCrearCuenta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                reenviarRegistro();
            }
        });
	}
	
	private void reenviarRegistro() {
		new RegistroView();
		this.dispose();
		
	}

	private void generarAvisos() {
		lblErrorIngresarDatos = new LblAviso("");
		lblErrorIngresarDatos.setBounds(143, 357, 316, 57);
		add(lblErrorIngresarDatos);
		
		lblContraseaRequerida = new LblAviso("");
		lblContraseaRequerida.setBounds(357, 253, 140, 57);
		add(lblContraseaRequerida);
		
		lblUsuarioRequerido = new LblAviso("");
		lblUsuarioRequerido.setBounds(357, 166, 140, 57);
		add(lblUsuarioRequerido);
	}
	
	private void generarTitulos() {
		lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblIniciarSesion.setForeground(new Color(255, 255, 255));
		lblIniciarSesion.setBounds(130, 41, 350, 62);
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblIniciarSesion);
		
		lblUsuario = new LblSubtitulo("USUARIO: ");
		lblUsuario.setBounds(133, 131, 220, 25);
		lblUsuario.setForeground(Color.WHITE);
		add(lblUsuario);
		
		lblContasena = new LblSubtitulo("CONTRASEÑA:");
		lblContasena.setBounds(133, 220, 240, 25);
		lblContasena.setForeground(Color.WHITE);
		add(lblContasena);
	}
	
	private void generarCamposDeTexto() {
		txtUsuario = new JTextField();
		txtUsuario.putClientProperty("JTextField.placeholderText", "Ingresa tu nombre de usuario");
		txtUsuario.setBounds(143, 155, 316, 25);
		add(txtUsuario);

		txtContrasena = new JPasswordField();
		txtContrasena.putClientProperty("JTextField.placeholderText", "Ingresa tu contrasena");
		txtContrasena.setBounds(143, 246, 316, 25);
		add(txtContrasena);
	}
	
	private void generarImagen() {
		lblCafeImg = new JLabel("", cargarIcono("../img/cafe.png", 300, 300), JLabel.CENTER);
		lblCafeImg.setBounds(145, 290, 350, 350); 
		add(lblCafeImg);
	}
	
	private void generarComponentes() {
		generarTitulos();
		generarCamposDeTexto();
		generarBotones();
		generarAvisos();
		generarImagen();
	}
	
	private void validarForm() {
		resetearAvisos();
		
		boolean valido = true;
		
		if(!verificarUsuario()) {
			valido = false;
		}
		
		if(!verificarPassword()) {
			valido = false;
		}
		
		if(valido) {
			//new Dirreccion();
		}
	}
	
	private void resetearAvisos() {
		lblUsuarioRequerido.setText("");
		lblContraseaRequerida.setText("");
	}
	
	private boolean verificarUsuario() {
		if(txtUsuario.getText().trim().equals("")) {
			lblUsuarioRequerido.setText("Usuario Requerido");
			lblUsuarioRequerido.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			return false;
		}
		return true;
	}
	
	private boolean verificarPassword() {
		if(new String(txtContrasena.getPassword()).trim().equals("")) {
			lblContraseaRequerida.setText("Contraseña Requerida");
			lblContraseaRequerida.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			return false;
		}
		return true;
	}
	
	
	
}
