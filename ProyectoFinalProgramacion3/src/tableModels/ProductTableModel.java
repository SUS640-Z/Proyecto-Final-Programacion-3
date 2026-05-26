package tableModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.Product;


public class ProductTableModel  extends AbstractTableModel{
	private List<Product> Products;
	private final String[] columns = {"Nombre","Precio","Tipo","Temporada","Activo"};
	
	public ProductTableModel(List<Product> Products) {
		this.Products = Products;
	}
	
	@Override
	public int getRowCount() { return Products.size(); }

	@Override
	public int getColumnCount() { return columns.length; }
	
	@Override
	public String getColumnName(int column) { return columns[column]; }
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Product product = Products.get(rowIndex);
		String estadoText = product.isIs_active() ? "Activo" : "Inactivo";
		
		String temporadaText = product.getSeason();
	    if (temporadaText == null) {
	    	temporadaText = "No especificada";
	    }
	    
	    switch (temporadaText) {
	        case "spring": temporadaText = "Primavera";
	        case "summer": temporadaText =  "Verano";
	        case "autumn": temporadaText = "Otoño";
	        case "winter": temporadaText = "Invierno";
	    };
	    
		switch(columnIndex) {
			case 0: return product.getName();
			case 1: return product.getPrice();
			case 2: return product.getProduct_type();
			case 3: return temporadaText;
			case 4: return estadoText;
		}
		return null;
	}
	
	public Product getProductTypeAt(int row) { return Products.get(row); }
	
	public void setProductsType(List<Product> products) {
		this.Products = products;
		fireTableDataChanged();
	}


	public void removeRow(int row) {
		Products.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(Product product) {
		int row = Products.size();
		Products.add(product);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, Product product) {
		Products.set(row, product);
		fireTableRowsUpdated(row, row);
	}
}