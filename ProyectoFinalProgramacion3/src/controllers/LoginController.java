package controllers;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.FlatLaf;

import models.User;
import repository.LoginRepository;
import utils.Session;
import views.DataView;
import views.InicioView;
import views.LoginView;
import views.RegistroView;

public class LoginController {
	
	private LoginView view;
	private LoginRepository repo;

	public LoginController(LoginView view) {
		this.view = view;
		this.repo = new LoginRepository();
		registerListeners();
	}

	private void registerListeners() {

		this.view.getBtnLogin().addActionListener(e -> manejarLogin());

		this.view.getLblRegister().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RegistroView registroView = new RegistroView();
				new RegistroController(registroView);
				cerrarVentana();
			}
		});
		
		this.view.getBtnRegresar().addActionListener(e -> regresar());
	}

	private void manejarLogin() {

		String email = view.getTxtUsuario().getText().trim();
		String password = new String(view.getTxtContrasena().getPassword()).trim();

		view.reiniciarErrorMessages();

		if (email.isEmpty() || password.isEmpty()) {
			view.ErrorGeneral("Por favor, ingresa tu correo y contraseña.");
			return;
		}

		
		try {
			User user = repo.login(email, password);

			if (user != null) {

				JOptionPane.showMessageDialog(view, "¡Bienvenido " + user.getName() + "!\nHas iniciado sesión como: " + user.getRol(), "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
				
				Session.login(user);
				
				if(!Session.getRol().equals("Cliente")) {
					DataView dataView = new DataView();
				    new DataController(dataView, user); 			
				}else {
					InicioView inicioView = new InicioView();
				}
				
			    
			    cerrarVentana();
				
			} else {

				view.ErrorGeneral("Correo o contraseña incorrectos.");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al conectar con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void cerrarVentana() {

		if (view.getWindow() != null) {
			view.getWindow().dispose();
		} else {
			Window window = SwingUtilities.getWindowAncestor(view);
			if (window != null) {
				window.dispose();
			}
		}
	}
	
	private void regresar() {
		InicioView inicioView = new InicioView();
		cerrarVentana();
	}
}