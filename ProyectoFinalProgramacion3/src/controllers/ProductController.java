package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import models.Product;
import repository.ProductRepository;
import services.ProductPDFExporter;
import tableModels.ProductTableModel;
import views.ProductFormDialog;
import views.ProductView;


public class ProductController {
	private ProductView view;
	private ProductRepository repo;
	private ProductTableModel model;
	private ProductPDFExporter pdfExporter;
	
	public ProductController(ProductView view) {
		this.view = view;
		repo = new ProductRepository();
		pdfExporter = new ProductPDFExporter();

		this.view.getBtnAdd().addActionListener(e -> openForm(null));
		
		this.view.getBtnEdit().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un producto", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			openForm(model.getProductTypeAt(row));
		});
		
		this.view.getBtnDelete().addActionListener(e -> {
			int row = view.getSelectedRow();
			if(row == -1) {
				JOptionPane.showMessageDialog(view, "Selecciona un producto para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			Product productToDelete = model.getProductTypeAt(row);
			String productName = productToDelete.getName();

			int opcion = JOptionPane.showConfirmDialog(
					view, 
					"¿Estás seguro de que deseas eliminar el " + productName + "?\nEsta acción no se puede deshacer.", 
					"Confirmar Eliminación", 
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE
			);

			if(opcion == JOptionPane.YES_OPTION) {
				try {
					boolean deleted = repo.delete(productToDelete.getId()); 
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
			List<Product> productsType = repo.getProducts();
			if(model == null) {
				model = new ProductTableModel(productsType);
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
			pdfExporter.exportProductType(repo.getProducts(), file);
			if(Desktop.isDesktopSupported()) Desktop.getDesktop().open(file);
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al exportar");
		}
	}
	
	
	
	private void openForm(Product product) {
		ProductFormDialog dialog = new ProductFormDialog(null, product);
		
		try {
	        JComboBox<String> comboTipos = dialog.getCmbType(); 
	        comboTipos.removeAllItems();
	        comboTipos.addItem("Seleccionar");
	        
	        List<String> rolesDeBD = repo.obtenerTipos();
	        for (String rol : rolesDeBD) {
	        	comboTipos.addItem(rol);
	        }

	        if (product != null && product.getProduct_type() != null) {
	        	comboTipos.setSelectedItem(product.getProduct_type());
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(view, "Error al cargar los tipos en el formulario: " + e.getMessage());
	    }
		
		dialog.setVisible(true);
		
		if(dialog.isSaved()) {
			Product savedProduct = dialog.getProduct();
			try {
				if(product == null) {

					repo.save(savedProduct); 
					model.addRow(savedProduct); 
				} else {
					int row = view.getSelectedRow();
		            savedProduct.setId(product.getId()); 

		            boolean updated = repo.update(product.getId(), savedProduct);
		            
		            if(updated) {
		                model.updateRow(row, savedProduct); 
		            }
				}
			} catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(view, e.getMessage());
			}
		}
	}
	
	
}