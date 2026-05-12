package views;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class LoginWindow extends JFrame { 
	
	private LoginView loginView;
	
	public LoginWindow() {
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("SaturnBucks");
		setBounds(200,100,600,640); 
		setResizable(false);
		setLocationRelativeTo(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_3.png");
		setIconImage(icono);
		setBackground(new Color(15, 19, 9));
		
		loginView = new LoginView(this);
		add(loginView);
		
		setVisible(true);
	}
	
	public LoginView getLoginView() {
		return loginView;
	}
	
}