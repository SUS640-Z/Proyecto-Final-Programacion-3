package tableModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.ProductType;


public class ProductTypeTableModel  extends AbstractTableModel{
	private List<ProductType> ProductsType;
	private final String[] columns = {"Nombre"};
	
	public ProductTypeTableModel(List<ProductType> ProductsType) {
		this.ProductsType = ProductsType;
	}
	
	@Override
	public int getRowCount() { return ProductsType.size(); }

	@Override
	public int getColumnCount() { return columns.length; }
	
	@Override
	public String getColumnName(int column) { return columns[column]; }
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProductType productType = ProductsType.get(rowIndex);
		switch(columnIndex) {
			case 0: return productType.getName();
		}
		return null;
	}
	
	public ProductType getProductTypeAt(int row) { return ProductsType.get(row); }
	
	public void setProductsType(List<ProductType> productsType) {
		this.ProductsType = productsType;
		fireTableDataChanged();
	}


	public void removeRow(int row) {
		ProductsType.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(ProductType productType) {
		int row = ProductsType.size();
		ProductsType.add(productType);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, ProductType productType) {
		ProductsType.set(row, productType);
		fireTableRowsUpdated(row, row);
	}
}
