package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import tableModels.UserTableModel;

public class UserView extends JPanel {

	private JTable table;
	private JButton btnAdd;
	private JButton btnEdit;
	private JButton btnDelete;
	private JButton btnPdf;
	
	public UserView() {
		setLayout(new BorderLayout());
		setBackground(new Color(15, 19, 9)); 
		
		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelButtons.setBackground(new Color(15, 19, 9));
		
		btnAdd = crearBotonCRUD("Agregar");
		btnEdit = crearBotonCRUD("Editar");
		btnDelete = crearBotonCRUD("Eliminar");
		btnPdf = crearBotonCRUD("Exportar a PDF");
		
		panelButtons.add(btnAdd);
		panelButtons.add(btnEdit);
		panelButtons.add(btnDelete);
		panelButtons.add(btnPdf);
		
		add(panelButtons, BorderLayout.NORTH);
		
		table = new JTable();
		styleTable(); 
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.getViewport().setBackground(new Color(15, 19, 9));
		
		add(scroll, BorderLayout.CENTER);
	}

	public void styleTable() {
		table.setRowHeight(35);
		table.setShowGrid(true);
		table.setGridColor(new Color(200, 185, 170)); 
		table.setBackground(new Color(245, 238, 230)); 
		table.setForeground(Color.BLACK);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setSelectionBackground(new Color(210, 180, 140)); 
		table.setSelectionForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(78, 59, 49)); 
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 14));
		header.setPreferredSize(new Dimension(0, 40));
		header.setReorderingAllowed(false);
		
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					if (row % 2 == 0) {
						c.setBackground(new Color(245, 238, 230)); 
					} else {
						c.setBackground(new Color(235, 224, 212)); 
					}
					c.setForeground(Color.BLACK);
				}

				c.setFont(new Font("Arial", Font.PLAIN, 14));
				((DefaultTableCellRenderer)c).setHorizontalAlignment(SwingConstants.CENTER);

				return c;
			}
		});
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
	public void setTableModel(UserTableModel model) { 
		table.setModel(model); 
		
		if(table.getColumnCount() >= 1) {
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
		}
		if(table.getColumnCount() >= 2) {
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
		}
		if(table.getColumnCount() >= 3) {
			table.getColumnModel().getColumn(2).setPreferredWidth(250);
		}
	}
	
	public File selectPdfFile() {
		String path = System.getProperty("user.home");
		JFileChooser chooser = new JFileChooser(path);
		
		chooser.setSelectedFile(new File("reporte-usuarios.pdf"));
		
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Documentos PDF",  "pdf");
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		
		int option = chooser.showDialog(this, "Exportar PDF de usuarios");
		
		if(option != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		
		File file = chooser.getSelectedFile();
		
		if(!file.getName().toLowerCase().endsWith(".pdf")) {
			file = new File(file.getAbsolutePath() + ".pdf");
		}
		
		return file;
	}
	public JTable getTable() { return table; }
	public JButton getBtnAdd() { return btnAdd; }
	public JButton getBtnEdit() { return btnEdit; }
	public JButton getBtnDelete() { return btnDelete; }
	public JButton getBtnPdf() { return btnPdf; }
	public int getSelectedRow() { return table.getSelectedRow(); }
}