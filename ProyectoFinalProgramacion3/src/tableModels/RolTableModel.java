package tableModels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import models.Rol;

public class RolTableModel extends AbstractTableModel {
	private List<Rol> roles;
	private final String[] columns = {"ID", "Nombre del Rol", "Descripción"};

	public RolTableModel(List<Rol> roles) {
		this.roles = roles;
	}

	@Override
	public int getRowCount() { return roles.size(); }

	@Override
	public int getColumnCount() { return columns.length; }

	@Override
	public String getColumnName(int column) { return columns[column]; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Rol rol = roles.get(rowIndex);
		switch(columnIndex) {
			case 0: return rol.getId();
			case 1: return rol.getName();
			case 2: return rol.getDescription();
		}
		return null;
	}

	public Rol getRolAt(int row) { return roles.get(row); }

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		roles.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(Rol rol) {
		int row = roles.size();
		roles.add(rol);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, Rol rol) {
		roles.set(row, rol);
		fireTableRowsUpdated(row, row);
	}
}