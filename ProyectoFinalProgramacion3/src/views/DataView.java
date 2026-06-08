package views;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import utils.ThemeManager;

public class DataView extends JFrame {
	public static final String HOME = "HOME";
	public static final String USERS = "USERS";
	public static final String ROLES = "ROLES";
	public static final String ADDRESSES = "ADDRESSES";
	public static final String ORDERS = "ORDERS"; 
	public static final String ORDERDETAILS = "ORDERDETAILS";
	public static final String PRODUCTS = "PRODUCTS";
	public static final String PRODUCTSTYPE = "PRODUCTSTYPE";
	public static final String DASHBOARD = "DASHBOARD"; 
	
	public JButton btnHome;
	public JButton btnDashboard; 
	public JButton btnUsers;
	public JButton btnRoles;
	public JButton btnAddresses;
	public JButton btnOrders; 
	public JButton btnProducts;	
	public JButton btnProductsType;
	public JButton btnOrdersDetails;
	public JButton btnSalir; 
	public JButton btnMode;
	
	public DashboardView dashboardPanel; 
	public UserView usersPanel;
	public RolView rolesPanel;
	public AddressView addressPanel;
	public OrderView ordersPanel; 

	public OrderDetailsView ordersDetailsPanel;
	public ProductView productsPanel;
	public ProductTypeView productsTypePanel;
	
	private CardLayout cardLayout;
	private JPanel container;
	
	public DataView() {
		setTitle("Saturnbucks - Panel de Administración");
		setSize(1250, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setLocationRelativeTo(null);
        setResizable(true);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}
        
		createNavbar();
		createViews();
		setVisible(true);
	}
	
	public void createNavbar() {
		JPanel navbar = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
		navbar.setBackground(new Color(15, 19, 9));
		
		btnHome = crearBoton("Inicio");		
		btnUsers = crearBoton("Usuarios");
		btnRoles = crearBoton("Roles");
		btnAddresses = crearBoton("Direcciones");
		btnOrders = crearBoton("Pedidos"); 
		btnOrdersDetails = crearBoton("Detalles Pedidos");
		btnProducts = crearBoton("Productos");
		btnProductsType = crearBoton("Tipos Prod.");
		btnDashboard = crearBoton("Estadísticas"); 
		btnSalir = crearBoton("Cerrar Sesión");
		btnMode = crearBoton("Modo");
		
		btnMode.addActionListener(e -> ThemeManager.toggle());
		
		navbar.add(btnHome);
		navbar.add(btnUsers);
		navbar.add(btnRoles);
		navbar.add(btnAddresses);
		navbar.add(btnOrders); 
		navbar.add(btnOrdersDetails);
		navbar.add(btnProducts);
		navbar.add(btnProductsType);
		navbar.add(btnDashboard); 
		navbar.add(btnSalir);
		navbar.add(btnMode);
		
		add(navbar, BorderLayout.NORTH);
	}
	
	private JButton crearBoton(String texto) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Arial", Font.BOLD, 13));
		btn.setBackground(new Color(48, 60, 26));
		btn.setForeground(Color.WHITE);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setFocusPainted(false);
		return btn;
	}
	
	private void createViews() {
		cardLayout = new CardLayout();
		container = new JPanel(cardLayout);
		
		JPanel homePanel = new JPanel(new BorderLayout());
		homePanel.setBackground(new Color(15, 19, 9));
		JLabel lblBienvenida = new JLabel("Panel de Control Saturnbucks", SwingConstants.CENTER);
		lblBienvenida.setForeground(new Color(210, 180, 140));
		lblBienvenida.setFont(new Font("Times New Roman", Font.BOLD, 26));
		homePanel.add(lblBienvenida, BorderLayout.CENTER);
 
		usersPanel = new UserView(); 
		rolesPanel = new RolView(); 
		addressPanel = new AddressView(); 
		ordersPanel = new OrderView(); 
		ordersDetailsPanel = new OrderDetailsView();
		productsPanel = new ProductView();
		productsTypePanel = new ProductTypeView();
		dashboardPanel = new DashboardView();
		
		container.add(homePanel, HOME);
		container.add(usersPanel, USERS);
		container.add(rolesPanel, ROLES); 
		container.add(addressPanel, ADDRESSES); 
		container.add(ordersPanel, ORDERS); 
		container.add(productsPanel, PRODUCTS);
		container.add(productsTypePanel, PRODUCTSTYPE);
		container.add(ordersDetailsPanel, ORDERDETAILS);
		container.add(dashboardPanel, DASHBOARD); 
		
		add(container, BorderLayout.CENTER);
	}
	
	public void showView(String view) { cardLayout.show(container, view); }
	public int confirmExit() { return JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cerrar la sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION); }
	public void setWindowSize(int width, int height) { setSize(width, height); }
	public void setWindowLocation(int x, int y) { setLocation(x, y); }
}