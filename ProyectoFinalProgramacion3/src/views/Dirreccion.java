package views;
import java.awt.BorderLayout; // Importación añadida para mantener el diseño intacto
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import components.BordePanel;
import components.BtnDirecion;
import components.LblAlertForm;
import components.LblAviso;
import components.LblSubtituloDirreccion;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Dirreccion extends JFrame {

	private BordePanel contentPane;
    private JTextField txtNombre;
    private JTextField txtCalle;
    private JTextField txtCodigoPostal;
    private JTextField txtNumeroTelefono;
    BtnDirecion btnConfirmar; 
    GridBagConstraints c;
    JPanel panelFormulario;
    LblAlertForm lblObs1;
    LblAlertForm lblObs2;
    LblAlertForm lblObs3;
    LblAlertForm lblObs4;
    LblAlertForm lblObs5;
    LblAlertForm lblObs6;
    JComboBox cboxPaises;
    JTextArea txtDetalles;
    BtnDirecion btnConfirmar2;
    
    public Dirreccion() {
    		setTitle("Saturnbucks.direccion");
    		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setBounds(100, 100, 450, 650); //Juan pablo tamaño
        setBounds(100, 100, 450, 720);//Ivan tamaño 
        setResizable(false);
        setLocationRelativeTo(null);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image icono = tk.getImage("src/img/SATURN_BUCKS_51.png");
        setIconImage(icono);
        contentPane = new BordePanel("DATOS DE ENVIO",Color.black);
        contentPane.setBackground(new Color(210, 180, 140));

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border panelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "DATOS DE ENVIO",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.BLACK);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);

        GridBagLayout gbl_contentPane = new GridBagLayout();
        contentPane.setLayout(gbl_contentPane);

        panelFormulario = new JPanel();
        panelFormulario.setOpaque(false);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        contentPane.add(panelFormulario, gbc_panel);

        GridBagLayout gbl_form = new GridBagLayout();
        panelFormulario.setLayout(gbl_form);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        LblSubtituloDirreccion lblTitulo = new LblSubtituloDirreccion("Ingresa tu direccion");
        c.gridy = 0;
        c.insets = new Insets(10, 5, 20, 5);
        panelFormulario.add(lblTitulo, c);

        c.insets = new Insets(2, 5, 0, 5); 
        c.gridy = 1;
        panelFormulario.add(new JLabel("País/Región:"), c);
        
        cboxPaises = new JComboBox();
        cboxPaises.setModel(new DefaultComboBoxModel<>(new String[] {
        	"Seleccionar","Alemania", "Argentina", "Australia", "Brasil", "Canadá", "Chile", "China", 
            "Colombia", "España", "Estados Unidos", "Francia", "Japón", "México", "Perú"
        }));
        JPanel pnlPaises = new JPanel(new BorderLayout());
        pnlPaises.setOpaque(false);
        pnlPaises.add(cboxPaises, BorderLayout.CENTER);
        lblObs1 = new LblAlertForm(" ");
        pnlPaises.add(lblObs1, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 3, 5); 
        c.gridy = 2;
        panelFormulario.add(pnlPaises, c);

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 3;
        panelFormulario.add(new JLabel("Nombre completo:"), c);
        
        txtNombre = new JTextField();
        txtNombre.putClientProperty("JTextField.placeholderText", "Ingresa tus nombres y apellidos");
        JPanel pnlNombre = new JPanel(new BorderLayout());
        pnlNombre.setOpaque(false);
        pnlNombre.add(txtNombre, BorderLayout.CENTER);
        lblObs2 = new LblAlertForm(" ");
        pnlNombre.add(lblObs2, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 3, 5); 
        c.gridy = 4;
        panelFormulario.add(pnlNombre, c);

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 5;
        panelFormulario.add(new JLabel("Direccion (col, calle, num): "), c);
        
        txtCalle = new JTextField();
        txtCalle.putClientProperty("JTextField.placeholderText", "Ingresa tu colonia y calle");
        JPanel pnlCalle = new JPanel(new BorderLayout());
        pnlCalle.setOpaque(false);
        pnlCalle.add(txtCalle, BorderLayout.CENTER);
        lblObs3 = new LblAlertForm(" ");
        pnlCalle.add(lblObs3, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 3, 5); 
        c.gridy = 6;
        panelFormulario.add(pnlCalle, c);

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 7;
        panelFormulario.add(new JLabel("Codigo Postal:"), c);
        
        txtCodigoPostal = new JTextField();
        txtCodigoPostal.putClientProperty("JTextField.placeholderText", "Ingresa tu C.P");
        JPanel pnlCP = new JPanel(new BorderLayout());
        pnlCP.setOpaque(false);
        pnlCP.add(txtCodigoPostal, BorderLayout.CENTER);
        lblObs4 = new LblAlertForm(" ");
        pnlCP.add(lblObs4, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 0, 5); 
        c.gridy = 8;
        panelFormulario.add(pnlCP, c);

        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.gridy = 9;
        c.insets = new Insets(5, 5, 20, 5);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 10;
        panelFormulario.add(new JLabel("Numero de telefono:"), c);
        
        txtNumeroTelefono = new JTextField();
        txtNumeroTelefono.putClientProperty("JTextField.placeholderText", "Ingresa tu numero de telefono");
        JPanel pnlTel = new JPanel(new BorderLayout());
        pnlTel.setOpaque(false);
        pnlTel.add(txtNumeroTelefono, BorderLayout.CENTER);
        lblObs5 = new LblAlertForm(" ");
        pnlTel.add(lblObs5, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 3, 5); 
        c.gridy = 11;
        panelFormulario.add(pnlTel, c);

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 12;
        panelFormulario.add(new JLabel("Instrucciones de entrega:"), c);
        
        txtDetalles = new JTextArea(4, 20);
        JScrollPane scroll = new JScrollPane(txtDetalles);
        JPanel pnlDetalles = new JPanel(new BorderLayout());
        pnlDetalles.setOpaque(false);
        pnlDetalles.add(scroll, BorderLayout.CENTER);
        lblObs6 = new LblAlertForm(" "); 
        pnlDetalles.add(lblObs6, BorderLayout.SOUTH);
        
        c.insets = new Insets(0, 5, 0, 5); 
        c.gridy = 13;
        panelFormulario.add(pnlDetalles, c);

        JCheckBox chckbx = new JCheckBox("Usar como predeterminada");
        chckbx.setOpaque(false);
        c.insets = new Insets(5, 5, 10, 5);
        c.gridy = 14;
        panelFormulario.add(chckbx, c);
        	
        generarBotones();
        

        setVisible(true);
    }
    
    private void generarBotones() {
    	    btnConfirmar = new BtnDirecion("Confirmar Direccion", 20, 3);
        c.gridy = 15;
        c.insets = new Insets(10, 5, 10, 5);
        panelFormulario.add(btnConfirmar, c);
		
        btnConfirmar.addActionListener( e -> validarForm());
        
        btnConfirmar2 = new BtnDirecion("Regresar", 15, 3);
        c.gridy = 16;
        c.insets = new Insets(1, 1, 1, 1);
        panelFormulario.add(btnConfirmar2, c);
        btnConfirmar2.addActionListener(e -> {
			
			int option = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas regresar? Se perderán todos los datos");
			
			if(option == JOptionPane.YES_OPTION) {
				new LoginView();
				dispose();
			}
			
		});

	}
    
	private void validarForm() {
		resetearAvisos();
		boolean valido = true;
		
		
		if(!verificarPais()) {
			valido = false;
		}
		
		if(!verificarNombre()) {
			valido = false;
		}
		
		if(!verificarDirrecion()) {
			valido = false;
		}
		
		if(!verificarCP()) {
			valido = false;
		}
		
		if(!verificarTelefono()) {
			valido = false;
		}
		
		if(!verificarDetails()) {
			valido = false;
		}
		
		if (valido) {
			JOptionPane.showMessageDialog(this, "Exito");
			reenviarLoginView();
		}
		
	}
	
	
	private void reenviarLoginView() {
		new LoginView();
		this.dispose();
	}

	private boolean verificarDetails() {
		if(txtDetalles.getText().trim().equals("")) {
			lblObs6.setText(" ");
			lblObs6.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}

	private boolean verificarTelefono() {
		if(txtNumeroTelefono.getText().trim().equals("")) {
			lblObs5.setText("El telefono es obligatorio");
			lblObs5.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}

	private boolean verificarCP() {
		if(txtCodigoPostal.getText().trim().equals("")) {
			lblObs4.setText("El Código postal es obligatorio");
			lblObs4.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}

	private boolean verificarDirrecion() {
		if(txtCalle.getText().trim().equals("")) {
			lblObs3.setText("*Este campo es obligatorio");
			lblObs3.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}

	private boolean verificarNombre() {
		if(txtNombre.getText().trim().equals("")) {
			lblObs2.setText("Nombre requerido");
			lblObs2.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}

	private boolean verificarPais() {
		if(cboxPaises.getSelectedIndex() == 0) {
			lblObs1.setText("Debes escoger un pais");
			lblObs1.setFont(new Font("Arial", Font.BOLD, 10));
			return false;
		}
		return true;
	}
	private void resetearAvisos() {
	    lblObs1.setText(" ");
	    lblObs2.setText(" ");
	    lblObs3.setText(" ");
	    lblObs4.setText(" ");
	    lblObs5.setText(" ");
	    lblObs6.setText(" ");
	}
}