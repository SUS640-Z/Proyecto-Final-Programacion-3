package views;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import components.BordePanel;

public class TablaView  extends JFrame {
	public TablaView() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("SaturnBucks");
		setBounds(200,100,600,640); 
		setResizable(false);
		setLocationRelativeTo(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
		setIconImage(icono);
		
		BordePanel login = new BordePanel("LOG IN",Color.white);
		login.setBackground(new Color(15, 19, 9));
		add(login);
		
		setVisible(true);
		eventoCerradoVentana();

	}
	
	private void eventoCerradoVentana() {
 		addWindowListener(new WindowAdapter() {
 			public void windowClosing(WindowEvent e) {
 				int opcion = JOptionPane.showConfirmDialog(
 		                 null,
 		                 "¿Seguro que deseas cerrar la aplicación?",
 		                 "Confirmar salida",
 		                 JOptionPane.YES_NO_OPTION
 		         );

 		         if(opcion == JOptionPane.YES_OPTION){
 		             dispose(); 
 		         }
 	        }
 		});
 	}
}
