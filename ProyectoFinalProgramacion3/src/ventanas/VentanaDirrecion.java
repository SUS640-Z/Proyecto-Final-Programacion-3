package ventanas;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import views.Dirreccion;

public class VentanaDirrecion extends JFrame{
	public VentanaDirrecion() {
		setTitle("Saturnbucks.direccion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 700);
		setResizable(false);
		setLocationRelativeTo(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/img/SATURN_BUCKS_51.png");
		setIconImage(icono);
		Dirreccion dirreccion = new Dirreccion();
		add(dirreccion);
		setVisible(true);
	}
}
