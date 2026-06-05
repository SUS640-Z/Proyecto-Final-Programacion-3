package models;


public class ProductMandado {
	private final Product producto;
    private int cantidad;

    public ProductMandado(Product producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    public Product getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
