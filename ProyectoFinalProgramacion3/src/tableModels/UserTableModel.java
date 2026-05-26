package tableModels;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import models.User;
import java.awt.Image;

public class UserTableModel extends AbstractTableModel {

	private List<User> users;

	private final String[] columns = {"Nombre", "Apellido", "Rol", "Correo", "Teléfono", "Género", "Fecha Nac.", "Imagen"};

	public UserTableModel(List<User> users) {
		this.users = users;
	}

	@Override
	public int getRowCount() { return users.size(); }

	@Override
	public int getColumnCount() { return columns.length; }

	@Override
	public String getColumnName(int column) { return columns[column]; }

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == 7) return ImageIcon.class; 
		return String.class;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = users.get(rowIndex);
		switch(columnIndex) {
			case 0: return user.getName();
			case 1: return user.getLastName();
			case 2: return user.getRol(); 
			case 3: return user.getEmail();
			case 4: return user.getTelefono();
			case 5: return user.getGenero();
			case 6: return user.getFechaNacimiento();
			case 7: return cargarIcono(user.getImagePath(), 40, 40);
		}
		return null;
	}

	private ImageIcon cargarIcono(String path, int width, int height) {
		if (path != null && !path.isEmpty()) {
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(img);
		}
		return null;
	}

	public User getUserAt(int row) { return users.get(row); }
	
	public void setUsers(List<User> users) {
		this.users = users;
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		users.remove(row);
		fireTableRowsDeleted(row, row);
	}

	public void addRow(User user) {
		int row = users.size();
		users.add(user);
		fireTableRowsInserted(row, row);
	}

	public void updateRow(int row, User user) {
		users.set(row, user);
		fireTableRowsUpdated(row, row);
	}
}