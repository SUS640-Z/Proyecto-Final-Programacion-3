package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class LoginView2 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginView2 frame = new LoginView2();
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
	public LoginView2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 603, 604);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setBackground(new Color(0, 0, 0));
		contentPane.setLayout(null);
		
		//Boton para iniciar sesion
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(95,320, 316, 40);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));				
		getContentPane().add(btnConfimar);
				
		//USUARIO
		JLabel labelUsuario = new JLabel("USUARIO");
		labelUsuario.setBounds(96, 153, 120, 25);
		labelUsuario.setForeground(Color.WHITE);
		labelUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(labelUsuario);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(95, 177, 316, 25);
		getContentPane().add(txtUsuario);

		// CONTRASENA
		JLabel labelContrasena = new JLabel("CONTRASENA");
		labelContrasena.setBounds(95, 228, 140, 25);
		labelContrasena.setForeground(Color.WHITE);
		labelContrasena.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		getContentPane().add(labelContrasena);

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(95, 254, 316, 25);
		getContentPane().add(txtContrasena);
		
		//Titulo de inicio de sesion
		JLabel lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		lblIniciarSesion.setForeground(new Color(255, 255, 255));
		lblIniciarSesion.setBounds(117, 40, 254, 62);
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblIniciarSesion);
		
		JLabel lblErrorIngresarDatos = new JLabel("Usuario y/o Contraseña son incorrectos");
		lblErrorIngresarDatos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblErrorIngresarDatos.setBackground(new Color(128, 255, 255));
		lblErrorIngresarDatos.setBounds(105, 361, 321, 57);
		lblErrorIngresarDatos.setForeground(new Color(96, 45, 20));
		getContentPane().add(lblErrorIngresarDatos);
		
		JLabel lblContraseaRequerida = new JLabel("Contraseña Requerida");
		lblContraseaRequerida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContraseaRequerida.setBackground(new Color(128, 255, 255));
		lblContraseaRequerida.setBounds(299, 269, 261, 57);
		lblContraseaRequerida.setForeground(new Color(96, 45, 20));
		getContentPane().add(lblContraseaRequerida);
		
		JLabel lblUsuarioRequerido = new JLabel("Usuario Requerido");
		lblUsuarioRequerido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuarioRequerido.setBackground(new Color(128, 255, 255));
		lblUsuarioRequerido.setBounds(299, 189, 261, 57);
		lblUsuarioRequerido.setForeground(new Color(96, 45, 20));
		getContentPane().add(lblUsuarioRequerido);

	}
}
