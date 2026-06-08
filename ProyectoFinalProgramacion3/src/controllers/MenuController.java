package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JOptionPane;

import models.Product;
import repository.ProductRepository;
import utils.Session;
import views.CarritoFormDialog;
import views.CarritoView;
import views.InicioView;
import views.MenuView;


public class MenuController {
    private MenuView view;
    private ProductRepository repo;
    
    public MenuController(MenuView view) {
        this.view = view;
        this.repo = new ProductRepository();
        registerListeners();
        loadProducts();
    }
    
    public void loadProducts() {    
        try {
            
            List<Product> products = repo.getProducts();
            view.actualizarMenuContenido(products);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar desde base de datos: " + ex.getMessage());
            ex.printStackTrace(); 
        }
    }
    
    private void registerListeners() {
    	 this.view.getLblInicio().addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent e) {
             	view.dispose();
             	InicioView inicioView = new InicioView();
             }
         });
    	 
    	 
    	 this.view.getLblCarrito().addMouseListener(new MouseAdapter() {
    		    @Override
    		    public void mouseClicked(MouseEvent e) {
    		        // 1. Ocultamos el menú principal
    		        view.setVisible(false);
    		   
    		        // 2. Abrimos el carrito
    		        CarritoView carrito = new CarritoView();
    		        carrito.setLocationRelativeTo(view);
    		        
    		        // 3. Pasamos la 'view' (menú principal) al controlador del carrito
    		        CarritoController control = new CarritoController(carrito, view); 
    		        
    		        carrito.setVisible(true);
    		    }
    		});
		
	}
}