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

import components.LblAviso;
import components.LblSubtitulo;

public class Ayudante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ayudante frame = new Ayudante();
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
	public Ayudante() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 608);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		setBackground(new Color(15, 19, 9));
		
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(143,317, 316, 40);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));	
		getContentPane().add(btnConfimar);
				
		//USUARIO
		LblSubtitulo lblUsuario = new LblSubtitulo("USUARIO");
		lblUsuario.setBounds(133, 131, 120, 25);
		getContentPane().add(lblUsuario);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(143, 155, 316, 25);
		getContentPane().add(txtUsuario);

		// CONTRASENA
		LblSubtitulo lblContasena = new LblSubtitulo("CONTRASEÑA");
		lblContasena.setBounds(133, 220, 140, 25);
		getContentPane().add(lblContasena);

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
		
		LblAviso lblErrorIngresarDatos = new LblAviso("");
		lblErrorIngresarDatos.setBounds(143, 357, 316, 57);
		getContentPane().add(lblErrorIngresarDatos);
		
		LblAviso lblContraseaRequerida = new LblAviso("");
		lblContraseaRequerida.setBounds(357, 253, 140, 57);
		getContentPane().add(lblContraseaRequerida);
		
		LblAviso lblUsuarioRequerido = new LblAviso("");
		lblUsuarioRequerido.setBounds(357, 166, 140, 57);
		getContentPane().add(lblUsuarioRequerido);
		
		
		JLabel lblCafeImg = new JLabel("");
		lblCafeImg.setBounds(125, 320, 350, 350);
		lblCafeImg.setIcon(cargarIcono("../img/cafe.png", 350, 350));
		getContentPane().add(lblCafeImg);
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

