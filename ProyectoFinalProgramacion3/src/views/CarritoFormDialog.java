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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import models.ProductMandado;
import utils.Mandado;


public class CarritoFormDialog extends JDialog {
	private JPanel contentPane;
    private JPanel panelListaItems;
    private JLabel lblTotalNeto;
	
	public CarritoFormDialog(JFrame parent) {
		super(parent, true);
		setLocationRelativeTo(parent);
		setTitle("Saturnbucks - Carrito de Compras");
        setBounds(150, 150, 650, 500); 
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(20, 25, 13));
        setContentPane(contentPane);

        generarContenidoCarrito();
        setVisible(true);
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

          JPanel pnlAbajo = new JPanel(new BorderLayout());
          pnlAbajo.setBackground(new Color(20, 25, 13));
          pnlAbajo.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

          lblTotalNeto = new JLabel();
          actualizarTextoTotal();
          lblTotalNeto.setFont(new Font("Times New Roman", Font.BOLD, 18));
          lblTotalNeto.setForeground(Color.WHITE);

          JButton btnConfirmarOrden = new JButton("Confirmar Pedido");
          btnConfirmarOrden.setFont(new Font("Times New Roman", Font.PLAIN, 16));
          btnConfirmarOrden.setBackground(new Color(48, 60, 26));
          btnConfirmarOrden.setForeground(Color.WHITE);
          btnConfirmarOrden.setBorder(new LineBorder(Color.GRAY, 2, true));
          btnConfirmarOrden.setCursor(new Cursor(Cursor.HAND_CURSOR));

          btnConfirmarOrden.addActionListener(e -> {
              if (Mandado.getItems().isEmpty()) {
                  JOptionPane.showMessageDialog(this, "El espacio está vacío. Agrega ítems en el menú.");
                  return;
              }
              /*
              JOptionPane.showMessageDialog(this, "¡Pedido Procesado! Transacción insertada en DB.");
              CarritoManager.limpiarCarrito();
              dispose();
              */
          });

          pnlAbajo.add(lblTotalNeto, BorderLayout.WEST);
          pnlAbajo.add(btnConfirmarOrden, BorderLayout.EAST);
          contentPane.add(pnlAbajo, BorderLayout.SOUTH);
      }
	  
	  
	  private void actualizarListaCarrito() {
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
	  
	  private void actualizarTextoTotal() {
          lblTotalNeto.setText("Monto de la Orden: $" + Mandado.calcularTotal());
      }

	
}
