package controllers;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import views.InvalidPasswordException;
import views.InvalidUserException;
import models.User;
import views.InicioView;
import views.LoginView;
import views.RegistroView;

public class LoginController {

	private LoginView view;

	public LoginController(LoginView view) {
		this.view = view;
		registerListeners();
	}

	private void registerListeners() {
		view.getBtnLogin().addActionListener(e -> manejarLogin());

		view.getLblRegister().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new RegistroView();
				view.dispose();
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
		User user = new User(view.getEmail(), view.getPassword());
		
		try {
			if (validateCredentials(user)) {
				JOptionPane.showMessageDialog(view, "Se inicio la sesion", "Sesion iniciada", JOptionPane.INFORMATION_MESSAGE);
				new InicioView(); 
				view.dispose();
			}
		} catch (InvalidUserException | InvalidPasswordException ex) {
			view.ErrorGeneral("Credenciales Incorrectas");
		}
	}
	
	private boolean validateCredentials(User user) throws InvalidUserException, InvalidPasswordException {
		boolean valid = true;

		if(user.getEmail().isEmpty()) {
			view.showEmailError("Usuario Requerido");
			valid = false;
		}
		
		if(user.getPassword().isEmpty()) {
			view.PasswordError("Contraseña Requerida");
			valid = false;
		}
		
		if (!valid) return false;

		if(!user.getEmail().equals("pepe@gmail.com")) {
			throw new InvalidUserException("El correo no coincide");
		}
		
		if(!user.getPassword().equals("12345")) {
			throw new InvalidPasswordException("La contraseña no coincide");
		}
		
		return true;
	}
}