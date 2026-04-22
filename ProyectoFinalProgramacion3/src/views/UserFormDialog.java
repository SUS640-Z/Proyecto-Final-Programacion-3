package views;

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
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import components.BtnDirecion;
import components.LblAviso;
import components.LblSubtitulo;
import controllers.RegistroController;
import models.User;

public class UserFormDialog extends JDialog{
    JPanel contentPane;
    JTextField txtName;
    JTextField txtLastName;
    JTextField txtCorreo;
    JPasswordField txtContrasena;
    JPasswordField txtConfirmarContrasena;
    LblAviso lblAvisoName;
    LblAviso lblAvisoLastName;
    LblAviso lblAvisoCorreo;
    LblAviso lblAvisoContra;
    LblAviso lblAvisoConfirmar;
    BtnDirecion btnConfirmar2;
    JPanel panelFormulario;
    JButton btnRegistrar;
    JLabel lblRegresar;
    
    private User user;
    private boolean saved = false;


    public UserFormDialog(JFrame parent, User user) {
    	
    	super(parent, true);
    	this.user = user;
        setTitle("Saturnbucks.registro");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 350, 645);
        setResizable(false);
        setLocationRelativeTo(null);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

        contentPane = new JPanel();
        contentPane.setBackground(new Color(15, 19, 9)); 

        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border panelTitledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                "REGISTRO", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                Color.WHITE);

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        generarComponentes();
        aplicarEventoFocus();
        resetearAvisos();
        setVisible(true);
    }

    
    	FocusAdapter efectoFocus = new FocusAdapter() {

        Color bordeActivo = new Color(0, 200, 120);   
        Color bordeInactivo = Color.BLACK; 

        @Override
        public void focusGained(FocusEvent e) {
            ((JComponent) e.getSource()).setBorder(
                BorderFactory.createLineBorder(bordeActivo, 2)
            );
        }

        @Override
        public void focusLost(FocusEvent e) {
            ((JComponent) e.getSource()).setBorder(
                BorderFactory.createLineBorder(bordeInactivo, 1)
            );
        }
    };
    
    private void aplicarEventoFocus(){
    		txtName.addFocusListener(efectoFocus);
    		txtLastName.addFocusListener(efectoFocus);
    		txtCorreo.addFocusListener(efectoFocus);
    		txtContrasena.addFocusListener(efectoFocus);
    		txtConfirmarContrasena.addFocusListener(efectoFocus);
    }
    
	
	private void generarComponentes() {
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setOpaque(false); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        contentPane.add(panelFormulario, gbc);

        generarTitulos(panelFormulario);
        generarCampos(panelFormulario);
        generarAvisos(panelFormulario);
        generarBotones(panelFormulario);
    }

    private void generarTitulos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        
        c.gridy = 0;
        c.insets = new Insets(10, 5, 30, 5);
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblTitulo, c);
    }

    private void generarCampos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;


        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 1;
        LblSubtitulo lblName = new LblSubtitulo("Nombre:");
        panel.add(lblName, c);
        
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 2;
        txtName = new JTextField(20);
        panel.add(txtName, c);
        
        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 4;
        LblSubtitulo lblLastName = new LblSubtitulo("Apellidos:");
        panel.add(lblLastName, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 5;
        txtLastName = new JTextField(20);
        panel.add(txtLastName, c);
        


        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 7;
        LblSubtitulo lblCorreo = new LblSubtitulo("Correo electronico:");
        panel.add(lblCorreo, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 8;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, c);


        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 10;
        LblSubtitulo lblContra = new LblSubtitulo("Contrasena:");
        panel.add(lblContra, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 11;
        txtContrasena = new JPasswordField(20);
        panel.add(txtContrasena, c);
        

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 13;
        LblSubtitulo lblConf = new LblSubtitulo("Confirmar contrasena:");
        panel.add(lblConf, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 14;
        txtConfirmarContrasena = new JPasswordField(20);
        panel.add(txtConfirmarContrasena, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 5, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 10);

        lblAvisoName = new LblAviso("");
        lblAvisoName.setForeground(Color.RED); lblAvisoName.setFont(fontAviso);
        c.gridy = 3; panel.add(lblAvisoName, c);

        lblAvisoLastName = new LblAviso("");
        lblAvisoLastName.setForeground(Color.RED); lblAvisoLastName.setFont(fontAviso);
        c.gridy = 6; panel.add(lblAvisoLastName, c);
        
        lblAvisoCorreo = new LblAviso("");
        lblAvisoCorreo.setForeground(Color.RED); lblAvisoCorreo.setFont(fontAviso);
        c.gridy = 9; panel.add(lblAvisoCorreo, c);

        lblAvisoContra = new LblAviso("");
        lblAvisoContra.setForeground(Color.RED); lblAvisoContra.setFont(fontAviso);
        c.gridy = 12; panel.add(lblAvisoContra, c);
        
        lblAvisoConfirmar = new LblAviso("");
        lblAvisoConfirmar.setForeground(Color.RED); lblAvisoConfirmar.setFont(fontAviso);
        c.gridy = 15; panel.add(lblAvisoConfirmar, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 16;
        c.insets = new Insets(20, 5, 10, 5); 

        btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnRegistrar.setBackground(new Color(48, 60, 26)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(new LineBorder(Color.GRAY, 3, true));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
            		btnRegistrar.setBackground(new Color(152, 158, 141));
            }
            
            	public void mouseExited(MouseEvent e){
            		btnRegistrar.setBackground(new Color(48, 60, 26));
            }
        });
       
        panel.add(btnRegistrar, c);
        
        lblRegresar = new JLabel("<html><u>Regresar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 17;
        c.insets = new Insets(1, 1, 1, 1);
        contentPane.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {  
            public void mouseEntered(MouseEvent e){
            		lblRegresar.setForeground(new Color(204, 207, 198));
            }
        
        		public void mouseExited(MouseEvent e){
        			lblRegresar.setForeground(Color.WHITE);
        		}
        });

    }
    
    public void resetearAvisos() {
        lblAvisoName.setText("");
        lblAvisoCorreo.setText("");
        lblAvisoContra.setText("");
        lblAvisoConfirmar.setText("");
    }
 
   
	public JTextField getTxtName() {return txtName;}
	public JTextField getTxtLastName() {return txtLastName;}
	public JTextField getTxtCorreo() {return txtCorreo;}
	public JPasswordField getTxtContrasena() {return txtContrasena;}
	public JPasswordField getTxtConfirmarContrasena() {return txtConfirmarContrasena;}
	public BtnDirecion getBtnConfirmar2() {return btnConfirmar2;}
	public JButton getBtnRegistrar() {return btnRegistrar;}
	public LblAviso getLblAvisoName() {return lblAvisoName;}
	public LblAviso getLblAvisoLastName() {return lblAvisoLastName;}
	public LblAviso getLblAvisoCorreo() {return lblAvisoCorreo;}
	public LblAviso getLblAvisoContra() {return lblAvisoContra;}
	public LblAviso getLblAvisoConfirmar() {return lblAvisoConfirmar;}
	public JLabel getLblRegresar() {return lblRegresar;}
}
