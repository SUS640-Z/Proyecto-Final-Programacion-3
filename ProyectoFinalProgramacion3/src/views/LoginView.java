package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPasswordField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import components.LblAviso;
import components.LblSubtitulo;

public class LoginView extends JPanel{
	public LoginView(){
		setLayout(null);
		setBackground(new Color(15, 19, 9));
		
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(143,317, 316, 40);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));	
		add(btnConfimar);
				
		//USUARIO
		LblSubtitulo lblUsuario = new LblSubtitulo("USUARIO: ");
		lblUsuario.setBounds(133, 131, 120, 25);
		add(lblUsuario);
		
		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(143, 155, 316, 25);
		add(txtUsuario);

		// CONTRASENA
		LblSubtitulo lblContasena = new LblSubtitulo("CONTRASEÑA:");
		lblContasena.setBounds(133, 220, 140, 25);
		add(lblContasena);

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(143, 246, 316, 25);
		add(txtContrasena);
		
		//Titulo de inicio de sesion
		JLabel lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		lblIniciarSesion.setForeground(new Color(255, 255, 255));
		lblIniciarSesion.setBounds(169, 41, 254, 62);
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblIniciarSesion);
		
		LblAviso lblErrorIngresarDatos = new LblAviso("");
		lblErrorIngresarDatos.setBounds(143, 357, 316, 57);
		add(lblErrorIngresarDatos);
		
		LblAviso lblContraseaRequerida = new LblAviso("");
		lblContraseaRequerida.setBounds(357, 253, 140, 57);
		add(lblContraseaRequerida);
		
		LblAviso lblUsuarioRequerido = new LblAviso("");
		lblUsuarioRequerido.setBounds(357, 166, 140, 57);
		add(lblUsuarioRequerido);
		
		
		JLabel lblCafeImg = new JLabel("");
		lblCafeImg.setBounds(125, 320, 350, 350);
		lblCafeImg.setIcon(cargarIcono("../img/cafe.png", 350, 350));
		add(lblCafeImg);
		
		
		btnConfimar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				if(txtUsuario.getText().trim().equals("")) {
					lblUsuarioRequerido.setText("Usuario Requerido");
				}else{
					lblUsuarioRequerido.setText("");
				}
				
				if(new String(txtContrasena.getPassword()).trim().equals("")) {
					lblContraseaRequerida.setText("Contraseña Requerida");
				}else{
					lblContraseaRequerida.setText("");
				}
			}
		});
		
		
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
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Image fondo = null;
		try {
			fondo=ImageIO.read(new File(""));
			
			g2.drawImage(fondo, 0, 0,getWidth(),getHeight(),null);
			
		}catch (IOException ex) {
			System.out.println("La imagen no existe");
		
		}
	}
	
	
}
