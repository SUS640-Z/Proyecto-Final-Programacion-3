package tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Order;

public class OrderTableModel extends AbstractTableModel {

	private List<Order> orders;
	private final String[] columns = {"ID Pedido", "Cliente", "Fecha", "Total", "Estado"};

	public OrderTableModel(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int getRowCount() { return orders.size(); }

	@Override
	public int getColumnCount() { return columns.length; }

	@Override
	public String getColumnName(int column) { return columns[column]; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Order order = orders.get(rowIndex);
		switch (columnIndex) {
			case 0: return order.getId();
			case 1: return order.getUserName();
			case 2: return order.getOrderDate();
			case 3: return String.format("$%.2f", order.getTotal()); 
			case 4: return traducirEstado(order.getStatus()); // Se traduce aquí
		}
		return null;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
		fireTableDataChanged();
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	private String traducirEstado(String estadoIngles) {
	    if (estadoIngles == null) return "Desconocido";
	    switch (estadoIngles.toLowerCase()) {
	        case "pending": return "Pendiente";
	        case "processing": return "Procesando";
	        case "shipped": return "Enviado";
	        case "delivered": return "Entregado";
	        case "cancelled": return "Cancelado";
	        case "refunded": return "Reembolsado";
	        case "on_hold": return "En espera";
	        case "failed": return "Fallido";
	        default: return estadoIngles;
	    }
	}
}