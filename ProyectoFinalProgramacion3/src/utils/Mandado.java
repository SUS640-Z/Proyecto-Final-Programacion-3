package utils;

import java.util.ArrayList;
import java.util.List;

import models.Product;
import models.ProductMandado;



public class Mandado {
	private static final List<ProductMandado> compras = new ArrayList<>();
	
    public static void añadirProducto(Product producto, int cantidad) {
        for (ProductMandado c : compras) {
            if (c.getProducto().getId() == producto.getId()) {
                c.setCantidad(c.getCantidad() + cantidad);
                return;
            }
        }
        
        compras.add(new ProductMandado(producto, cantidad));
    }
    
    public static void removerProducto(int productId) {
    	compras.removeIf(item -> item.getProducto().getId() == productId);
    }

    public static List<ProductMandado> getItems() { return compras; }
    public static void limpiarCarrito() { compras.clear(); }
    
    public static double calcularTotal() {
        double total = 0;
        for (ProductMandado c : compras) {
            total += c.getProducto().getPrice() * c.getCantidad();
        }
        return total;
    }
}
