package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import models.Order;
import models.OrderDetails;
import repository.OrderRepository;
import repository.OrderDetailsRepository;
import utils.Session;
import views.InicioView;
import views.OrdenesView;
import views.ProfileView;

public class OrdenesController {
	private OrdenesView view;
	private OrderRepository orderRepo;
	private OrderDetailsRepository detailsRepo;

	public OrdenesController(OrdenesView view) {
		this.view = view;
		this.orderRepo = new OrderRepository();
		this.detailsRepo = new OrderDetailsRepository();

		registerListeners();
		cargarTarjetasDesdeBD();
		
		this.view.setVisible(true);
	}

	private void registerListeners() {
		view.getLblInicio().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				new InicioView();
			}
		});

		view.getLblPerfil().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				ProfileView perfilView = new ProfileView();
				new ProfileController(perfilView);
			}
		});

		view.getLblCerrar().addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Sesión cerrada exitosamente");
				view.dispose();
				Session.logout();
				new InicioView();
			}
		});
	}

	private void cargarTarjetasDesdeBD() {
		try {
			int idUsuario = Session.getId();
			List<Order> ordenes = orderRepo.getOrdersByUserId(idUsuario);

			JPanel pnlGrid = view.getPnlGridOrdenes();
			pnlGrid.removeAll();

			if (ordenes.isEmpty()) {
				JLabel lblVacio = new JLabel("Aún no tienes órdenes registradas en Saturnbucks.");
				lblVacio.setForeground(Color.GRAY);
				pnlGrid.add(lblVacio);
			} else {
				for (Order orden : ordenes) {
					pnlGrid.add(crearTarjetaMapeada(orden));
				}
			}

			pnlGrid.revalidate();
			pnlGrid.repaint();

		} catch (Exception e) {
			System.out.println("Error cargando historial de órdenes reales: " + e.getMessage());
		}
	}

	// Recreamos exactamente tu diseño de tarjeta, pero con el modelo Order real
	private JPanel crearTarjetaMapeada(Order orden) {
		JPanel tarjeta = new JPanel();
		tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
		tarjeta.setBackground(new Color(25, 32, 16)); 
		tarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(new Color(48, 60, 26), 2, true),
				BorderFactory.createEmptyBorder(15, 20, 15, 20)
		));
		tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

		JLabel lblID = new JLabel("Orden ID: #" + orden.getId());
		lblID.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblID.setForeground(new Color(210, 180, 140));

		JLabel lblFecha = new JLabel("Fecha Emisión: " + orden.getOrderDate());
		lblFecha.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFecha.setForeground(Color.GRAY);

		JPanel panelAbajo = new JPanel(new BorderLayout());
		panelAbajo.setOpaque(false);
		panelAbajo.setMaximumSize(new Dimension(400, 30));

		JLabel lblTotal = new JLabel("Monto: $" + orden.getTotal());
		lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
		lblTotal.setForeground(new Color(210, 180, 140));

		JLabel lblEstado = new JLabel(traducirEstado(orden.getStatus()).toUpperCase());
		lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
		
		switch (orden.getStatus().toLowerCase()) {
			case "delivered" -> lblEstado.setForeground(new Color(100, 180, 100)); 
			case "processing", "shipped", "pending" -> lblEstado.setForeground(new Color(230, 180, 80)); 
			case "cancelled", "failed" -> lblEstado.setForeground(new Color(220, 90, 90)); 
			default -> lblEstado.setForeground(Color.LIGHT_GRAY);
		}

		panelAbajo.add(lblTotal, BorderLayout.WEST);
		panelAbajo.add(lblEstado, BorderLayout.EAST);

		tarjeta.add(lblID);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 4)));
		tarjeta.add(lblFecha);
		tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
		tarjeta.add(panelAbajo);

		tarjeta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mostrarModalAuditoria(orden);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				tarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.WHITE, 2, true), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				tarjeta.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(48, 60, 26), 2, true), BorderFactory.createEmptyBorder(15, 20, 15, 20)));
			}
		});

		return tarjeta;
	}

	// Recreamos tu Modal pero consultando la base de datos real
	private void mostrarModalAuditoria(Order orden) {
		JDialog modal = new JDialog(view, "Auditoría de Orden Relacional", true);
		modal.setSize(500, 500);
		modal.setLocationRelativeTo(view);
		modal.setResizable(false);

		JPanel pnlModal = new JPanel();
		pnlModal.setLayout(new BoxLayout(pnlModal, BoxLayout.Y_AXIS));
		pnlModal.setBackground(new Color(20, 25, 13)); 
		pnlModal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

		JLabel lblTitulo = new JLabel("ORDER DETAILS (ID: " + orden.getId() + ")");
		lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblTitulo.setForeground(new Color(210, 180, 140));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlModal.add(lblTitulo);
		pnlModal.add(Box.createRigidArea(new Dimension(0, 20)));

		agregarFilaDetalle(pnlModal, "Fecha de Orden:", orden.getOrderDate());
		agregarFilaDetalle(pnlModal, "Cliente ID (user_id):", String.valueOf(orden.getUserId()));
		agregarFilaDetalle(pnlModal, "Estado:", traducirEstado(orden.getStatus()));

		pnlModal.add(Box.createRigidArea(new Dimension(0, 10)));
		JSeparator sep = new JSeparator();
		sep.setForeground(new Color(48, 60, 26));
		sep.setMaximumSize(new Dimension(450, 2));
		pnlModal.add(sep);
		pnlModal.add(Box.createRigidArea(new Dimension(0, 15)));

		// Sacamos los productos reales de esa orden desde la base de datos
		StringBuilder sbDetails = new StringBuilder();
		sbDetails.append(String.format("%-25s %-10s\n", "PRODUCTO", "CANTIDAD"));
		sbDetails.append("------------------------------------------\n");
		
		try {
			// Filtramos los detalles reales que le pertenecen a este ID de orden
			List<OrderDetails> listaDetallesBD = detailsRepo.getOrdersDetails();
			boolean tieneDetalles = false;
			
			for(OrderDetails det : listaDetallesBD) {
				if(det.getOrder_id() == orden.getId()) {
					sbDetails.append(String.format("%-25s %-10d\n", det.getProduct_name(), det.getQuantity()));
					tieneDetalles = true;
				}
			}
			if(!tieneDetalles) {
				sbDetails.append("No se encontraron productos para esta orden.\n");
			}
		} catch (Exception ex) {
			sbDetails.append("Error cargando productos de la base de datos.");
		}

		javax.swing.JTextArea txtProductos = new javax.swing.JTextArea(sbDetails.toString());
		txtProductos.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
		txtProductos.setForeground(Color.WHITE);
		txtProductos.setBackground(new Color(27, 34, 18));
		txtProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		txtProductos.setEditable(false);
		txtProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JScrollPane scrollArea = new JScrollPane(txtProductos);
		scrollArea.setBorder(new LineBorder(new Color(48, 60, 26)));
		scrollArea.setMaximumSize(new Dimension(450, 150));
		scrollArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		pnlModal.add(scrollArea);

		pnlModal.add(Box.createRigidArea(new Dimension(0, 15)));

		JLabel lblTotal = new JLabel("TOTAL ACUMULADO:  $" + orden.getTotal() + " MXN");
		lblTotal.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblTotal.setForeground(new Color(210, 180, 140));
		lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlModal.add(lblTotal);

		pnlModal.add(Box.createRigidArea(new Dimension(0, 20)));

		JButton btnCerrar = new JButton("Cerrar Detalles");
		btnCerrar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		btnCerrar.setBackground(new Color(48, 60, 26));
		btnCerrar.setForeground(Color.WHITE);
		btnCerrar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCerrar.setPreferredSize(new Dimension(150, 35));
		btnCerrar.setMaximumSize(new Dimension(150, 35));
		btnCerrar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnCerrar.addActionListener(e -> modal.dispose());
		pnlModal.add(btnCerrar);

		modal.add(pnlModal);
		modal.setVisible(true);
	}

	private void agregarFilaDetalle(JPanel panel, String tag, String info) {
		JPanel fila = new JPanel(new BorderLayout());
		fila.setOpaque(false);
		fila.setMaximumSize(new Dimension(450, 24));
		JLabel lblTag = new JLabel(tag);
		lblTag.setFont(new Font("Arial", Font.BOLD, 13));
		lblTag.setForeground(Color.LIGHT_GRAY);
		JLabel lblInfo = new JLabel(info);
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 13));
		lblInfo.setForeground(Color.WHITE);
		fila.add(lblTag, BorderLayout.WEST);
		fila.add(lblInfo, BorderLayout.EAST);
		panel.add(fila);
		panel.add(Box.createRigidArea(new Dimension(0, 4)));
	}

	private String traducirEstado(String estado) {
		if (estado == null) return "Desconocido";
		switch (estado.toLowerCase()) {
			case "pending": return "Pendiente";
			case "processing": return "Procesando";
			case "shipped": return "Enviado";
			case "delivered": return "Entregado";
			case "cancelled": return "Cancelado";
			default: return estado;
		}
	}
}