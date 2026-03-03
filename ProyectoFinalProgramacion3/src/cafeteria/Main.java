package cafeteria;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import ventanas.VentanaDirrecion;
import ventanas.VentanaLogIn;
import views.Dirreccion;
//import views.VentanaBienvenida;

public class Main {
	public static void main(String[] args) {

	FlatLightLaf.setup();
	VentanaLogIn ventana = new VentanaLogIn();
	//VentanaBienvenida bienvenida = new VentanaBienvenida();
	VentanaDirrecion direcion = new VentanaDirrecion();
	}
}





