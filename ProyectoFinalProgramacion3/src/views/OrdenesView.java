package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class OrdenesView extends JFrame {

    private JPanel contentPane;
    // Lista de órdenes basada estrictamente en tu tabla Orders
    private List<Order> listaOrdenes; 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new OrdenesView();
        });
    }

    public OrdenesView() {
        setTitle("Saturnbucks - Mis Órdenes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);

        // Simulamos el llenado que harías desde tu Base de Datos usando tus objetos relacionales
        simularCargaDatosDesdeBD();

        generarMenu();
        generarContenidoOrdenes();
        generarFooter();

        setVisible(true);
    }

    private void generarMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9));
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26)));

        JLabel lblInicio = crearItemMenu("Inicio");
        JLabel lblPerfil = crearItemMenu("Mi Perfil");
        JLabel lblCerrar = crearItemMenu("Cerrar Sesión");

        JLabel lblSeparador1 = new JLabel("  |  ");
        lblSeparador1.setForeground(Color.DARK_GRAY);
        JLabel lblSeparador2 = new JLabel("  |  ");
        lblSeparador2.setForeground(Color.DARK_GRAY);

        panelMenu.add(lblInicio);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblPerfil);
        panelMenu.add(lblSeparador2);
        panelMenu.add(lblCerrar);

        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada exitosamente", "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
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

    private void generarContenidoOrdenes() {
        JPanel contenedorPrincipal = new JPanel();
        contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
        contenedorPrincipal.setBackground(new Color(15, 19, 9));
        contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // --- ENCABEZADO ---
        JLabel lblTitulo = new JLabel("HISTORIAL DE ÓRDENES");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(210, 180, 140));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorPrincipal.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Haz clic sobre una orden para auditar los detalles y artículos adquiridos.");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 13));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        contenedorPrincipal.add(lblSubtitulo);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- PANEL DE TARJETAS (Grid de 2 columnas) ---
        JPanel panelGrid = new JPanel(new GridLayout(0, 2, 20, 20));
        panelGrid.setBackground(new Color(15, 19, 9));

        for (Order orden : listaOrdenes) {
            panelGrid.add(crearTarjetaOrden(orden));
        }

        contenedorPrincipal.add(panelGrid);

        JScrollPane scroll = new JScrollPane(contenedorPrincipal);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(15, 19, 9));

        contentPane.add(scroll, BorderLayout.CENTER);
    }

    private JPanel crearTarjetaOrden(Order orden) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(new Color(25, 32, 16)); 
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(48, 60, 26), 2, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Mapeo directo de atributos SQL
        JLabel lblID = new JLabel("Orden ID: #" + orden.order_id);
        lblID.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblID.setForeground(new Color(210, 180, 140));

        JLabel lblFecha = new JLabel("Fecha Emisión: " + orden.order_date);
        lblFecha.setFont(new Font("Arial", Font.PLAIN, 13));
        lblFecha.setForeground(Color.GRAY);

        // Pequeño contador de ítems para el resumen de la tarjeta
        int totalItems = 0;
        for(OrderDetail detail : orden.detalles) { totalItems += detail.quantity; }
        
        JLabel lblResumen = new JLabel("<html>Contiene <b>" + totalItems + "</b> artículos en esta transacción.</html>");
        lblResumen.setFont(new Font("Arial", Font.PLAIN, 14));
        lblResumen.setForeground(Color.WHITE);

        // Total y Estado (Mapeo de ENUM)
        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.setOpaque(false);
        panelAbajo.setMaximumSize(new Dimension(400, 30));

        JLabel lblTotal = new JLabel("Monto: $" + orden.total_amount);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 15));
        lblTotal.setForeground(new Color(210, 180, 140));

        // Dar formato legible a los estados del ENUM
        JLabel lblEstado = new JLabel(orden.status_order.toUpperCase());
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        
        switch (orden.status_order) {
            case "delivered" -> lblEstado.setForeground(new Color(100, 180, 100)); // Verde
            case "processing", "shipped" -> lblEstado.setForeground(new Color(230, 180, 80)); // Amarillo
            case "cancelled", "failed" -> lblEstado.setForeground(new Color(220, 90, 90)); // Rojo
            default -> lblEstado.setForeground(Color.LIGHT_GRAY);
        }

        panelAbajo.add(lblTotal, BorderLayout.WEST);
        panelAbajo.add(lblEstado, BorderLayout.EAST);

        tarjeta.add(lblID);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 4)));
        tarjeta.add(lblFecha);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 12)));
        tarjeta.add(lblResumen);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 15)));
        tarjeta.add(panelAbajo);

        // Acciones del mouse
        tarjeta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mostrarDetalleOrdenModal(orden);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(Color.WHITE, 2, true),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                tarjeta.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(new Color(48, 60, 26), 2, true),
                        BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }
        });

        return tarjeta;
    }

    // ================= VENTANA MODAL DESGLOSE: ORDER_DETAILS =================
    private void mostrarDetalleOrdenModal(Order orden) {
        JDialog modal = new JDialog(this, "Auditoría de Orden Relacional", true);
        modal.setSize(500, 500);
        modal.setLocationRelativeTo(this);
        modal.setResizable(false);

        JPanel pnlModal = new JPanel();
        pnlModal.setLayout(new BoxLayout(pnlModal, BoxLayout.Y_AXIS));
        pnlModal.setBackground(new Color(20, 25, 13)); 
        pnlModal.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel lblTitulo = new JLabel("ORDER DETAILS (ID: " + orden.order_id + ")");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(210, 180, 140));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlModal.add(lblTitulo);
        pnlModal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Cabecera relacional
        agregarFilaDetalle(pnlModal, "Fecha de Orden:", orden.order_date);
        agregarFilaDetalle(pnlModal, "Cliente ID (user_id):", String.valueOf(orden.user_id));
        agregarFilaDetalle(pnlModal, "Dirección ID (client_address_id):", String.valueOf(orden.client_address_id));
        agregarFilaDetalle(pnlModal, "Estado del ENUM:", orden.status_order);

        pnlModal.add(Box.createRigidArea(new Dimension(0, 10)));
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(48, 60, 26));
        sep.setMaximumSize(new Dimension(450, 2));
        pnlModal.add(sep);
        pnlModal.add(Box.createRigidArea(new Dimension(0, 15)));

        // Tabla interna de artículos detallados
        JLabel lblProdTitle = new JLabel("Líneas de la Orden (Order_details):");
        lblProdTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblProdTitle.setForeground(new Color(210, 180, 140));
        lblProdTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlModal.add(lblProdTitle);
        pnlModal.add(Box.createRigidArea(new Dimension(0, 10)));

        // Generar el String simulando la unión (JOIN) con la tabla Product
        StringBuilder sbDetails = new StringBuilder();
        sbDetails.append(String.format("%-6s %-25s %-10s\n", "CANT.", "PRODUCTO (ID)", "SUBTOTAL"));
        sbDetails.append("--------------------------------------------------\n");
        
        for (OrderDetail det : orden.detalles) {
            String nombreProd = det.productoAux.name + " (#" + det.product_id + ")";
            double subtotal = det.quantity * det.productoAux.price;
            sbDetails.append(String.format("  %-4d  %-25s  $%-9.2f\n", det.quantity, nombreProd, subtotal));
        }

        JTextArea txtProductos = new JTextArea(sbDetails.toString());
        txtProductos.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
        txtProductos.setForeground(Color.WHITE);
        txtProductos.setBackground(new Color(27, 34, 18)); // Panel interno para el texto
        txtProductos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtProductos.setEditable(false);
        txtProductos.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Agregar scroll al text area por si la orden tiene muchas líneas de productos
        JScrollPane scrollArea = new JScrollPane(txtProductos);
        scrollArea.setBorder(new LineBorder(new Color(48, 60, 26)));
        scrollArea.setMaximumSize(new Dimension(450, 150));
        scrollArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlModal.add(scrollArea);

        pnlModal.add(Box.createRigidArea(new Dimension(0, 15)));

        // Total
        JLabel lblTotal = new JLabel("TOTAL ACUMULADO (chk):  $" + orden.total_amount + " USD");
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

    private void generarFooter() {
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(15, 19, 9));
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        JLabel lblFooter = new JLabel("© 2026 Saturnbucks Co. | La Paz, B.C.S.");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(JLabel.CENTER);

        panelFooter.add(lblFooter);
        contentPane.add(panelFooter, BorderLayout.SOUTH);
    }

    // ================= LLENADO DE DATOS MAPEADOS DE TU SQL =================
    private void simularCargaDatosDesdeBD() {
        listaOrdenes = new ArrayList<>();

        // 1. Productos Ficticios para simular las llaves foráneas (product_id)
        ProductSimulado p1 = new ProductSimulado(101, "Capuccino Espacial", 85.00);
        ProductSimulado p2 = new ProductSimulado(102, "Muffin Saturnal", 55.00);
        ProductSimulado p3 = new ProductSimulado(103, "Orbit Cold Brew", 90.00);

        // --- ORDEN 1 ---
        Order orden1 = new Order(1, "2026-06-01", 225.00, "delivered", 4, 12);
        orden1.detalles.add(new OrderDetail(501, 2, 1, 101, p1)); // 2 Capuccinos
        orden1.detalles.add(new OrderDetail(502, 1, 1, 102, p2)); // 1 Muffin
        listaOrdenes.add(orden1);

        // --- ORDEN 2 ---
        Order orden2 = new Order(2, "2026-06-01", 180.00, "processing", 4, 12);
        orden2.detalles.add(new OrderDetail(503, 2, 2, 103, p3)); // 2 Cold Brews
        listaOrdenes.add(orden2);
        
        // --- ORDEN 3 ---
        Order orden3 = new Order(3, "2026-05-28", 55.00, "cancelled", 4, 15);
        orden3.detalles.add(new OrderDetail(504, 1, 3, 102, p2)); // 1 Muffin cancelado
        listaOrdenes.add(orden3);
    }

    // ================= ESTRUCTURAS DE CLASES ORIENTADAS A TUS TABLAS REALES =================

    // Representación exacta de la Tabla 'Orders'
    public static class Order {
        public int order_id;
        public String order_date;
        public double total_amount;
        public String status_order; // ENUM mapeado a String
        public int user_id;
        public int client_address_id;
        
        // Relación Uno a Muchos: Una orden tiene su lista de detalles
        public List<OrderDetail> detalles;

        public Order(int order_id, String order_date, double total_amount, String status_order, int user_id, int client_address_id) {
            this.order_id = order_id;
            this.order_date = order_date;
            this.total_amount = total_amount;
            this.status_order = status_order;
            this.user_id = user_id;
            this.client_address_id = client_address_id;
            this.detalles = new ArrayList<>();
        }
    }

    // Representación exacta de la Tabla 'Order_details'
    public static class OrderDetail {
        public int order_details_id;
        public int quantity;
        public int order_id;
        public int product_id;
        
        // Objeto auxiliar para recuperar el nombre y precio mediante el ID del producto
        public ProductSimulado productoAux; 

        public OrderDetail(int order_details_id, int quantity, int order_id, int product_id, ProductSimulado productoAux) {
            this.order_details_id = order_details_id;
            this.quantity = quantity;
            this.order_id = order_id;
            this.product_id = product_id;
            this.productoAux = productoAux;
        }
    }

    // Simulación auxiliar de la tabla Product
    public static class ProductSimulado {
        public int product_id;
        public String name;
        public double price;

        public ProductSimulado(int product_id, String name, double price) {
            this.product_id = product_id;
            this.name = name;
            this.price = price;
        }
    }
}