package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;
import repository.UserRepository;
import tableModels.UserTableModel;
import views.DataView;
import views.LoginView;
import views.RegistroView;
import views.UserFormDialog;

public class DataController {
	private DataView view;
	
	public DataController(DataView view) {
		this.view = view;
		registerListeners();
	}

	public void registerListeners() {
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				handleClose();
			}
		});

		view.btnUsers.addActionListener(e -> showUsers());
		view.btnHome.addActionListener(e -> view.showView(DataView.HOME));
		view.btnSalir.addActionListener(e -> handleClose());
		view.usersPanel.getBtnAdd().addActionListener(e -> {
			UserFormDialog form = new UserFormDialog(null, null);
			form.setVisible(true);
		});

		view.usersPanel.getBtnEdit().addActionListener(e -> {
			UserFormDialog form = new UserFormDialog(null, null);
			form.setVisible(true);
		});
		
		view.usersPanel.getBtnDelete().addActionListener(e -> {
		});
	}
	
	private void showUsers() {
		UserRepository repository = new UserRepository();
		try {
			List<User> users = repository.getUsers(); 
			UserTableModel model = new UserTableModel(users); 
			
			view.usersPanel.setTableModel(model); 
			view.showView(DataView.USERS); 
			
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(view, "Error al cargar los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void handleClose() {
		int option = view.confirmExit();
		if (option == JOptionPane.YES_OPTION) {
			view.dispose(); 
			LoginView ventanaLogin = new LoginView(); 
            new controllers.LoginController(ventanaLogin);
		}
	}
}