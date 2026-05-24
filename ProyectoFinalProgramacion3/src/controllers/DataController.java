package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;

import models.Product;
import models.User;
import repository.ProductRepository;
import repository.UserRepository;
import tableModels.ProductTableModel;
import models.ProductType;

import repository.ProductTypeRepository;
import tableModels.ProductTypeTableModel;
import tableModels.UserTableModel;
import config.Config;
import views.DataView;
import views.LoginWindow;

public class DataController {
	private DataView view;
	private UserController userController;
	private ProductController productController;
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

		view.btnUsers.addActionListener(e -> showUsers());
		view.btnProducts.addActionListener(e -> showProduct());
		view.btnProductsType.addActionListener(e -> showProductType());
		
		view.btnHome.addActionListener(e -> {
			view.showView(DataView.HOME);
			updateMenuState(DataView.HOME);
		});
		
		view.btnSalir.addActionListener(e -> handleClose());
	}
	
	private void showUsers() {
		// 1. Aseguramos que el controlador hijo exista
		if (userController == null) {
			userController = new UserController(view.usersPanel);
		}
		
		// 2. Dejamos que el controlador se encargue de consultar al repositorio y llenar la tabla
		userController.loadUsers();
		
		// 3. Cambiamos la vista y el estado del menú
		view.showView(DataView.USERS); 
		updateMenuState(DataView.USERS);
	}
	

	private void showProduct() {
		// 1. Aseguramos que el controlador hijo exista
		if (productController == null) {
			productController = new ProductController(view.productsPanel);
		}
		
		// 2. El sub-controlador lee la BD y actualiza el panel de productos
		productController.loadProductsType();		
		
		// 3. CORRECCIÓN: Mostramos la constante correcta de productos (asumiendo que se llama PRODUCTS)
		// Si en tu DataView la constante se llama diferente, ajusta este String.
		view.showView(DataView.PRODUCTS); 
		updateMenuState(DataView.PRODUCTS);
	}

	private void showProductType() {
		// 1. Aseguramos que el controlador hijo exista
		if (productTypeController == null) {
			productTypeController = new ProductTypeController(view.productsTypePanel);
		}
			
		// 2. El sub-controlador lee la BD y actualiza el panel de tipos de productos
		productTypeController.loadProductsType();

		// 3. Mostramos la vista de tipos de productos
		view.showView(DataView.PRODUCTSTYPE); 
		updateMenuState(DataView.PRODUCTSTYPE);
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
		// Tip extra: Es buena idea deshabilitar también los botones de productos cuando estés en ellos
		if(view.btnProducts != null) view.btnProducts.setEnabled(!viewName.equals(DataView.PRODUCTS));
		if(view.btnProductsType != null) view.btnProductsType.setEnabled(!viewName.equals(DataView.PRODUCTSTYPE));
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
		} else {
			view.setLocationRelativeTo(null);
		}
		
		view.setWindowSize(width, height);
	}
}