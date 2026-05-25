package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;


import models.OrderDetails;
import models.User;
import repository.OrderDetailsRepository;
import repository.UserRepository;
import tableModels.OrderDetailsTableModel;
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
	private OrderDetailsController orderDetailsController;
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
		view.btnOrdersDetails.addActionListener(e -> {showOrderDetails();});
		
		view.btnHome.addActionListener(e -> {
			view.showView(DataView.HOME);
			updateMenuState(DataView.HOME);
		});
		
		view.btnSalir.addActionListener(e -> handleClose());
	}
	
	private void showUsers() {
		if (userController == null) {
			userController = new UserController(view.usersPanel);
		}

		userController.loadUsers();

		view.showView(DataView.USERS); 
		updateMenuState(DataView.USERS);
	}
	

	private void showProduct() {
		
		if (productController == null) {
			productController = new ProductController(view.productsPanel);
		}
		
		
		productController.loadProductsType();		
		
		view.showView(DataView.PRODUCTS); 
		updateMenuState(DataView.PRODUCTS);
	}

	private void showProductType() {

		if (productTypeController == null) {
			productTypeController = new ProductTypeController(view.productsTypePanel);
		}

		productTypeController.loadProductsType();

		view.showView(DataView.PRODUCTSTYPE); 
		updateMenuState(DataView.PRODUCTSTYPE);
	}
	
	private void showOrderDetails() {

		if (orderDetailsController == null) {
			orderDetailsController = new OrderDetailsController(view.ordersDetailsPanel);
		}

		orderDetailsController.loadOrdersDetails();

		view.showView(DataView.ORDERDETAILS); 
		updateMenuState(DataView.ORDERDETAILS);
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
		if(view.btnProducts != null) view.btnProducts.setEnabled(!viewName.equals(DataView.PRODUCTS));
		if(view.btnProductsType != null) view.btnProductsType.setEnabled(!viewName.equals(DataView.PRODUCTSTYPE));
		if(view.btnOrdersDetails != null) view.btnOrdersDetails.setEnabled(!viewName.equals(DataView.ORDERDETAILS));

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