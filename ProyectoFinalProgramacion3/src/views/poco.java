package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;

public class Poco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Poco frame = new Poco();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Poco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(143,317, 316, 40);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));				
		getContentPane().add(btnConfimar);
				
		//USUARIO
		JLabel labelUsuario = new JLabel("USUARIO");
		labelUsuario.setBounds(133, 131, 120, 25);
		labelUsuario.setForeground(Color.WHITE);
		labelUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(labelUsuario);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(143, 155, 316, 25);
		getContentPane().add(txtUsuario);

		// CONTRASENA
		JLabel labelContrasena = new JLabel("CONTRASENA");
		labelContrasena.setBounds(133, 220, 140, 25);
		labelContrasena.setForeground(Color.WHITE);
		labelContrasena.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(labelContrasena);

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(143, 246, 316, 25);
		getContentPane().add(txtContrasena);
		
		//Titulo de inicio de sesion
		JLabel lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		lblIniciarSesion.setForeground(new Color(255, 255, 255));
		lblIniciarSesion.setBounds(169, 41, 254, 62);
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblIniciarSesion);
		
		JLabel lblErrorIngresarDatos = new JLabel("Usuario y/o Contraseña son incorrectos");
		lblErrorIngresarDatos.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorIngresarDatos.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblErrorIngresarDatos.setBackground(new Color(128, 255, 255));
		lblErrorIngresarDatos.setBounds(143, 357, 316, 57);
		lblErrorIngresarDatos.setForeground(new Color(195, 136, 93));
		getContentPane().add(lblErrorIngresarDatos);
		
		JLabel lblContraseaRequerida = new JLabel("Contraseña Requerida");
		lblContraseaRequerida.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblContraseaRequerida.setBackground(new Color(128, 255, 255));
		lblContraseaRequerida.setBounds(357, 253, 140, 57);
		lblContraseaRequerida.setForeground(new Color(195, 136, 93));
		getContentPane().add(lblContraseaRequerida);
		
		JLabel lblUsuarioRequerido = new JLabel("Usuario Requerido");
		lblUsuarioRequerido.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblUsuarioRequerido.setBackground(new Color(128, 255, 255));
		lblUsuarioRequerido.setBounds(357, 166, 140, 57);
		lblUsuarioRequerido.setForeground(new Color(195, 136, 93));
		getContentPane().add(lblUsuarioRequerido);
		
		
		JLabel lblCafeImg = new JLabel("");
		lblCafeImg.setBounds(125, 320, 350, 350);
		lblCafeImg.setIcon(cargarIcono("../img/cafe.png", 350, 350));
		getContentPane().add(lblCafeImg);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 255, 128));
		panel.setBounds(10, 53, 299, 218);
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(270, 228, 299, 57);
		contentPane.add(panel_1_1);
		panel_1_1.setBackground(new Color(255, 255, 128));
		panel_1_1.setLayout(new BoxLayout(panel_1_1, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(413, 27, 299, 218);
		contentPane.add(panel_1);
		panel_1.setBackground(new Color(128, 255, 255));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
	}
	
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
}
