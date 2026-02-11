package views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginView extends JPanel{
	public LoginView(){
		setLayout(null);
		setBackground(new Color(15, 19, 20));
		
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setForeground(Color.WHITE);
		btnConfimar.setBounds(95,320, 316, 40);
		add(btnConfimar);
		
		
		/*JTextField txtNombre = new JTextField();
		txtNombre.setBounds(172, 99, 96, 18);
		add(txtNombre);
		txtNombre.setColumns(10);
		
		JTextField txtEmail = new JTextField();
		txtEmail.setBounds(172, 146, 96, 18);
		add(txtEmail);
		txtEmail.setColumns(10);*/
		
		//USUARIO
		JLabel labelUsuario = new JLabel("USUARIO");
		labelUsuario.setForeground(Color.WHITE);
		labelUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		labelUsuario.setBounds(95, 190, 120, 25);
		add(labelUsuario);

		JTextField txtUsuario = new JTextField();
		txtUsuario.setBounds(95, 215, 316, 25);
		add(txtUsuario);

		// CONTRASENA
		JLabel labelContrasena = new JLabel("CONTRASENA");
		labelContrasena.setForeground(Color.WHITE);
		labelContrasena.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		labelContrasena.setBounds(95, 245, 140, 25);
		add(labelContrasena);

		JPasswordField txtContrasena = new JPasswordField();
		txtContrasena.setBounds(95, 270, 316, 25);
		add(txtContrasena);
		
		
		
		JLabel lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		lblIniciarSesion.setForeground(new Color(255, 255, 255));
		lblIniciarSesion.setBounds(117, 40, 254, 62);
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblIniciarSesion);
	}
}
