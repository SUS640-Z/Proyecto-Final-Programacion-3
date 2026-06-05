package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.LblAviso;
import components.LblSubtitulo;
import models.Order;
import models.User;

public class OrderFormDialog extends JDialog {
	
	private JPanel contentPane;
	private JComboBox<String> cmbUsuarios;
	private JTextField txtTotal;
	private JComboBox<String> cmbEstado;
	
	private LblAviso lblAvisoUsuario, lblAvisoTotal, lblAvisoEstado;
	private JButton btnGuardar;
	private JLabel lblCancelar;

	private Order order;
	private List<User> listaUsuariosBD;
	private boolean saved = false;

	public OrderFormDialog(JFrame parent, Order order, List<User> usuarios) {
		super(parent, true);
		this.order = order;
		this.listaUsuariosBD = usuarios;
		
		setTitle(order == null ? "Agregar Pedido" : "Editar Pedido");
		setSize(400, 500); 
		setLocationRelativeTo(parent);
		setResizable(true);

		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));

		Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.WHITE, 2),
				order == null ? "NUEVO PEDIDO" : "EDITAR PEDIDO",
				TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14), Color.WHITE);
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		generarComponentes();
		aplicarValidacionesEnTiempoReal();
		
		if (order != null) { loadData(); }
	}
	
	private void generarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setOpaque(false);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
		Font fontAviso = new Font("Arial", Font.ITALIC, 10);

		c.insets = new Insets(10, 5, 0, 5); c.gridy = 0; panel.add(new LblSubtitulo("Cliente:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 1;
		cmbUsuarios = new JComboBox<>();
		cmbUsuarios.addItem("Seleccionar Cliente");
		for(User u : listaUsuariosBD) {
			cmbUsuarios.addItem(u.getId() + " - " + u.getName() + " " + u.getLastName());
		}
		panel.add(cmbUsuarios, c);
		lblAvisoUsuario = new LblAviso(""); lblAvisoUsuario.setForeground(Color.RED); lblAvisoUsuario.setFont(fontAviso);
		c.gridy = 2; panel.add(lblAvisoUsuario, c);

		c.insets = new Insets(5, 5, 0, 5); c.gridy = 3; panel.add(new LblSubtitulo("Total ($):"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 4; txtTotal = new JTextField(20); panel.add(txtTotal, c);
		lblAvisoTotal = new LblAviso(""); lblAvisoTotal.setForeground(Color.RED); lblAvisoTotal.setFont(fontAviso);
		c.gridy = 5; panel.add(lblAvisoTotal, c);

		c.insets = new Insets(5, 5, 0, 5); c.gridy = 6; panel.add(new LblSubtitulo("Estado:"), c);
		c.insets = new Insets(0, 5, 0, 5); c.gridy = 7; 

		String[] estados = {"Seleccionar", "Pendiente", "Procesando", "Enviado", "Entregado", "Cancelado", "Reembolsado", "En espera", "Fallido"};
		cmbEstado = new JComboBox<>(estados); panel.add(cmbEstado, c);
		lblAvisoEstado = new LblAviso(""); lblAvisoEstado.setForeground(Color.RED); lblAvisoEstado.setFont(fontAviso);
		c.gridy = 8; panel.add(lblAvisoEstado, c);

		c.insets = new Insets(25, 5, 5, 5); c.gridy = 9;
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		btnGuardar.setBackground(new Color(48, 60, 26)); btnGuardar.setForeground(Color.WHITE);
		btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGuardar.setBorder(new LineBorder(Color.GRAY, 2, true));
		btnGuardar.addActionListener(e -> save());
		panel.add(btnGuardar, c);

		c.insets = new Insets(10, 5, 10, 5); c.gridy = 10;
		lblCancelar = new JLabel("<html><u>Cancelar</u></html>", JLabel.CENTER);
		lblCancelar.setForeground(Color.WHITE); lblCancelar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCancelar.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { dispose(); } });
		panel.add(lblCancelar, c);

		JScrollPane mainScroll = new JScrollPane(panel);
		mainScroll.setBorder(null); mainScroll.setOpaque(false); mainScroll.getViewport().setOpaque(false);
		contentPane.add(mainScroll, BorderLayout.CENTER);
	}

	private void aplicarValidacionesEnTiempoReal() {
		cmbUsuarios.addItemListener(e -> { if (e.getStateChange() == ItemEvent.SELECTED && cmbUsuarios.getSelectedIndex() > 0) lblAvisoUsuario.setText(""); });
		cmbEstado.addItemListener(e -> { if (e.getStateChange() == ItemEvent.SELECTED && cmbEstado.getSelectedIndex() > 0) lblAvisoEstado.setText(""); });
		txtTotal.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { lblAvisoTotal.setText(""); }
			public void removeUpdate(DocumentEvent e) { lblAvisoTotal.setText(""); }
			public void changedUpdate(DocumentEvent e) { lblAvisoTotal.setText(""); }
		});
	}

	private void loadData() {
		txtTotal.setText(String.valueOf(order.getTotal()));

		cmbEstado.setSelectedItem(traducirInglesAEspanol(order.getStatus()));
		
		for(int i = 1; i < cmbUsuarios.getItemCount(); i++) {
			if(cmbUsuarios.getItemAt(i).startsWith(order.getUserId() + " -")) { cmbUsuarios.setSelectedIndex(i); break; }
		}
	}

	private void save() {
		boolean valido = true;
		if (cmbUsuarios.getSelectedIndex() == 0) { lblAvisoUsuario.setText("Selecciona un cliente"); valido = false; }
		if (cmbEstado.getSelectedIndex() == 0) { lblAvisoEstado.setText("Selecciona un estado"); valido = false; }
		if (txtTotal.getText().trim().isEmpty()) { lblAvisoTotal.setText("Requerido"); valido = false; }
		
		double total = 0;
		try { total = Double.parseDouble(txtTotal.getText().trim()); } catch (NumberFormatException e) { lblAvisoTotal.setText("Debe ser numérico"); valido = false; }

		if (!valido) return;

		String seleccion = (String) cmbUsuarios.getSelectedItem();
		int userId = Integer.parseInt(seleccion.split(" ")[0]);
		String userName = seleccion.substring(seleccion.indexOf("-") + 2);

		String estadoIngles = traducirEspanolAIngles((String) cmbEstado.getSelectedItem());

		if (order == null) {
			order = new Order(0, userId, userName, "", total, estadoIngles);
		} else {
			order.setUserId(userId);
			order.setUserName(userName);
			order.setTotal(total);
			order.setStatus(estadoIngles);
		}
		saved = true; dispose();
	}

	public boolean isSaved() { return saved; }
	public Order getOrder() { return order; }

	private String traducirInglesAEspanol(String estado) {
	    if (estado == null) return "Seleccionar";
	    switch (estado.toLowerCase()) {
	        case "pending": return "Pendiente";
	        case "processing": return "Procesando";
	        case "shipped": return "Enviado";
	        case "delivered": return "Entregado";
	        case "cancelled": return "Cancelado";
	        case "refunded": return "Reembolsado";
	        case "on_hold": return "En espera";
	        case "failed": return "Fallido";
	        default: return estado;
	    }
	}
	
	private String traducirEspanolAIngles(String estado) {
	    switch (estado) {
	        case "Pendiente": return "pending";
	        case "Procesando": return "processing";
	        case "Enviado": return "shipped";
	        case "Entregado": return "delivered";
	        case "Cancelado": return "cancelled";
	        case "Reembolsado": return "refunded";
	        case "En espera": return "on_hold";
	        case "Fallido": return "failed";
	        default: return "pending";
	    }
	}
}