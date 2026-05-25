package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import models.Address;
import models.User;
import repository.AddressRepository;
import tableModels.AddressTableModel;
import services.PDFExporter;
import views.AddressFormDialog;
import views.AddressView;

public class AddressController {
	private AddressView view;
	private AddressRepository repo;
	private AddressTableModel model;
	private PDFExporter pdfExporter; 

	public AddressController(AddressView view) {
		this.view = view;
		this.repo = new AddressRepository();
		this.pdfExporter = new PDFExporter(); 

		this.view.getBtnAdd().addActionListener(e -> openForm(null));

		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona una dirección de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			openForm(model.getAddressAt(row));
		});

		this.view.getBtnDelete().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona una dirección para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}

			Address addrToDelete = model.getAddressAt(row);
			int opcion = JOptionPane.showConfirmDialog(view, "¿Estás seguro de eliminar esta dirección?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

			if(opcion == JOptionPane.YES_OPTION) {
				try {
					boolean deleted = repo.delete(addrToDelete.getId());
					if(deleted) {
						model.removeRow(row);
						JOptionPane.showMessageDialog(view, "Dirección eliminada.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "Error al eliminar: " + ex.getMessage());
				}
			}
		});

		this.view.getBtnPdf().addActionListener(e -> generatePdf());
	}

	public void loadAddresses() {
		try {
			List<Address> addresses = repo.getAddresses();
			if(model == null) {
				model = new AddressTableModel(addresses);
				view.setTableModel(model);
			} else {
				model.setAddresses(addresses);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Error al cargar direcciones: " + ex.getMessage());
		}
	}

	public void generatePdf() {
	    File file = view.selectPdfFile();
	    if(file == null) return;
	    try {
	        pdfExporter.exportAddresses(repo.getAddresses(), file); 
	        if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
	    } catch(Exception ex) { 
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(view, "Error al exportar: " + ex.getMessage());
	    }
	}

	private void openForm(Address address) {
		try {
			List<User> usuariosBD = repo.getUsersForCombo();
			AddressFormDialog dialog = new AddressFormDialog(null, address, usuariosBD);
			dialog.setVisible(true);
			if(dialog.isSaved()) {
				Address savedAddr = dialog.getAddress();
				if(address == null) {
					repo.save(savedAddr);
					loadAddresses(); 
				} else {
					savedAddr.setId(address.getId());
					boolean updated = repo.update(savedAddr.getId(), savedAddr);
					if(updated) {
						loadAddresses(); 
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al abrir formulario: " + e.getMessage());
		}
	}
}