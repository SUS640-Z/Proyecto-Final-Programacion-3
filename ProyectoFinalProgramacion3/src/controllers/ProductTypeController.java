package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import models.ProductType;
import repository.ProductTypeRepository;
import services.ProductTypePDFExporter;
import tableModels.ProductTypeTableModel;
import views.ProductTypeFormDialog;
import views.ProductTypeView;


public class ProductTypeController {
	private ProductTypeView view;
	private ProductTypeRepository repo;
	private ProductTypeTableModel model;
	private ProductTypePDFExporter pdfExporter;
	
	public ProductTypeController(ProductTypeView view) {
		this.view = view;
		repo = new ProductTypeRepository();
		pdfExporter = new ProductTypePDFExporter();

		this.view.getBtnAdd().addActionListener(e -> openForm(null));
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un tipo de producto", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			openForm(model.getProductTypeAt(row));
		});
		
		this.view.getBtnDelete().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un tipo de producto para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			ProductType productTypeToDelete = model.getProductTypeAt(row);
			String productTypeName = productTypeToDelete.getName();

			int opcion = JOptionPane.showConfirmDialog(
					view, 
					"¿Estás seguro de que deseas eliminar el " + productTypeName + "?\nEsta acción no se puede deshacer.", 
					"Confirmar Eliminación", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE
			);

			if(opcion == JOptionPane.YES_OPTION) {
				try {
					boolean deleted = repo.delete(productTypeToDelete.getId()); 
					if(deleted) {

						model.removeRow(row); 
						JOptionPane.showMessageDialog(view, "Tipo de producto eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(view, "Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		this.view.getBtnPdf().addActionListener(e -> generatePdf());
		loadProductsType();
	}
	
	public void loadProductsType() {	
		try {
			List<ProductType> productsType = repo.getProductsType();
			if(model == null) {
				model = new ProductTypeTableModel(productsType);
				view.setTableModel(model);
			} else {
				model.setProductsType(productsType);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(view, "Error al cargar desde base de datos: " + ex.getMessage());
		}
	}
	
	
	public void generatePdf() {
		File file = view.selectPdfFile();
		if(file == null) return;
		try {
			pdfExporter.exportProductType(repo.getProductsType(), file);
			if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al exportar");
		}
	}
	
	
	
	private void openForm(ProductType productType) {
		ProductTypeFormDialog dialog = new ProductTypeFormDialog(null, productType);
		
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			ProductType savedUser = dialog.getProductType();
			try {
				if(productType == null) {

					repo.save(savedUser); 
					model.addRow(savedUser); 
				} else {
					int row = view.getSelectedRow();
		            savedUser.setId(productType.getId()); 

		            boolean updated = repo.update(productType.getId(), savedUser);
		            
		            if(updated) {
		                model.updateRow(row, savedUser); 
		            }
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
	}
	
	
}
