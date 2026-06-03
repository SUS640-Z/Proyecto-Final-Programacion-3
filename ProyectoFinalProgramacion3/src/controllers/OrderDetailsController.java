package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import models.OrderDetails;
import repository.OrderDetailsRepository;
import services.OrderDetailsPDFExporter;
import tableModels.OrderDetailsTableModel;
import views.OrderDetailsFormDialog;
import views.OrderDetailsView;

public class OrderDetailsController {
    private OrderDetailsView view;
    private OrderDetailsRepository repo;
    private OrderDetailsTableModel model;
    private OrderDetailsPDFExporter pdfExporter;

    public OrderDetailsController(OrderDetailsView view) {
        this.view = view;
        this.repo = new OrderDetailsRepository();
        this.pdfExporter = new OrderDetailsPDFExporter();

        this.view.getBtnAdd().addActionListener(e -> {
            openForm(null);
        });

        this.view.getBtnEdit().addActionListener(e -> {
            int row = view.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un detalle de orden para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            openForm(model.getOrderDetailsAt(row));
        });

        this.view.getBtnDelete().addActionListener(e -> {
            int row = view.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un detalle de orden para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            OrderDetails orderDetailsToDelete = model.getOrderDetailsAt(row);
            String itemInfo = "Orden N. " + orderDetailsToDelete.getOrder_id() + " - " + orderDetailsToDelete.getProduct_name();

            int opcion = JOptionPane.showConfirmDialog(
                    view, 
                    "¿Estás seguro de que deseas eliminar el registro (" + itemInfo + ")?\nEsta acción no se puede deshacer.", 
                    "Confirmar Eliminación", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.ERROR_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                try {
                    boolean deleted = repo.delete(orderDetailsToDelete.getId()); 
                    if (deleted) {
                        model.removeRow(row); 
                        JOptionPane.showMessageDialog(view, "Detalle de orden eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.view.getBtnPdf().addActionListener(e -> generatePdf());
        loadOrdersDetails();
    }


    public void loadOrdersDetails() {	
        try {
            List<OrderDetails> ordersDetailsList = repo.getOrdersDetails();
            if (model == null) {
                model = new OrderDetailsTableModel(ordersDetailsList);
                view.setTableModel(model);
            } else {
                model.setOrdersDetails(ordersDetailsList);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar desde la base de datos: " + ex.getMessage());
        }
    }

    public void generatePdf() {
        File file = view.selectPdfFile();
        if (file == null) return;
        try {
            pdfExporter.exportOrderDetails(repo.getOrdersDetails(), file);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al exportar a PDF: " + ex.getMessage());
        }
    }

   
    private void openForm(OrderDetails orderDetails) {
        OrderDetailsFormDialog dialog = new OrderDetailsFormDialog(null, orderDetails);
        
        cargarUsuarios(dialog, orderDetails);
        cargarProductos(dialog, orderDetails);
        
        cargarOrdenesPorUsuario(dialog, orderDetails);
        
        dialog.getCmbClient().addActionListener(e -> {
            cargarOrdenesPorUsuario(dialog, null);
        });

        dialog.setVisible(true);
        
        if (dialog.isSaved()) {
            OrderDetails savedData = dialog.getOrderDetails(); 
            
            try {
                if (orderDetails == null) {
                    repo.save(savedData); 
                    loadOrdersDetails(); 
                    JOptionPane.showMessageDialog(view, "Detalle de orden registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int row = view.getSelectedRow();
                    savedData.setId(orderDetails.getId()); 

                    boolean updated = repo.update(orderDetails.getId(), savedData);
                    if (updated) {
                        repo.updateOrder(orderDetails.getId());
                        JOptionPane.showMessageDialog(view, "Detalle de orden actualizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    }
                    repo.updateOrder(orderDetails.getId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error al procesar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public void cargarUsuarios(OrderDetailsFormDialog dialog, OrderDetails orderDetails) {
        try {
            JComboBox<String> comboClientes = dialog.getCmbClient(); 
            comboClientes.removeAllItems();
            comboClientes.addItem("Seleccionar");
            
            List<String> usuariosDeBD = repo.obtenerNombresUsuarios();
            for (String usuario : usuariosDeBD) {
                comboClientes.addItem(usuario);
            }

            if (orderDetails != null && orderDetails.getClient_name() != null) {
                comboClientes.setSelectedItem(orderDetails.getClient_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al cargar los usuarios en el formulario: " + e.getMessage());
        }
    }
    
    public void cargarOrdenesPorUsuario(OrderDetailsFormDialog dialog, OrderDetails orderDetails) {
        try {
            JComboBox<String> comboClientes = dialog.getCmbClient();
            JComboBox<String> comboOrdenes = dialog.getCmbOrden();
            
            comboOrdenes.removeAllItems();
            comboOrdenes.addItem("Seleccionar");
            
            String clienteSeleccionado = (String) comboClientes.getSelectedItem();
            
            if (clienteSeleccionado == null || clienteSeleccionado.equals("Seleccionar")) {
                return;
            }
            
            List<String> ordenesDeBD = repo.obtenerOrdenesPorUsuario(clienteSeleccionado);
            for (String orden : ordenesDeBD) {
                comboOrdenes.addItem(orden);
            }
     
            if (orderDetails != null) {
                String buscarPrefijo = "Orden N. " + orderDetails.getOrder_id() + " -";
                for (int i = 0; i < comboOrdenes.getItemCount(); i++) {
                    String item = comboOrdenes.getItemAt(i);
                    if (item.startsWith(buscarPrefijo)) {
                        comboOrdenes.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al cargar las órdenes del cliente: " + e.getMessage());
        }
    }

    public void cargarProductos(OrderDetailsFormDialog dialog, OrderDetails orderDetails) {
        try {
            JComboBox<String> comboProductos = dialog.getCmbProducto(); 
            comboProductos.removeAllItems();
            comboProductos.addItem("Seleccionar");
            
            List<String> productosDeBD = repo.obtenerNombresProductos();
            for (String producto : productosDeBD) {
                comboProductos.addItem(producto);
            }

            if (orderDetails != null && orderDetails.getProduct_name() != null) {
                comboProductos.setSelectedItem(orderDetails.getProduct_name());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al cargar los productos en el formulario: " + e.getMessage());
        }
    }
}