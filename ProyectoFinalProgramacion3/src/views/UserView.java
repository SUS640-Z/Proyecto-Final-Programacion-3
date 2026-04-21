package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import tableModels.UserTableModel;

public class UserView extends JPanel {

	private JTable table;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	
	public UserView() {
		setLayout(new BorderLayout());
		setBackground(new Color(15, 19, 9)); 
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelButtons.setBackground(new Color(15, 19, 9));
		
		btnAdd = crearBotonCRUD("Agregar");
		btnEdit = crearBotonCRUD("Editar");
		btnDelete = crearBotonCRUD("Eliminar");
		
		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);
		panelButtons.add(btnDelete);
		
		add(panelButtons, BorderLayout.NORTH);
		table = new JTable();
		table.setBackground(new Color(48, 60, 26)); 
		table.setForeground(Color.WHITE);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.getTableHeader().setBackground(Color.DARK_GRAY);
		table.getTableHeader().setForeground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(new Color(15, 19, 9));
		
		add(scroll, BorderLayout.CENTER);
	}
	
	private JButton crearBotonCRUD(String texto) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("Arial", Font.BOLD, 12));
		btn.setBackground(new Color(210, 180, 140)); 
		btn.setForeground(Color.BLACK);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setFocusPainted(false);
		return btn;
	}
	
	public void setTableModel(UserTableModel model) { table.setModel(model); }
	
	public JTable getTable() { return table; }
	public JButton getBtnAdd() { return btnAdd; }
	public JButton getBtnEdit() { return btnEdit; }
	public JButton getBtnDelete() { return btnDelete; }
	public int getSelectedRow() { return table.getSelectedRow(); }
}