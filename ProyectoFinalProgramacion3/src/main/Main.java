package main;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

import views.DataView;
import views.InicioView;

public class Main {
	public static void main(String[] args) {
		
		FlatLightLaf.setup();
		//new RegistroView();
		//new Dirreccion();
		new InicioView ();
		//new DataView();
		//LoginView ventana = new LoginView();
		//new LoginController(ventana);

	}
}