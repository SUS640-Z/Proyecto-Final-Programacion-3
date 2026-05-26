package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;

import components.LblAviso;
import components.LblSubtitulo;
import models.Product;

public class ProductFormDialog extends JDialog {
    JPanel contentPane;
    
    // Tus 5 componentes requeridos (Variables Globales)
    JTextField txtName;
    JFormattedTextField txtPrice;
    JComboBox<String> cmbSeason;
    JComboBox<String> cmbType;
    JCheckBox chkActive;
    
    LblAviso lblAvisoName;
    LblAviso lblAvisoPrice;
    LblAviso lblAvisoSeason;
    LblAviso lblAvisoType;
    
    JPanel panelFormulario;
    JButton btnRegistrar;
    JLabel lblRegresar;
    
    private Product product;
    private boolean saved = false;

    public ProductFormDialog(JFrame parent, Product product) {
        super(parent, true);
        this.product = product;
        setTitle(product == null ? "Agregar producto" : "Editar producto");
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 400, 450); 
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
                product == null ? "NUEVO PRODUCTO" : "EDITAR PRODUCTO", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);

        // SOLUCIÓN AL NULL POINTER EXCEPTION:
        // 1. Generar componentes asigna memoria a las variables globales primero.
        generarComponentes();
        // 2. Ahora que no son nulos, aplicar los focos es seguro.
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
    
    private void aplicarEventoFocus() {
        txtName.addFocusListener(efectoFocus);
        txtPrice.addFocusListener(efectoFocus);
        cmbSeason.addFocusListener(efectoFocus);
        cmbType.addFocusListener(efectoFocus);
        chkActive.addFocusListener(efectoFocus);
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
        c.fill = GridBagConstraints.HORIZONTAL; 
        c.gridx = 0;

        // 1. Campo Nombre
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 1; panel.add(new LblSubtitulo("Nombre:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 2; 
        txtName = new JTextField(20); 
        panel.add(txtName, c);
        
        // 2. Campo Precio
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 4; panel.add(new LblSubtitulo("Precio:"), c);
        
        NumberFormat formatoPrecio = NumberFormat.getNumberInstance();
        formatoPrecio.setMinimumFractionDigits(0);
        formatoPrecio.setMaximumFractionDigits(2);
        
        NumberFormatter formateador = new NumberFormatter(formatoPrecio);
        formateador.setValueClass(Double.class);
        formateador.setAllowsInvalid(false); 

        // Se usa la variable global de la clase directamente
        txtPrice = new JFormattedTextField(formateador);
        txtPrice.setColumns(20);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 5; 
        panel.add(txtPrice, c);
        
        // 3. Campo Tipo
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 7; panel.add(new LblSubtitulo("Tipo:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 8; 
        String[] tipos = {"Seleccionar", "Bebida", "Alimento", "Postre", "Mercancía"}; 
        cmbType = new JComboBox<>(tipos); 
        panel.add(cmbType, c);
        
        // 4. Campo Temporada
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 10; panel.add(new LblSubtitulo("Temporada:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 11; 
        String[] temporadas = {"Primavera", "Verano", "Otoño", "Invierno"}; 
        cmbSeason = new JComboBox<>(temporadas); 
        panel.add(cmbSeason, c);
        
        // 5. Campo Activo (Switch de FlatLaf)
        c.insets = new Insets(2, 5, 0, 5); c.gridy = 13; panel.add(new LblSubtitulo("Estado del Producto:"), c);
        c.insets = new Insets(0, 5, 5, 5); c.gridy = 14; 
        chkActive = new JCheckBox("Activo / Disponible");
        chkActive.setOpaque(false);
        chkActive.setForeground(Color.WHITE);
        chkActive.putClientProperty("JButton.buttonType", "switch");
        panel.add(chkActive, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.insets = new Insets(0, 5, 2, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 10); 

        lblAvisoName = new LblAviso(""); lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso); c.gridy = 3; panel.add(lblAvisoName, c);
        lblAvisoPrice = new LblAviso(""); lblAvisoPrice.setForeground(Color.RED); lblAvisoPrice.setFont(fontAviso); c.gridy = 6; panel.add(lblAvisoPrice, c);
        lblAvisoType = new LblAviso(""); lblAvisoType.setForeground(Color.RED); lblAvisoType.setFont(fontAviso); c.gridy = 9; panel.add(lblAvisoType, c);
        lblAvisoSeason = new LblAviso(""); lblAvisoSeason.setForeground(Color.RED); lblAvisoSeason.setFont(fontAviso); c.gridy = 12; panel.add(lblAvisoSeason, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 30; c.insets = new Insets(20, 5, 5, 5); 

        btnRegistrar = new JButton("Guardar");
        btnRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnRegistrar.setBackground(new Color(48, 60, 26)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(new LineBorder(Color.GRAY, 3, true));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegistrar.setPreferredSize(new Dimension(150, 35));
        
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
        
        c.gridy = 31; c.insets = new Insets(10, 1, 15, 1);
        panel.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
            public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
            public void mouseClicked(MouseEvent e){ dispose(); }
        });
        
        btnRegistrar.addActionListener(e -> save());
    }

    // --- VALIDACIONES DE CAMPOS EN TIEMPO REAL ---
    private boolean verificarName() { 
        if(txtName.getText().trim().equals("")) { lblAvisoName.setText("El nombre es requerido"); return false; } 
        return true; 
    }
    
    private boolean verificarPrice() { 
        if(txtPrice.getText().trim().equals("") || txtPrice.getValue() == null) { lblAvisoPrice.setText("El precio es requerido"); return false; } 
        if(((Number)txtPrice.getValue()).doubleValue() <= 0) { lblAvisoPrice.setText("El precio debe ser mayor a 0"); return false; }
        return true; 
    }
    
    private boolean verificarType() { 
        if(cmbType.getSelectedItem().equals("Seleccionar")) { lblAvisoType.setText("Seleccione un tipo válido"); return false; } 
        return true; 
    }

    private void verificarInstaName() {
        lblAvisoName.setText("");
        if(txtName.getText().trim().equals("")) {
            lblAvisoName.setText("Nombre requerido");
        }
    }
    
    private void eventosCampos(){
        txtName.getDocument().addDocumentListener(crearListener(() -> verificarInstaName()));
        txtPrice.getDocument().addDocumentListener(crearListener(() -> lblAvisoPrice.setText("")));
        
        // Bloquear letras en el campo de texto de nombre si así lo prefieres (opcional)
        txtName.addKeyListener(new KeyAdapter() { 
            public void keyTyped(KeyEvent e) { 
                if(!Character.isLetter(e.getKeyChar()) && !(e.getKeyChar() == ' ') && !Character.isDigit(e.getKeyChar())) e.consume(); 
            } 
        });
    }
    
    private DocumentListener crearListener(Runnable accion) {
        return new DocumentListener() {
            @Override public void removeUpdate(DocumentEvent e) { accion.run(); }
            @Override public void insertUpdate(DocumentEvent e) { accion.run(); }
            @Override public void changedUpdate(DocumentEvent e) { accion.run(); }
        };
    }
    
    public void resetearAvisos() {
        lblAvisoName.setText(""); 
        lblAvisoPrice.setText(""); 
        lblAvisoType.setText(""); 
        lblAvisoSeason.setText("");
    }
    
    // --- MÉTODOS DE DATOS ---
    private void loadData() {
        if(product != null) {
            txtName.setText(product.getName());
            txtPrice.setValue(product.getPrice());
            
            if(product.getProduct_type() != null) cmbType.setSelectedItem(product.getProduct_type());
            
            // Si la temporada viene del JTable mapeada al español
            if(product.getSeason() != null) {
                cmbSeason.setSelectedItem(product.getSeason());
            }
            
            chkActive.setSelected(product.isIs_active());
        } else {
            chkActive.setSelected(true); // Activo por defecto si es producto nuevo
        }
    }
    
    private void save() {
        resetearAvisos();
        boolean valido = true;
        
        if (!verificarName()) valido = false; 
        if (!verificarPrice()) valido = false; 
        if (!verificarType()) valido = false;
        if (!valido) return;
        
        double precio = ((Number) txtPrice.getValue()).doubleValue();
        String tipo = (String) cmbType.getSelectedItem();
        
        // Almacenamos el String seleccionado de la temporada ("Primavera", "Verano", etc.)
        String temporada = (String) cmbSeason.getSelectedItem();
        boolean activo = chkActive.isSelected();
        
        if(product == null) {
            product = new Product();
        }
        
        product.setName(txtName.getText().trim());
        product.setPrice(precio);
        product.setProduct_type(tipo);
        product.setSeason(temporada);
        product.setIs_active(activo);
        
        saved = true; 
        dispose();
    }
    
    public boolean isSaved() { return saved; }
    public Product getProduct() { return product; }

	public JComboBox<String> getCmbType() {
		return cmbType;
	}

	public void setCmbType(JComboBox<String> cmbType) {
		this.cmbType = cmbType;
	}
    
    
}

