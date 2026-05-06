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
import repository.UserRepository;
import views.DataView;
import views.LoginView;
import views.RegistroView;

public class LoginController {
	private UserRepository repo;
	private LoginView view;

	public LoginController(LoginView view) {
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
				DataView ventanaDatos = new DataView(); 
                new controllers.DataController(ventanaDatos); 
                view.dispose(); 
			}
		} catch (InvalidUserException | InvalidPasswordException ex) {
			view.ErrorGeneral("Credenciales Incorrectas");
		}
	}
	
	private boolean validateCredentials(User user) throws InvalidUserException, InvalidPasswordException {
		boolean valid = true;
		boolean validCorreo=false;
		boolean validPassword=false;
		

		if(user.getEmail().isEmpty()) {
			view.showEmailError("Usuario Requerido");
			valid = false;
		}
		
		if(user.getPassword().isEmpty()) {
			view.PasswordError("Contraseña Requerida");
			valid = false;
		}
		
		if (!valid) return false;
		
		try {
		List<User> users = repo.getUsers();
		
		for(int i=0;i<users.size() ;i++) {
			if(user.getEmail().equals(users.get(i).getEmail())) {
				validCorreo=true;
				if(user.getPassword().equals(users.get(i).getPassword())) {
					validPassword=true;
					break;
				}
			}
		}
		
		}catch(IOException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
		if(!validCorreo) {
			throw new InvalidPasswordException("El correo no coincide");
		}
		
		if(!validPassword) {
			throw new InvalidPasswordException("La contraseña no coincide");
		}
		return true;
	}
}