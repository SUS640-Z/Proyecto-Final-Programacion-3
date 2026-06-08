package controllers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import repository.CarritoRepository;
import utils.Mandado;
import utils.Session;
import views.CarritoFormDialog;
import views.CarritoView;
import views.Dirreccion;
import views.InicioView; 

public class CarritoController {
    private CarritoView view;
    private CarritoRepository repo;
    private JFrame menuPrincipal;
    
    public CarritoController(CarritoView view,JFrame menuPrincipal) {
        this.view = view;
        this.menuPrincipal = menuPrincipal;
        this.repo = new CarritoRepository();
        registerListeners();
    }
    
    public void registerListeners() {
    	
    	
    	view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                openForm(); 
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                if (menuPrincipal != null) {
                    menuPrincipal.setVisible(true);
                }
            }
        });
        
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                openForm(); 
            }
        });
        
       
        view.getBtnAgregarDireccion().addActionListener(e -> {
            view.dispose();
            Dirreccion ventanaDir = new Dirreccion(Session.getCurrentUser(), menuPrincipal);
            ventanaDir.setLocationRelativeTo(null);            
            ventanaDir.setVisible(true);
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
        
    
        String itemSeleccionado = (String) view.getComboDirecciones().getSelectedItem();
        try {
            int idUsuario = Session.getId();
            
           
            String[] partesPrincipales = itemSeleccionado.split(", ");
            String barrio = partesPrincipales[0].trim();
            String resto = partesPrincipales[1];
            String calle = resto.substring(0, resto.indexOf(" (Ref: ")).trim();
            String referencia = resto.substring(resto.indexOf(" (Ref: ") + 7, resto.length() - 1).trim();
            
            int idDireccionReal = repo.findAddressIdByDetails(idUsuario, barrio, calle, referencia);

            double total = Mandado.calcularTotal();
            String estadoPedido = "pending"; 
            
            repo.saveOrder(total, estadoPedido, idUsuario, idDireccionReal);
            
            JOptionPane.showMessageDialog(view, "¡Pedido Completado!\n");
            
            new InicioView(Session.getCurrentUser()).setVisible(true);
            
            view.dispose();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al procesar la dirección o el pedido: " + e.getMessage(), 
                    "Error de Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}