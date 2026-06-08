package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;

import models.Product;
import repository.ProductRepository;
import utils.Session;
import views.CarritoFormDialog;
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
             public void mouseClicked(MouseEvent e) {
            	 view.setVisible(false);
        
                 CarritoFormDialog carrito = new CarritoFormDialog(view);
                 
              
                 CarritoController control = new CarritoController(carrito); 
                 
    
                 carrito.setVisible(true);
                 
               
                 view.setVisible(true);
             }
         });
		
	}
}