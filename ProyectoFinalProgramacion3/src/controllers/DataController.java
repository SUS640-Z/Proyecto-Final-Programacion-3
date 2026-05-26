package controllers;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;
import repository.UserRepository;
import tableModels.UserTableModel;
import config.Config;
import views.DataView;
import views.LoginWindow;

public class DataController {
	private DataView view;
	private UserController userController;
	private RolController rolController;
	private AddressController addressController;

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
		view.btnRoles.addActionListener(e -> showRoles());
		view.btnAddresses.addActionListener(e -> showAddresses());

		view.btnProducts.addActionListener(e -> showProduct());
		view.btnProductsType.addActionListener(e -> showProductType());
		view.btnOrdersDetails.addActionListener(e -> showOrderDetails());
		
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
			updateMenuState(DataView.USERS);
		} catch (Exception ex) { 
			JOptionPane.showMessageDialog(view, "Error al cargar los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		if(userController == null) {
			userController = new UserController(view.usersPanel);
		}
		userController.loadUsers();
	}
	
	private void showRoles() {
		view.showView(DataView.ROLES);
		updateMenuState(DataView.ROLES);
		if(rolController == null) {
			rolController = new RolController(view.rolesPanel);
		}
		rolController.loadRoles();
	}

	private void showAddresses() {
		view.showView(DataView.ADDRESSES);
		updateMenuState(DataView.ADDRESSES);
		if(addressController == null) {
			addressController = new AddressController(view.addressPanel);
		}
		addressController.loadAddresses();
	}

	private void showProduct() {
		view.showView(DataView.PRODUCTS);
		updateMenuState(DataView.PRODUCTS);
		if(productController == null) {
			// productController = new ProductController(view.productsPanel);
		}
	}

	private void showProductType() {
		view.showView(DataView.PRODUCTSTYPE);
		updateMenuState(DataView.PRODUCTSTYPE);
		if(productTypeController == null) {
			// typeController = new ProductTypeController(view.productsTypePanel);
		}
	}

	private void showOrderDetails() {
		view.showView(DataView.ORDERDETAILS);
		updateMenuState(DataView.ORDERDETAILS);
		if(orderDetailsController == null) {
			// orderDetailsController = new OrderDetailsController(view.ordersDetailsPanel);
		}
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
		view.btnRoles.setEnabled(!viewName.equals(DataView.ROLES));
		view.btnAddresses.setEnabled(!viewName.equals(DataView.ADDRESSES));
		view.btnProducts.setEnabled(!viewName.equals(DataView.PRODUCTS));
		view.btnProductsType.setEnabled(!viewName.equals(DataView.PRODUCTSTYPE));
		view.btnOrdersDetails.setEnabled(!viewName.equals(DataView.ORDERDETAILS));
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
		int width = Integer.parseInt(Config.get("registration.window.width", "1000"));
		int height = Integer.parseInt(Config.get("registration.window.height", "600"));
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