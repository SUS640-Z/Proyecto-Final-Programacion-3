package cafeteria;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

import controllers.LoginController;
import controllers.RegistroController;
import views.LoginView;
import views.RegistroView;
import views.Dirreccion;
import views.InicioView;

public class Main {
	public static void main(String[] args) {
		
		FlatLightLaf.setup();

		//new RegistroView();
		//new Dirreccion();
		new InicioView ();
		//LoginView ventana = new LoginView();
		//new LoginController(ventana);



	}
}





