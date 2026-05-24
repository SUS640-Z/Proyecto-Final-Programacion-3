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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import components.BtnDirecion;
import components.LblAviso;
import components.LblSubtitulo;
import models.ProductType;
import models.User;
import utils.PasswordUtils;


public class ProductTypeFormDialog extends JDialog{
	JPanel contentPane;
    JTextField txtName;
    
    LblAviso lblAvisoName;
    
    BtnDirecion btnConfirmar2;
    JPanel panelFormulario;
    JButton btnRegistrar;
    JLabel lblRegresar;
    
    private ProductType productType;
    private boolean saved = false;
    
    
    public ProductTypeFormDialog(JFrame parent, ProductType productType) {
    	super(parent, true);
    	this.productType = productType;
    	setTitle(productType == null ? "Agregar tipos de productos" : "Editar tipo de producto");
    	setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 350, 250); 
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
                productType == null ? "NUEVO TIPO PRODUCTO" : "EDITAR TIPO PRODUCTO", 
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
    		txtName.addFocusListener(efectoFocus);
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

	        c.insets = new Insets(2, 5, 0, 5); c.gridy = 1; panel.add(new LblSubtitulo("Nombre:"), c);
	        c.insets = new Insets(0, 5, 5, 5); c.gridy = 2; txtName = new JTextField(20); panel.add(txtName, c);

	 }
	 
	 private void generarAvisos(JPanel panel) {
	        GridBagConstraints c = new GridBagConstraints();
	        c.fill = GridBagConstraints.HORIZONTAL; c.gridx = 0; c.insets = new Insets(0, 5, 2, 4);
	        Font fontAviso = new Font("Arial", Font.ITALIC, 10); 

	        lblAvisoName = new LblAviso(""); lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso); c.gridy = 3; panel.add(lblAvisoName, c);
	 }
	 
	 private void generarBotones(JPanel panel) {
	        GridBagConstraints c = new GridBagConstraints();
	        c.gridx = 0; c.gridy = 30; c.insets = new Insets(10, 5, 5, 5); 

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
	        
	        c.gridy = 31; c.insets = new Insets(0, 1, 15, 1);
	        panel.add(lblRegresar, c);
	        
	        lblRegresar.addMouseListener(new MouseAdapter() {  
	            public void mouseEntered(MouseEvent e){ lblRegresar.setForeground(new Color(204, 207, 198)); }
	        	public void mouseExited(MouseEvent e){ lblRegresar.setForeground(Color.WHITE); }
	        	public void mouseClicked(MouseEvent e){ dispose(); }
	        });
	        
	        btnRegistrar.addActionListener(e -> save());
	 }
	 
	 private boolean verificarName() { if(txtName.getText().trim().equals("")) { lblAvisoName.setText("Requerido"); return false; } return true; }
	 private void verificarInstaName() {
			lblAvisoName.setText("");
			if(txtName.getText().trim().equals("")) {
				lblAvisoName.setText("Nombres requerido");
				lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
			} else if(txtName.getText().matches(".*\\d.*")) {
				lblAvisoName.setText("No debe contener números");
				lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
			}
	 }
	 
	 private void eventosCampos(){
		  txtName.getDocument().addDocumentListener(crearListener(() -> verificarInstaName()));
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
	}
	
    private void loadData() {
    	if(productType != null) {
    		txtName.setText(productType.getName());   
    	}
    }
    
    
    private void save() {
    	resetearAvisos();
        boolean valido = true;
        if (!verificarName()) valido = false; 
        if (!valido) return;
    	
    	
        if(productType == null) {
        	productType = new ProductType(txtName.getText().trim());
        }else {
        	productType.setName(txtName.getText().trim());
        }
        saved = true; dispose();
    }
    
    public boolean isSaved() { return saved; }
    public ProductType getProductType() { return productType; }

}
