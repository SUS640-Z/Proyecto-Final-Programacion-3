package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import models.OrderDetails;

public class OrderDetailsFormDialog extends JDialog {
    private JPanel contentPane;
    private JComboBox<String> cmbClient;
    private JComboBox<String> cmbOrden;
    private JComboBox<String> cmbProducto; // CORREGIDO: Se mantiene JComboBox consistentemente
    private JTextField txtCantidad;
    
    private JLabel lblAvisoClient;
    private JLabel lblAvisoOrden;
    private JLabel lblAvisoProducto;
    private JLabel lblAvisoCantidad;
    
    private JPanel panelFormulario;
    private JButton btnRegistrar;
    private JLabel lblRegresar;
    
    private OrderDetails orderDetails;
    private boolean saved = false;

    public OrderDetailsFormDialog(JFrame parent, OrderDetails orderDetails) {
        super(parent, true);
        this.orderDetails = orderDetails;
        setTitle(orderDetails == null ? "Agregar Detalle de Orden" : "Editar Detalle de Orden");
        setSize(400, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

        contentPane = new JPanel();
        contentPane.setBackground(new Color(15, 19, 9)); 
        contentPane.setLayout(new BorderLayout()); 

        Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        Border panelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                orderDetails == null ? "NUEVO DETALLE" : "EDITAR DETALLE", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);

        generarComponentes();
        aplicarEventoFocus();
        eventosCampos();
        resetearAvisos();
        loadData(); 
    }

    FocusAdapter efectoFocus = new FocusAdapter() {
        Color bordeActivo = new Color(0, 200, 120);   
        Color bordeInactivo = Color.BLACK; 

        @Override
        public void focusGained(FocusEvent e) { ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeActivo, 2)); }
        @Override
        public void focusLost(FocusEvent e) { ((JComponent) e.getSource()).setBorder(BorderFactory.createLineBorder(bordeInactivo, 1)); }
    };
    
    private void aplicarEventoFocus(){
        cmbClient.addFocusListener(efectoFocus);
        cmbOrden.addFocusListener(efectoFocus);
        cmbProducto.addFocusListener(efectoFocus);
        txtCantidad.addFocusListener(efectoFocus);
    }
    
    private void generarComponentes() {
        panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false); 

        generarCampos(panelFormulario);
        generarAvisos(panelFormulario);
        generarBotones(panelFormulario);
        
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(panelFormulario, BorderLayout.NORTH); 
        
        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null); 
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private void generarCampos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0;
        Font fontSub = new Font("Arial", Font.BOLD, 12);

        c.insets = new Insets(8, 5, 0, 5); c.gridy = 1; 
        JLabel l1 = new JLabel("Persona / Cliente:"); l1.setForeground(Color.WHITE); l1.setFont(fontSub); panel.add(l1, c);
        c.insets = new Insets(2, 5, 2, 5); c.gridy = 2; cmbClient = new JComboBox<>(); panel.add(cmbClient, c);
        
        c.insets = new Insets(8, 5, 0, 5); c.gridy = 4; 
        JLabel l2 = new JLabel("N. Orden:"); l2.setForeground(Color.WHITE); l2.setFont(fontSub); panel.add(l2, c);
        c.insets = new Insets(2, 5, 2, 5); c.gridy = 5; cmbOrden = new JComboBox<>(); panel.add(cmbOrden, c);
        
        c.insets = new Insets(8, 5, 0, 5); c.gridy = 7; 
        JLabel l3 = new JLabel("Producto:"); l3.setForeground(Color.WHITE); l3.setFont(fontSub); panel.add(l3, c);
        c.insets = new Insets(2, 5, 2, 5); c.gridy = 8; cmbProducto = new JComboBox<>(); // CORREGIDO: Inicializado correctamente como JComboBox
        panel.add(cmbProducto, c);
        
        c.insets = new Insets(8, 5, 0, 5); c.gridy = 10; 
        JLabel l4 = new JLabel("Cantidad:"); l4.setForeground(Color.WHITE); l4.setFont(fontSub); panel.add(l4, c);
        c.insets = new Insets(2, 5, 5, 5); c.gridy = 11; txtCantidad = new JTextField(20); panel.add(txtCantidad, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.insets = new Insets(0, 5, 2, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 11); 

        lblAvisoClient = new JLabel(""); lblAvisoClient.setForeground(Color.RED); lblAvisoClient.setFont(fontAviso); c.gridy = 3; panel.add(lblAvisoClient, c);
        lblAvisoOrden = new JLabel(""); lblAvisoOrden.setForeground(Color.RED); lblAvisoOrden.setFont(fontAviso); c.gridy = 6; panel.add(lblAvisoOrden, c);
        lblAvisoProducto = new JLabel(""); lblAvisoProducto.setForeground(Color.RED); lblAvisoProducto.setFont(fontAviso); c.gridy = 9; panel.add(lblAvisoProducto, c);
        lblAvisoCantidad = new JLabel(""); lblAvisoCantidad.setForeground(Color.RED); lblAvisoCantidad.setFont(fontAviso); c.gridy = 12; panel.add(lblAvisoCantidad, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 14; c.insets = new Insets(25, 5, 5, 5); 

        btnRegistrar = new JButton("Guardar");
        btnRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnRegistrar.setBackground(new Color(48, 60, 26)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(new LineBorder(Color.GRAY, 3, true));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){ btnRegistrar.setBackground(new Color(152, 158, 141)); }
            public void mouseExited(MouseEvent e){ btnRegistrar.setBackground(new Color(48, 60, 26)); }
        });
        panel.add(btnRegistrar, c);
        
        lblRegresar = new JLabel("<html><u>Cancelar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 15; c.insets = new Insets(10, 1, 15, 1);
        panel.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
            public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
            public void mouseClicked(MouseEvent e){ dispose(); }
        });
        
        btnRegistrar.addActionListener(e -> save());
    }

    private boolean verificarClient() { 
        if(cmbClient.getSelectedIndex() <= 0) { lblAvisoClient.setText("Seleccione un cliente válido"); return false; } 
        return true; 
    }
    private boolean verificarOrden() { 
        if(cmbOrden.getSelectedIndex() <= 0) { lblAvisoOrden.setText("Seleccione un número de orden"); return false; } 
        return true; 
    }
    private boolean verificarProducto() { 
        if(cmbProducto.getSelectedIndex() <= 0) { lblAvisoProducto.setText("Seleccione un producto"); return false; } 
        return true; 
    }
    private boolean verificarCantidad() { 
        String cantStr = txtCantidad.getText().trim();
        if(cantStr.isEmpty()) { lblAvisoCantidad.setText("Requerido"); return false; }
        try {
            int cant = Integer.parseInt(cantStr);
            if(cant <= 0) { lblAvisoCantidad.setText("La cantidad debe ser mayor a 0"); return false; }
        } catch(NumberFormatException e) {
            lblAvisoCantidad.setText("Debe ingresar un número entero válido");
            return false;
        }
        return true; 
    }

    private void eventosCampos(){
        // CORREGIDO: Se removió el KeyListener de letras a cmbProducto ya que al ser combobox no se digita directamente de esa forma.
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() { 
            public void keyTyped(java.awt.event.KeyEvent e) { 
                if(!Character.isDigit(e.getKeyChar())) e.consume(); 
            } 
        });
    }

    public void resetearAvisos() {
        lblAvisoClient.setText(""); 
        lblAvisoOrden.setText(""); 
        lblAvisoProducto.setText("");
        lblAvisoCantidad.setText(""); 
    }
    
    private void loadData() {
        if(orderDetails != null) {
            cmbClient.setSelectedItem(orderDetails.getClient_name());
            cmbOrden.setSelectedItem(String.valueOf(orderDetails.getOrder_id()));
            cmbProducto.setSelectedItem(orderDetails.getProduct_name()); // CORREGIDO: Adaptado para JComboBox
            txtCantidad.setText(String.valueOf(orderDetails.getQuantity()));
        }
    }
    
    private void save() {
        resetearAvisos();
        boolean valido = true;
        if (!verificarClient()) valido = false; 
        if (!verificarOrden()) valido = false; 
        if (!verificarProducto()) valido = false;
        if (!verificarCantidad()) valido = false;
        if (!valido) return;
        
        String ordenTexto = cmbOrden.getSelectedItem().toString();
        String[] partes = ordenTexto.split(" ");
        int numOrden = Integer.parseInt(partes[2]);

        int cantidad = Integer.parseInt(txtCantidad.getText().trim());
        String prodName = cmbProducto.getSelectedItem().toString(); // CORREGIDO: Lee de JComboBox
        String clientName = cmbClient.getSelectedItem().toString();

        if(orderDetails == null) {
            orderDetails = new OrderDetails();
        }
        
        orderDetails.setOrder_id(numOrden);
        orderDetails.setQuantity(cantidad);
        orderDetails.setProduct_name(prodName);
        orderDetails.setClient_name(clientName);
        
        saved = true; 
        dispose();
    }

    public boolean isSaved() { return saved; }
    public OrderDetails getOrderDetails() { return orderDetails; }

    public JComboBox<String> getCmbClient() { return cmbClient; }
    public void setCmbClient(JComboBox<String> cmbClient) { this.cmbClient = cmbClient; }

    public JComboBox<String> getCmbOrden() { return cmbOrden; }
    public void setCmbOrden(JComboBox<String> cmbOrden) { this.cmbOrden = cmbOrden; }

    public JComboBox<String> getCmbProducto() { return cmbProducto; }
    public void setCmbProducto(JComboBox<String> cmbProducto) { this.cmbProducto = cmbProducto; }

    public JTextField getTxtCantidad() { return txtCantidad; }
    public void setTxtCantidad(JTextField txtCantidad) { this.txtCantidad = txtCantidad; }
}