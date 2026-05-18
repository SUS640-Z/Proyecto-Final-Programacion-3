package tableModels;

import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import models.User;

public class UserTableModel extends AbstractTableModel{
	
	private List<User> users;
	private final String[] columns = {"Nombre", "Apellido", "Correo", "Imagen"};
	
	public UserTableModel(List<User> users) {
		this.users = users;
	}
	
	@Override
	public int getRowCount() { return users.size(); }

	@Override
	public int getColumnCount() { return columns.length; }
	
	@Override
	public String getColumnName(int column) { return columns[column]; }
	
	public Class<?> getColumnClass(int columnIndex) {
	    if (columnIndex == 3) return ImageIcon.class; 
	    return String.class;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		User user = users.get(rowIndex);
		switch(columnIndex) {
			case 0: return user.getName();
			case 1: return user.getLastName();
			case 2: return user.getEmail();
			case 3: return cargarIcono(user.getImagePath(), 40, 40);
		}
		return null;
	}
	
	public User getUserAt(int row) { return users.get(row); }
	
	public void setUsers(List<User> users) {
		this.users = users;
		fireTableDataChanged();
	}
	
	private ImageIcon cargarIcono(String ruta, int ancho, int largo) {
		try {
			if (ruta == null || ruta.isEmpty()) return null; 
			Image icono = ImageIO.read(new File(ruta));
			icono = icono.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
			return new ImageIcon(icono);
		}catch(Exception ex) {}
		return null;
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