package tableModels;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import models.OrderDetails;


public class OrderDetailsTableModel extends AbstractTableModel{
	private List<OrderDetails> ordersDetails;
	private final String[] columns = {"N. Orden", "Persona","Producto", "Cantidad","Subtotal"};
	
	public OrderDetailsTableModel(List<OrderDetails> ordersDetails) {
		this.ordersDetails = ordersDetails;
	}
	
	@Override
	public int getRowCount() { return ordersDetails.size(); }

	@Override
	public int getColumnCount() { return columns.length; }
	
	@Override
	public String getColumnName(int column) { return columns[column]; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		OrderDetails orderDetails = ordersDetails.get(rowIndex);
		switch(columnIndex) {
		case 0: return orderDetails.getOrder_id();
		case 1: return orderDetails.getClient_name();
		case 2: return orderDetails.getProduct_name();
		case 3: return orderDetails.getQuantity();
		case 4: return orderDetails.getPrice();
		}
		return null;
	}
	
	public OrderDetails getOrderDetailsAt(int row) { return ordersDetails.get(row); }
	
	public void setOrdersDetails(List<OrderDetails> ordersDetails) {
		this.ordersDetails = ordersDetails;
		fireTableDataChanged();
	}
	

	public void removeRow(int row) {
		ordersDetails.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(OrderDetails orderDetails) {
		int row = ordersDetails.size();
		ordersDetails.add(orderDetails);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, OrderDetails orderDetails) {
		ordersDetails.set(row, orderDetails);
		fireTableRowsUpdated(row, row);
	}
}
