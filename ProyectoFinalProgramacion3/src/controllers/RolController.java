package controllers;

import java.awt.Desktop;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;
import models.Rol;
import repository.RolRepository;
import tableModels.RolTableModel;
import services.PDFExporter;
import views.RolFormDialog;
import views.RolView;

public class RolController {
    private RolView view;
    private RolRepository repo;
    private RolTableModel model;
    private PDFExporter pdfExporter;

    public RolController(RolView view) {
        this.view = view;
        this.repo = new RolRepository();
        this.pdfExporter = new PDFExporter();

        // Conexión de eventos
        this.view.getBtnAdd().addActionListener(e -> openForm(null));

        this.view.getBtnEdit().addActionListener(e -> {
            int row = view.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(view, "Selecciona un rol de la tabla", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            openForm(model.getRolAt(row));
        });

        this.view.getBtnDelete().addActionListener(e -> deleteRol());

        this.view.getBtnPdf().addActionListener(e -> generatePdf());
    }

    public void loadRoles() {
        try {
            List<Rol> roles = repo.getRoles();
            if(model == null) {
                model = new RolTableModel(roles);
                view.setTableModel(model);
            } else {
                model.setRoles(roles);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Error al cargar los roles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteRol() {
        int row = view.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(view, "Selecciona un rol para eliminar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Rol rolToDelete = model.getRolAt(row);
        int opcion = JOptionPane.showConfirmDialog(view, "¿Estás seguro de eliminar el rol: " + rolToDelete.getName() + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

        if(opcion == JOptionPane.YES_OPTION) {
            try {
                boolean deleted = repo.delete(rolToDelete.getId());
                if(deleted) {
                    model.removeRow(row);
                    JOptionPane.showMessageDialog(view, "Rol eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Error al eliminar (Puede que haya usuarios asignados a este rol): " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void generatePdf() {
        File file = view.selectPdfFile();
        if(file == null) return;
        try {
            pdfExporter.exportRoles(repo.getRoles(), file);
            if(Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al exportar a PDF: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openForm(Rol rol) {
        RolFormDialog dialog = new RolFormDialog(null, rol);
        dialog.setVisible(true);

        if(dialog.isSaved()) {
            Rol savedRol = dialog.getRol();
            try {
                if(rol == null) {
                    repo.save(savedRol);
                    loadRoles(); // Recargamos para obtener el ID real de la BD
                } else {
                    savedRol.setId(rol.getId());
                    boolean updated = repo.update(savedRol.getId(), savedRol);
                    if(updated) {
                        loadRoles();
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error al guardar el rol: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}