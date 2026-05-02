package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import models.User;
import repository.UserRepository;
import services.PDFExporter;
import tableModels.UserTableModel;
import views.UserFormDialog;
import views.UserView;

public class UserController {
	private UserView view;
	private UserRepository repo;
	private UserTableModel model;
	private PDFExporter pdfExporter;
	
	public UserController(UserView view) {
		this.view = view;
		repo = new UserRepository();
		pdfExporter = new PDFExporter();

		this.view.getBtnAdd().addActionListener(e -> {
			openForm(null);
		});
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un usuario");
				return;
			}
			
			openForm(model.getUserAt(row));
			
		});
			this.view.getBtnDelete().addActionListener(e -> {
				int row = view.getSelectedRow();

				if(row == -1) {
					JOptionPane.showMessageDialog(view, "Selecciona un usuario para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				String userName = model.getUserAt(row).getName();

				int opcion = JOptionPane.showConfirmDialog(
						view, 
						"¿Estás seguro de que deseas eliminar al usuario " + userName + "?\nEsta acción no se puede deshacer.", 
						"Confirmar Eliminación", 
						JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE
				);

				if(opcion == JOptionPane.YES_OPTION) {
					try {
						repo.delete(row); 
						loadUsers();      
						JOptionPane.showMessageDialog(view, "Usuario eliminado", "Exito", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(view, "Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			this.view.getBtnPdf().addActionListener(e -> generatePdf());
	}
	
	
	public void loadUsers() {	
		System.out.println("Carga usuarios");
		try {
			List<User> users = repo.getUsers();
			
			if(model == null) {
				model = new UserTableModel(users);
				view.setTableModel(model);
			}else {
				model.setUsers(users);
			}
			
		}catch (IOException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
	}
	
	public void generatePdf() {
		File file = view.selectPdfFile();
		
		if(file == null) {
			return;
		}
		
		try {
			pdfExporter.exportUsers(repo.getUsers(), file);
			if(Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(file);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al exportar");
		}
	}
	
	private void openForm(User user) {
		UserFormDialog dialog = new UserFormDialog(null, user);
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			User savedUser = dialog.getUser();
			
			try {
				if(user == null) {
					repo.save(savedUser);
				}else {
					int row = view.getSelectedRow();
					repo.update(row, savedUser);
				}
				
				loadUsers();
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
			
		}
		
	}
}
