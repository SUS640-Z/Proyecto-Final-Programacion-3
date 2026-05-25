package tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Address;

public class AddressTableModel extends AbstractTableModel {
	private List<Address> addresses;
	private final String[] columns = {"ID", "Cliente (Dueño)", "Colonia", "Calle", "Referencia"};

	public AddressTableModel(List<Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public int getRowCount() { return addresses.size(); }

	@Override
	public int getColumnCount() { return columns.length; }

	@Override
	public String getColumnName(int column) { return columns[column]; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Address addr = addresses.get(rowIndex);
		switch(columnIndex) {
			case 0: return addr.getId();
			case 1: return addr.getUserName(); 
			case 2: return addr.getNeighborhood();
			case 3: return addr.getStreet();
			case 4: return addr.getReference();
		}
		return null;
	}

	public Address getAddressAt(int row) { return addresses.get(row); }

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		addresses.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(Address addr) {
		int row = addresses.size();
		addresses.add(addr);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, Address addr) {
		addresses.set(row, addr);
		fireTableRowsUpdated(row, row);
	}
}