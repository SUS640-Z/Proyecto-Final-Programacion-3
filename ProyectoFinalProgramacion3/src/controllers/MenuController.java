package controllers;

import java.util.List;
import javax.swing.JOptionPane;

import models.Product;
import repository.ProductRepository;
import views.MenuView;


public class MenuController {
    private MenuView view;
    private ProductRepository repo;
    
    public MenuController(MenuView view) {
        this.view = view;
        this.repo = new ProductRepository();
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
}