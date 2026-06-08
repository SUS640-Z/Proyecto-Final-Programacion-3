package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox; 
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import models.ProductMandado;
import utils.Mandado;

public class CarritoFormDialog extends JDialog {
    private JPanel contentPane;
    private JPanel panelListaItems;
    private JLabel lblTotalNeto;
    private JComboBox<String> comboDirecciones; 
    

    private JButton btnAgregarDireccion;
    private JButton btnConfirmarOrden;
    
    public CarritoFormDialog(JFrame parent) {
        super(parent, true);
        setTitle("Saturnbucks - Carrito de Compras");
        setBounds(150, 150, 650, 560); 
        setResizable(false);
        setLocationRelativeTo(parent);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(20, 25, 13));
        setContentPane(contentPane);

        generarContenidoCarrito();
    }
    
    private void generarContenidoCarrito() {
       
        JPanel pnlHeader = new JPanel();
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        JLabel lblTitulo = new JLabel("CARRITO GALACTICO");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(210, 180, 140));
        pnlHeader.add(lblTitulo);
        contentPane.add(pnlHeader, BorderLayout.NORTH);

        
        panelListaItems = new JPanel();
        panelListaItems.setLayout(new BoxLayout(panelListaItems, BoxLayout.Y_AXIS));
        panelListaItems.setBackground(new Color(15, 19, 9));

        JScrollPane scroll = new JScrollPane(panelListaItems);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(48, 60, 26), 1));
        contentPane.add(scroll, BorderLayout.CENTER);

        actualizarListaCarrito();

       
        JPanel pnlAbajoContenedor = new JPanel();
        pnlAbajoContenedor.setLayout(new BoxLayout(pnlAbajoContenedor, BoxLayout.Y_AXIS));
        pnlAbajoContenedor.setBackground(new Color(20, 25, 13));
        pnlAbajoContenedor.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));

       
        JPanel pnlDireccion = new JPanel(new BorderLayout(10, 0));
        pnlDireccion.setOpaque(false);
        pnlDireccion.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel lblDireccion = new JLabel("Enviar a:");
        lblDireccion.setFont(new Font("Times New Roman", Font.BOLD, 14));
        lblDireccion.setForeground(new Color(210, 180, 140));

        comboDirecciones = new JComboBox<>();
        comboDirecciones.setFont(new Font("Arial", Font.PLAIN, 13));
        comboDirecciones.setBackground(new Color(15, 19, 9));
        comboDirecciones.setForeground(Color.WHITE);
        comboDirecciones.setBorder(new LineBorder(new Color(48, 60, 26), 1, true));
        
      
        btnAgregarDireccion = new JButton("+");
        btnAgregarDireccion.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregarDireccion.setBackground(new Color(48, 60, 26));
        btnAgregarDireccion.setForeground(Color.WHITE);
        btnAgregarDireccion.setBorder(new LineBorder(Color.GRAY, 1, true));
        btnAgregarDireccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarDireccion.setPreferredSize(new Dimension(80, 25));

        JPanel pnlSelectorYBoton = new JPanel(new BorderLayout(8, 0));
        pnlSelectorYBoton.setOpaque(false);
        pnlSelectorYBoton.add(comboDirecciones, BorderLayout.CENTER);
        pnlSelectorYBoton.add(btnAgregarDireccion, BorderLayout.EAST);

        pnlDireccion.add(lblDireccion, BorderLayout.WEST);
        pnlDireccion.add(pnlSelectorYBoton, BorderLayout.CENTER);
        pnlAbajoContenedor.add(pnlDireccion);

       
        JPanel pnlBotonesYTotal = new JPanel(new BorderLayout());
        pnlBotonesYTotal.setOpaque(false);

        lblTotalNeto = new JLabel();
        actualizarTextoTotal();
        lblTotalNeto.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblTotalNeto.setForeground(Color.WHITE);

      
        btnConfirmarOrden = new JButton("Confirmar Pedido");
        btnConfirmarOrden.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        btnConfirmarOrden.setBackground(new Color(48, 60, 26));
        btnConfirmarOrden.setForeground(Color.WHITE);
        btnConfirmarOrden.setBorder(new LineBorder(Color.GRAY, 2, true));
        btnConfirmarOrden.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlBotonesYTotal.add(lblTotalNeto, BorderLayout.WEST);
        pnlBotonesYTotal.add(btnConfirmarOrden, BorderLayout.EAST);
        
        pnlAbajoContenedor.add(pnlBotonesYTotal);
        contentPane.add(pnlAbajoContenedor, BorderLayout.SOUTH);
    }
      
    public void actualizarListaCarrito() {
        panelListaItems.removeAll();

        List<ProductMandado> productos = Mandado.getItems();
        if (productos.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay productos");
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setFont(new Font("Arial", Font.ITALIC, 14));
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelListaItems.add(Box.createRigidArea(new Dimension(0, 50)));
            panelListaItems.add(lblVacio);
        } else {
            for (ProductMandado p : productos) {
                panelListaItems.add(crearFilaItem(p));
                panelListaItems.add(Box.createRigidArea(new Dimension(0, 6)));
            }
        }

        panelListaItems.revalidate();
        panelListaItems.repaint();
    }
      
    private JPanel crearFilaItem(ProductMandado p) {
        JPanel fila = new JPanel(new BorderLayout(15, 0));
        fila.setBackground(new Color(25, 32, 16));
        fila.setMaximumSize(new Dimension(620, 55));
        fila.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblDetalleProd = new JLabel(p.getCantidad() + "x   " + p.getProducto().getName());
        lblDetalleProd.setFont(new Font("Arial", Font.PLAIN, 14));
        lblDetalleProd.setForeground(Color.WHITE);
        fila.add(lblDetalleProd, BorderLayout.WEST);
       
        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelDerecho.setOpaque(false);

        double subtotal = p.getProducto().getPrice() * p.getCantidad();
        JLabel lblSubtotal = new JLabel("$" + subtotal);
        lblSubtotal.setFont(new Font("Monospaced", Font.BOLD, 14));
        lblSubtotal.setForeground(new Color(210, 180, 140));

        JButton btnEliminar = new JButton("X");
        btnEliminar.setFont(new Font("Arial", Font.PLAIN, 11));
        btnEliminar.setBackground(new Color(60, 20, 20)); 
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setPreferredSize(new Dimension(32, 24));

        btnEliminar.addActionListener(e -> {
            Mandado.removerProducto(p.getProducto().getId());
            actualizarListaCarrito(); 
            actualizarTextoTotal();  
        });

        panelDerecho.add(lblSubtotal);
        panelDerecho.add(btnEliminar);
        
        fila.add(panelDerecho, BorderLayout.EAST);

        return fila;
    }
      
    public void actualizarTextoTotal() {
        lblTotalNeto.setText("Monto de la Orden: $" + Mandado.calcularTotal());
    }

	public JComboBox<String> getComboDirecciones() { return comboDirecciones; }
	public JButton getBtnAgregarDireccion() { return btnAgregarDireccion; }
	public JButton getBtnConfirmarOrden() { return btnConfirmarOrden; }
}