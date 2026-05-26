package controllers;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import models.Order;
import models.User;
import repository.OrderRepository;
import repository.UserRepository;
import tableModels.OrderTableModel;
import views.OrderFormDialog;
import views.OrderView;
import services.PDFExporter;

public class OrderController {
	
	private OrderView view;
	private OrderRepository repo;
	private OrderTableModel model;

	public OrderController(OrderView view) {
		this.view = view;
		this.repo = new OrderRepository();
		registerListeners();
	}

	private void registerListeners() {
		view.btnAgregar.addActionListener(e -> openForm(null));
		
		view.btnEditar.addActionListener(e -> {
			int row = view.getSelectedRow();
			if (row >= 0) {
				Order order = model.getOrders().get(row);
				openForm(order);
			} else {
				JOptionPane.showMessageDialog(view, "Selecciona un pedido para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		});

		view.btnEliminar.addActionListener(e -> deleteOrder());
		view.btnExportar.addActionListener(e -> exportToPDF());
	}

	public void loadOrders() {
		try {
			List<Order> orders = repo.getOrders();
			model = new OrderTableModel(orders);
			view.setTableModel(model);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al cargar pedidos: " + e.getMessage());
		}
	}

	private void openForm(Order order) {
		try {
			UserRepository userRepo = new UserRepository();
			List<User> usuarios = userRepo.getUsers();
			
			OrderFormDialog dialog = new OrderFormDialog((JFrame) SwingUtilities.getWindowAncestor(view), order, usuarios);
			dialog.setVisible(true);

			if (dialog.isSaved()) {
				Order savedOrder = dialog.getOrder();
				if (order == null) {
					repo.save(savedOrder);
				} else {
					repo.update(order.getId(), savedOrder);
				}
				loadOrders(); 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al abrir el formulario: " + ex.getMessage());
		}
	}

	private void deleteOrder() {
		int row = view.getSelectedRow();
		if (row >= 0) {
			Order order = model.getOrders().get(row);
			int option = JOptionPane.showConfirmDialog(view, 
					"¿Seguro que deseas eliminar el pedido #" + order.getId() + "?\nSe borrarán también sus productos.", 
					"Confirmar", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				if (repo.delete(order.getId())) {
					loadOrders();
				} else {
					JOptionPane.showMessageDialog(view, "Error al eliminar el pedido.");
				}
			}
		} else {
			JOptionPane.showMessageDialog(view, "Selecciona un pedido para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}

	private void exportToPDF() {
		java.io.File file = view.selectPdfFile();
		if (file == null) return; 
		
		try {
			services.PDFExporter pdfExporter = new services.PDFExporter();

			pdfExporter.exportOrders(model.getOrders(), file);

			if (java.awt.Desktop.isDesktopSupported()) {
				java.awt.Desktop.getDesktop().open(file);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(view, "Error al exportar a PDF: " + ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}