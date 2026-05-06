package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;
import repository.UserRepository;
import tableModels.UserTableModel;
import utils.Config;
import views.DataView;
import views.LoginView;
import views.RegistroView;
import views.UserFormDialog;

public class DataController {
	private DataView view;
	private UserController userController;
	
	public DataController(DataView view) {
		this.view = view;
		
		loadWindowPreferences();
		registerListeners();
	}

	public void registerListeners() {
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveWindowPreferences();
				handleClose();
			}
		});


		view.btnUsers.addActionListener(e -> {
			showUsers();
		});
		
		view.btnHome.addActionListener(e -> {
			view.showView(DataView.HOME);
			updateMenuState(DataView.HOME);
		});
		

		view.btnSalir.addActionListener(e -> handleClose());
	
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
		
		if(userController == null) {
			userController = new UserController(view.usersPanel);
		}
			
		userController.loadUsers();
	
	}
	
	private void handleClose() {
		int option = view.confirmExit();
		if (option == JOptionPane.YES_OPTION) {
			view.dispose(); 
			LoginView ventanaLogin = new LoginView(); 
            new controllers.LoginController(ventanaLogin);
		}
	}
	
	private void updateMenuState(String viewName) {
		view.btnUsers.setEnabled(!viewName.equals(DataView.USERS));
		view.btnHome.setEnabled(!viewName.equals(DataView.HOME));
	}
	
	private void saveWindowPreferences() {
		Dimension size = view.getSize();
		Point point = view.getLocation();
		
		Config.set("registration.window.width", 
				String.valueOf(size.width));
		
		Config.set("registration.window.height", 
				String.valueOf(size.height));
		
		Config.set("registration.window.x", 
				String.valueOf(point.x));
		
		Config.set("registration.window.y", 
				String.valueOf(point.y));
		
	}
	
	private void loadWindowPreferences()
	{
		int width = Integer.parseInt(
				Config.get("registration.window.width"
						, "500"));
		
		int height = Integer.parseInt(
				Config.get("registration.window.height"
						, "500"));
		
		String xValue = Config.get("registration.window.x"
						, "");
		
		String yValue = Config.get("registration.window.y"
				, "");
		
		if(!xValue.isBlank() && !yValue.isBlank()) {
			view.setWindowLocation(Integer.parseInt(xValue), Integer.parseInt(yValue));
		}else {
			view.setLocationRelativeTo(null);
		}
		
		view.setWindowSize(width, height);
	}

}