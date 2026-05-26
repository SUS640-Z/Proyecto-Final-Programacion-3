package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;
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

		this.view.getBtnAdd().addActionListener(e -> openForm(null));
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un usuario", "Aviso", JOptionPane.WARNING_MESSAGE);
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
			
			User userToDelete = model.getUserAt(row);
			String userName = userToDelete.getName();

			int opcion = JOptionPane.showConfirmDialog(
					view, 
					"¿Estás seguro de que deseas eliminar al usuario " + userName + "?\nEsta acción no se puede deshacer.", 
					"Confirmar Eliminación", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE
			);

			if(opcion == JOptionPane.YES_OPTION) {
				try {
					boolean deleted = repo.delete(userToDelete.getId()); 
					if(deleted) {

						model.removeRow(row); 
						JOptionPane.showMessageDialog(view, "Usuario eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		this.view.getBtnPdf().addActionListener(e -> generatePdf());
	}
	
	public void loadUsers() {	
		try {
			List<User> users = repo.getUsers();
			if(model == null) {
				model = new UserTableModel(users);
				view.setTableModel(model);
			} else {
				model.setUsers(users);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Error al cargar desde base de datos: " + ex.getMessage());
		}
	}
	
	public void generatePdf() {
		File file = view.selectPdfFile();
		if(file == null) return;
		try {
			pdfExporter.exportUsers(repo.getUsers(), file);
			if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al exportar");
		}
	}
	
	private void openForm(User user) {
		UserFormDialog dialog = new UserFormDialog(null, user);

		
		// ¡CÓDIGO DEL EQUIPO PARA LLENAR EL COMBOBOX!
		try {
			JComboBox<String> comboRoles = dialog.getCmbRol(); 
			comboRoles.removeAllItems();
			comboRoles.addItem("Seleccionar");
			
			List<String> rolesDeBD = repo.obtenerRoles();
			for (String rol : rolesDeBD) {
				comboRoles.addItem(rol);
			}
			
			if (user != null && user.getRol() != null) {
				comboRoles.setSelectedItem(user.getRol());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al cargar los roles en el formulario: " + e.getMessage());
		}
		
		dialog.setVisible(true);

		if(dialog.isSaved()) {
			User savedUser = dialog.getUser();
			try {
				if(user == null) {
					repo.save(savedUser);
					model.addRow(savedUser); 
				} else {
					int row = view.getSelectedRow();
		            savedUser.setId(user.getId()); 

					boolean updated = repo.update(user.getId(), savedUser);
					
					if(updated) {
						model.updateRow(row, savedUser); 
					}

				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, "Error al guardar el usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}