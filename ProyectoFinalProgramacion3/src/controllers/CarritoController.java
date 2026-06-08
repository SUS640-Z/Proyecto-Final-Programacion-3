package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import repository.CarritoRepository;
import utils.Mandado;
import utils.Session;
import views.CarritoFormDialog;
import views.Dirreccion;

public class CarritoController {
	private CarritoFormDialog view;
	private CarritoRepository repo;
	
	public CarritoController(CarritoFormDialog view) {
		this.view = view;
		this.repo = new CarritoRepository();
		registerListeners();
	}
	

	public void registerListeners() {
	
		view.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				openForm(); 
			}
		});
		
	
		view.getBtnAgregarDireccion().addActionListener(e -> {
			view.dispose();
			new Dirreccion(Session.getCurrentUser()).setVisible(true);
		});
		
		
		view.getBtnConfirmarOrden().addActionListener(e -> {
			clickConfirmarPedido();
		});
	}


	public void openForm() {
		try {
			JComboBox<String> comboDir = view.getComboDirecciones(); 
			if (comboDir == null) return;
			
			comboDir.removeAllItems();
			comboDir.addItem("Seleccionar");
			
			int id = Session.getId();
			List<String> direcciones = repo.getAdressForCombo(id);
			
			if (direcciones != null) {
				for (String dir : direcciones) {
					comboDir.addItem(dir);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Error al cargar las direcciones: " + e.getMessage());
		}
	}




	private void clickConfirmarPedido() {
	
		if (Mandado.getItems().isEmpty()) {
			JOptionPane.showMessageDialog(view, "El espacio está vacío. Agrega ítems en el menú.");
			return;
		}
		
		int seleccionIdx = view.getComboDirecciones().getSelectedIndex();
		if (seleccionIdx <= 0) { 
			JOptionPane.showMessageDialog(view, 
					"Por favor, selecciona una dirección de envío válida galáctica.", 
					"Dirección requerida", 
					JOptionPane.WARNING_MESSAGE);
			return; 
		}
		
	
		String direccionSeleccionada = (String) view.getComboDirecciones().getSelectedItem();
		JOptionPane.showMessageDialog(view, "¡Pedido Procesado con éxito!\nDestino cósmico: " + direccionSeleccionada);
		

		Mandado.limpiarCarrito(); 
		view.dispose();
	}
}