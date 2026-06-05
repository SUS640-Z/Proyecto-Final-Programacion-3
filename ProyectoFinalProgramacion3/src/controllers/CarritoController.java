package controllers;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import repository.CarritoRepository;
import repository.LoginRepository;
import utils.Session;
import views.CarritoFormDialog;
import views.LoginView;
import views.ProductFormDialog;

public class CarritoController {
	private CarritoFormDialog view;
	private CarritoRepository repo;
	
	public CarritoController(CarritoFormDialog view) {
		this.view = view;
		this.repo = new CarritoRepository();
		registerListeners();
	}
	
	public void registerListeners(){
		OpenForm();
	}

	
	
	public void OpenForm(){
		try {
		    JComboBox<String> comboDir = view.getComboDirecciones(); 
		    comboDir.removeAllItems();
		    comboDir.addItem("---Seleccionar---");
		    
		    int id = Session.getId();
		    
		   
		    List<String> direcciones = repo.getAdressForCombo(id);
		    
		    
		    for (String dir : direcciones) {
		        comboDir.addItem(dir);
		    }

		} catch (Exception e) {
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(view, "Error al cargar las direcciones en el formulario: " + e.getMessage());
		}
	}
}
