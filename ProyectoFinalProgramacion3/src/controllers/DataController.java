package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;

import models.ProductType;
import models.User;
import repository.ProductTypeRepository;
import repository.UserRepository;
import tableModels.ProductTypeTableModel;
import tableModels.UserTableModel;
import config.Config;
import views.DataView;

import views.LoginWindow;


public class DataController {
	private DataView view;
	private UserController userController;
	private ProductTypeController productTypeController;
	
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
		
		view.btnProductsType.addActionListener(e -> {
			showProductType();
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
			
		} catch (Exception ex) { 
			JOptionPane.showMessageDialog(view, "Error al cargar los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		if(userController == null) {
			userController = new UserController(view.usersPanel);
		}
			
		userController.loadUsers();
	}
	
	private void showProductType() {
		ProductTypeRepository repository = new ProductTypeRepository();
		try {
			List<ProductType> productsType = repository.getProductsType(); 
			ProductTypeTableModel model = new ProductTypeTableModel(productsType); 
			
			view.productsTypePanel.setTableModel(model); 
			view.showView(DataView.PRODUCTSTYPE); 
			
		} catch (Exception ex) { 
			JOptionPane.showMessageDialog(view, "Error al cargar los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		if(productTypeController == null) {
			productTypeController = new ProductTypeController(view.productsTypePanel);
		}
			
		productTypeController.loadProductsType();
	}
	
	private void handleClose() {
		int option = view.confirmExit();
		if (option == JOptionPane.YES_OPTION) {
			new LoginController(new LoginWindow().getLoginView());
			view.dispose(); 
		}
	}
	
	private void updateMenuState(String viewName) {
		view.btnUsers.setEnabled(!viewName.equals(DataView.USERS));
		view.btnHome.setEnabled(!viewName.equals(DataView.HOME));
	}
	
	private void saveWindowPreferences() {
		Dimension size = view.getSize();
		Point point = view.getLocation();
		
		Config.set("registration.window.width", String.valueOf(size.width));
		Config.set("registration.window.height", String.valueOf(size.height));
		Config.set("registration.window.x", String.valueOf(point.x));
		Config.set("registration.window.y", String.valueOf(point.y));
	}
	
	private void loadWindowPreferences() {
		int width = Integer.parseInt(Config.get("registration.window.width", "500"));
		int height = Integer.parseInt(Config.get("registration.window.height", "500"));
		String xValue = Config.get("registration.window.x", "");
		String yValue = Config.get("registration.window.y", "");
		
		if(!xValue.isBlank() && !yValue.isBlank()) {
			view.setWindowLocation(Integer.parseInt(xValue), Integer.parseInt(yValue));
		}else {
			view.setLocationRelativeTo(null);
		}
		
		view.setWindowSize(width, height);
	}
}