package controllers;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import exceptions.InvalidPasswordException;
import exceptions.InvalidUserException;
import models.User;

import repository.LoginRepository;
import repository.UserRepository;
import views.DataView;
import views.LoginView;
import views.RegistroView;

public class LoginController {
	private LoginRepository repository;
	private UserRepository repo;
	private LoginView view;

	public LoginController(LoginView view) {
		repository = new LoginRepository();
		repo = new UserRepository();
		this.view = view;
		registerListeners();
	}

	private void registerListeners() {
		view.getBtnLogin().addActionListener(e -> manejarLogin());

		view.getLblRegister().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				RegistroView vistaRegistro = new RegistroView();
				new controllers.RegistroController(vistaRegistro); 
				view.getWindow().dispose();
			}
		});

		DocumentListener limpiarErrores = new DocumentListener() {
			public void removeUpdate(DocumentEvent e) { view.reiniciarErrorMessages(); }
			public void insertUpdate(DocumentEvent e) { view.reiniciarErrorMessages(); }
			public void changedUpdate(DocumentEvent e) { view.reiniciarErrorMessages(); }
		};
		
		view.getTxtUsuario().getDocument().addDocumentListener(limpiarErrores);
		view.getTxtContrasena().getDocument().addDocumentListener(limpiarErrores);
	}

	private void manejarLogin() {
		view.reiniciarErrorMessages();
		if(!validateCredentials(new User(view.getEmail(), view.getPassword()))){
			return;
		}
		
		User user = repository.login(view.getEmail(), view.getPassword());
		
		if(user == null) {
			view.PasswordError("Credenciales incorrectas");
			return;
		}
		
		JOptionPane.showMessageDialog(view.getWindow(),  "Se inició la sesión", "Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
		new DataController(new DataView());
		view.getWindow().dispose();
	}
	
	private boolean validateCredentials(User user) {
		boolean valid = true;
		

		if(user.getEmail().isEmpty()) {
			view.showEmailError("Usuario Requerido");
			valid = false;
		}
		
		if(user.getPassword().isEmpty()) {
			view.PasswordError("Contraseña Requerida");
			valid = false;
		}
		
		
		return valid;
	}
}