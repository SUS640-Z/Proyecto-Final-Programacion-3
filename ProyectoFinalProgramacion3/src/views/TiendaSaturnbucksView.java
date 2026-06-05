package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TiendaSaturnbucksView {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuView();
        });
    }

    // =========================================================================
    // 1. GERENTE DEL CARRITO (ESTADO ACTUALIZADO CON REMOCIÓN)
    // =========================================================================
    public static class CarritoManager {
        private static final List<ItemCarrito> items = new ArrayList<>();

        public static void añadirProducto(Product producto, int cantidad) {
            for (ItemCarrito item : items) {
                if (item.getProducto().product_id == producto.product_id) {
                    item.setCantidad(item.getCantidad() + cantidad);
                    return;
                }
            }
            items.add(new ItemCarrito(producto, cantidad));
        }

        // NUEVO MÉTODO: Remueve un producto específico usando su ID de base de datos
        public static void removerProducto(int productId) {
            items.removeIf(item -> item.getProducto().product_id == productId);
        }

        public static List<ItemCarrito> getItems() { return items; }
        public static void limpiarCarrito() { items.clear(); }
        
        public static double calcularTotal() {
            double total = 0;
            for (ItemCarrito item : items) {
                total += item.getProducto().price * item.getCantidad();
            }
            return total;
        }
    }

    public static class ItemCarrito {
        private final Product producto;
        private int cantidad;

        public ItemCarrito(Product producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }
        public Product getProducto() { return producto; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    }

    // =========================================================================
    // 2. VISTA: MENÚ DE PRODUCTOS
    // =========================================================================
    public static class MenuView extends JFrame {
        private JPanel contentPane;
        private List<Product> bdSimuladaProductos;

        public MenuView() {
            setTitle("Saturnbucks - Menú Galáctico");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setBounds(100, 100, 900, 650);
            setResizable(false);
            setLocationRelativeTo(null);

            contentPane = new JPanel(new BorderLayout());
            contentPane.setBackground(new Color(15, 19, 9));
            setContentPane(contentPane);

            cargarProductosDesdeBD();
            generarMenuSuperior();
            generarContenidoMenu();
            generarFooter();

            setVisible(true);
        }

        private void generarMenuSuperior() {
            JPanel panelMenu = new JPanel();
            panelMenu.setBackground(new Color(15, 19, 9));
            panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26)));

            JLabel lblInicio = crearItemMenu("Inicio");
            JLabel lblSeparador1 = new JLabel("  |  ");
            lblSeparador1.setForeground(Color.DARK_GRAY);
            
            JLabel lblCarrito = crearItemMenu("Ver Carrito 🛒");

            panelMenu.add(lblInicio);
            panelMenu.add(lblSeparador1);
            panelMenu.add(lblCarrito);

            lblCarrito.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new CarritoView(); 
                }
            });

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

            JPanel gridProductos = new JPanel(new GridLayout(0, 3, 20, 20));
            gridProductos.setBackground(new Color(15, 19, 9));

            for (Product prod : bdSimuladaProductos) {
                if (prod.is_active) {
                    gridProductos.add(crearTarjetaProducto(prod));
                }
            }

            contenedor.add(gridProductos);

            JScrollPane scroll = new JScrollPane(contenedor);
            scroll.setBorder(null);
            scroll.getVerticalScrollBar().setUnitIncrement(16);
            contentPane.add(scroll, BorderLayout.CENTER);
        }

        private JPanel crearTarjetaProducto(Product prod) {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
            tarjeta.setBackground(new Color(25, 32, 16));
            tarjeta.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(48, 60, 26), 2, true),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)
            ));

            JLabel lblImagenPlaceholder = new JLabel("☕");
            lblImagenPlaceholder.setFont(new Font("Arial", Font.PLAIN, 40));
            lblImagenPlaceholder.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblImagenPlaceholder.setForeground(new Color(210, 180, 140));

            JLabel lblNombre = new JLabel(prod.product_name);
            lblNombre.setFont(new Font("Times New Roman", Font.BOLD, 18));
            lblNombre.setForeground(Color.WHITE);
            lblNombre.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lblPrecio = new JLabel("$" + prod.price + " USD (" + prod.season.toUpperCase() + ")");
            lblPrecio.setFont(new Font("Arial", Font.PLAIN, 13));
            lblPrecio.setForeground(Color.LIGHT_GRAY);
            lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
            panelAcciones.setOpaque(false);

            JLabel lblCantTag = new JLabel("Cant:");
            lblCantTag.setForeground(Color.WHITE);
            lblCantTag.setFont(new Font("Arial", Font.PLAIN, 12));

            Integer[] opciones = {1, 2, 3, 4, 5, 10};
            JComboBox<Integer> cmbCantidad = new JComboBox<>(opciones);
            cmbCantidad.setBackground(new Color(15, 19, 9));
            cmbCantidad.setForeground(Color.WHITE);

            JButton btnAñadir = new JButton("Añadir");
            btnAñadir.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            btnAñadir.setBackground(new Color(48, 60, 26));
            btnAñadir.setForeground(Color.WHITE);
            btnAñadir.setBorder(new LineBorder(Color.GRAY, 1, true));
            btnAñadir.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnAñadir.addActionListener(e -> {
                int cantidadSeleccionada = (int) cmbCantidad.getSelectedItem();
                CarritoManager.añadirProducto(prod, cantidadSeleccionada);
                JOptionPane.showMessageDialog(this, cantidadSeleccionada + "x " + prod.product_name + " añadido.");
            });

            panelAcciones.add(lblCantTag);
            panelAcciones.add(cmbCantidad);
            panelAcciones.add(btnAñadir);

            tarjeta.add(lblImagenPlaceholder);
            tarjeta.add(Box.createRigidArea(new Dimension(0, 8)));
            tarjeta.add(lblNombre);
            tarjeta.add(Box.createRigidArea(new Dimension(0, 4)));
            tarjeta.add(lblPrecio);
            tarjeta.add(Box.createRigidArea(new Dimension(0, 12)));
            tarjeta.add(panelAcciones);

            return tarjeta;
        }

        private void cargarProductosDesdeBD() {
            bdSimuladaProductos = new ArrayList<>();
            bdSimuladaProductos.add(new Product(1, "Latte de Andrómeda", 75.00, "spring", true, 1, ""));
            bdSimuladaProductos.add(new Product(2, "Supernova Cold Brew", 85.00, "summer", true, 1, ""));
            bdSimuladaProductos.add(new Product(3, "Muffin de Agujero Negro", 45.00, "winter", true, 2, ""));
            bdSimuladaProductos.add(new Product(4, "Espresso Interestelar", 60.00, "autumn", true, 1, ""));
            bdSimuladaProductos.add(new Product(5, "Tarta de Polvo Estelar", 55.00, "spring", true, 2, ""));
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
    }

    // =========================================================================
    // 3. VISTA: CARRITO (MODIFICADO CON COMPONENTE DE ACCIÓN DE ELIMINAR)
    // =========================================================================
    public static class CarritoView extends JFrame {
        private JPanel contentPane;
        private JPanel panelListaItems;
        private JLabel lblTotalNeto;

        public CarritoView() {
            setTitle("Saturnbucks - Carrito de Compras");
            setBounds(150, 150, 650, 500); // Ampliado ligeramente para el botón de borrado
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
            JLabel lblTitulo = new JLabel("TU ÓRBITA DE SELECCIÓN");
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
            actualizarTextoTotal(); // Calcula dinámicamente el precio en pantalla
            lblTotalNeto.setFont(new Font("Times New Roman", Font.BOLD, 18));
            lblTotalNeto.setForeground(Color.WHITE);

            JButton btnConfirmarOrden = new JButton("Confirmar Pedido");
            btnConfirmarOrden.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            btnConfirmarOrden.setBackground(new Color(48, 60, 26));
            btnConfirmarOrden.setForeground(Color.WHITE);
            btnConfirmarOrden.setBorder(new LineBorder(Color.GRAY, 2, true));
            btnConfirmarOrden.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnConfirmarOrden.addActionListener(e -> {
                if (CarritoManager.getItems().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El espacio está vacío. Agrega ítems en el menú.");
                    return;
                }
                JOptionPane.showMessageDialog(this, "¡Pedido Procesado! Transacción insertada en DB.");
                CarritoManager.limpiarCarrito();
                dispose();
            });

            pnlAbajo.add(lblTotalNeto, BorderLayout.WEST);
            pnlAbajo.add(btnConfirmarOrden, BorderLayout.EAST);
            contentPane.add(pnlAbajo, BorderLayout.SOUTH);
        }

        private void actualizarListaCarrito() {
            panelListaItems.removeAll();

            List<ItemCarrito> itemsActuales = CarritoManager.getItems();
            if (itemsActuales.isEmpty()) {
                JLabel lblVacio = new JLabel("No hay productos espaciales listados.");
                lblVacio.setForeground(Color.GRAY);
                lblVacio.setFont(new Font("Arial", Font.ITALIC, 14));
                lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelListaItems.add(Box.createRigidArea(new Dimension(0, 50)));
                panelListaItems.add(lblVacio);
            } else {
                for (ItemCarrito item : itemsActuales) {
                    panelListaItems.add(crearFilaItem(item));
                    panelListaItems.add(Box.createRigidArea(new Dimension(0, 6)));
                }
            }

            panelListaItems.revalidate();
            panelListaItems.repaint();
        }

        private void actualizarTextoTotal() {
            lblTotalNeto.setText("Monto de la Orden: $" + CarritoManager.calcularTotal() + " USD");
        }

        private JPanel crearFilaItem(ItemCarrito item) {
            JPanel fila = new JPanel(new BorderLayout(15, 0));
            fila.setBackground(new Color(25, 32, 16));
            fila.setMaximumSize(new Dimension(620, 55));
            fila.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

   
            JLabel lblDetalleProd = new JLabel(item.getCantidad() + "x   " + item.getProducto().product_name);
            lblDetalleProd.setFont(new Font("Arial", Font.PLAIN, 14));
            lblDetalleProd.setForeground(Color.WHITE);
            fila.add(lblDetalleProd, BorderLayout.WEST);

           
            JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
            panelDerecho.setOpaque(false);

            double subtotal = item.getProducto().price * item.getCantidad();
            JLabel lblSubtotal = new JLabel("$" + subtotal + " USD");
            lblSubtotal.setFont(new Font("Monospaced", Font.BOLD, 14));
            lblSubtotal.setForeground(new Color(210, 180, 140));


            JButton btnEliminar = new JButton("X");
            btnEliminar.setFont(new Font("Arial", Font.PLAIN, 11));
            btnEliminar.setBackground(new Color(60, 20, 20)); // Color rojizo sutil
            btnEliminar.setForeground(Color.WHITE);
            btnEliminar.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));
            btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnEliminar.setPreferredSize(new Dimension(32, 24));
            
          
            btnEliminar.addActionListener(e -> {
                CarritoManager.removerProducto(item.getProducto().product_id);
                actualizarListaCarrito(); 
                actualizarTextoTotal();  
            });

            panelDerecho.add(lblSubtotal);
            panelDerecho.add(btnEliminar);
            
            fila.add(panelDerecho, BorderLayout.EAST);

            return fila;
        }
    }

    // =========================================================================
    // 4. MODELO DE CLASES MAPEADO
    // =========================================================================
    public static class Product {
        public int product_id;
        public String product_name;
        public double price;
        public String season; 
        public boolean is_active;
        public int product_type_id;
        public String image_path;

        public Product(int product_id, String product_name, double price, String season, boolean is_active, int product_type_id, String image_path) {
            this.product_id = product_id;
            this.product_name = product_name;
            this.price = price;
            this.season = season;
            this.is_active = is_active;
            this.product_type_id = product_type_id;
            this.image_path = image_path;
        }
    }
}