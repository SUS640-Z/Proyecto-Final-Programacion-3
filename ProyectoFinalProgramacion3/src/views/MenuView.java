package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import models.Product;
import utils.Mandado;
import views.TiendaSaturnbucksView.CarritoManager;

public class MenuView extends JFrame {
    private List<Product> products;
    private JPanel contentPane;
    private JPanel gridProductos; // Componente global
    JLabel lblCarrito;
    JLabel lblInicio;
    
    public MenuView() {
        setTitle("Saturnbucks - Menú Galáctico");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 750);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);
          
       
        generarMenuSuperior();
        generarContenidoMenu(); 
        generarFooter();
          


        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

       setVisible(true);
    }
	
    private void generarMenuSuperior() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9));
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26)));

        lblInicio = crearItemMenu("Inicio");
        JLabel lblSeparador1 = new JLabel("  |  ");
        lblSeparador1.setForeground(Color.DARK_GRAY);
        
        lblCarrito = crearItemMenu("Ver Carrito");

        panelMenu.add(lblInicio);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblCarrito);
        
 

        contentPane.add(panelMenu, BorderLayout.NORTH);
    }
	
    private JLabel crearItemMenu(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(210, 180, 140));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { label.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { label.setForeground(new Color(210, 180, 140)); }
        });
        return label;
    }
    
    private void generarFooter() {
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(15, 19, 9));
        JLabel lblFooter = new JLabel("© 2026 Saturnbucks Co. | Menú Oficial");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        panelFooter.add(lblFooter);
        contentPane.add(panelFooter, BorderLayout.SOUTH);
    }
    
    private void generarContenidoMenu() {
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setBackground(new Color(15, 19, 9));
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel lblTitulo = new JLabel("NUESTRO MENÚ CÓSMICO");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(210, 180, 140));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedor.add(lblTitulo);
        contenedor.add(Box.createRigidArea(new Dimension(0, 20)));

    
        gridProductos = new JPanel(new GridLayout(0, 3, 20, 20));
        gridProductos.setBackground(new Color(15, 19, 9));
        
        contenedor.add(gridProductos);

        JScrollPane scroll = new JScrollPane(contenedor);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scroll, BorderLayout.CENTER);
    }
    
   
    public void actualizarMenuContenido(List<Product> listaProductos) {
        this.products = listaProductos;
        
       
        if (gridProductos == null || products == null) {
            return; 
        }
        
        gridProductos.removeAll(); 

        for (Product prod : products) {
            if (prod != null && prod.isIs_active()) { 
                gridProductos.add(crearTarjetaProducto(prod));
            }
        }

        gridProductos.revalidate();
        gridProductos.repaint();
    }

    private JPanel crearTarjetaProducto(Product prod) {
        JPanel tarjeta = new JPanel();
       
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(new Color(25, 32, 16)); 

       
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new javax.swing.border.LineBorder(new Color(48, 60, 26), 2, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
      
        JLabel lblNombre = new JLabel(prod.getName());
        lblNombre.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblPrecio;
        
        if(prod.getSeason().equals("No especificada")) {
            lblPrecio = new JLabel("$" + prod.getPrice());
        }else {
            lblPrecio = new JLabel("$" + prod.getPrice() + " (" + prod.getSeason().toUpperCase() + ")");
        }

   
        lblPrecio.setFont(new Font("Arial", Font.PLAIN, 13));
        lblPrecio.setForeground(Color.LIGHT_GRAY);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

     
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        panelAcciones.setOpaque(false); 

        JLabel lblCantTag = new JLabel("Cant:");
        lblCantTag.setForeground(Color.WHITE);
        lblCantTag.setFont(new Font("Arial", Font.PLAIN, 12));

        
        SpinnerNumberModel modeloSpinner = new SpinnerNumberModel(1, 1, 999, 1);
        JSpinner spnCantidad = new JSpinner(modeloSpinner);
        spnCantidad.setPreferredSize(new Dimension(55, 25));
        
        
        spnCantidad.getEditor().getComponent(0).setBackground(new Color(15, 19, 9));
        spnCantidad.getEditor().getComponent(0).setForeground(Color.WHITE);

        
        JButton btnAñadir = new JButton("Añadir");
        btnAñadir.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnAñadir.setBackground(new Color(48, 60, 26));
        btnAñadir.setForeground(Color.WHITE);
        btnAñadir.setBorder(new LineBorder(Color.GRAY, 1, true));
        btnAñadir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnAñadir.addActionListener(e -> {
            int cantidadSeleccionada = (int) spnCantidad.getValue();
            Mandado.añadirProducto(prod, cantidadSeleccionada);
            JOptionPane.showMessageDialog(this, cantidadSeleccionada + "x " + prod.getName() + " añadido.");
        });

       
        panelAcciones.add(lblCantTag);
        panelAcciones.add(spnCantidad);
        panelAcciones.add(btnAñadir);

        
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 4)));
        tarjeta.add(lblPrecio);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 12)));
        tarjeta.add(panelAcciones);

        return tarjeta;
    }

	public JLabel getLblCarrito() {
		return lblCarrito;
	}

	public void setLblCarrito(JLabel lblCarrito) {
		this.lblCarrito = lblCarrito;
	}

	public JLabel getLblInicio() {
		return lblInicio;
	}

	public void setLblInicio(JLabel lblInicio) {
		this.lblInicio = lblInicio;
	}
	
	
    
    
}
