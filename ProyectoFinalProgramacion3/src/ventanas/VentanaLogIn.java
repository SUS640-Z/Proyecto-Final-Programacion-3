package ventanas;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

import views.Dirreccion;
import views.LoginView;

public class VentanaLogIn extends JFrame{
	public VentanaLogIn() {
		//setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("SaturnBucks");
		//setLocation(100,100);
		setBounds(200,100,640,640); // cordenadas y tamaño
		setResizable(false);
		setLocationRelativeTo(null);// Colocar la ventana el centro
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/img/SATURN_BUCKS_3.png");
		setIconImage(icono);
		//Panel panelito = new Panel();
		//add(panelito);
		LoginView login = new LoginView();
		add(login);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	
}