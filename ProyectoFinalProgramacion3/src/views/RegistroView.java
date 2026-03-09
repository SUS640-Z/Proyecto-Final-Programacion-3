package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFrame;

import components.BtnDirecion;
import components.LblAviso;
import components.LblSubtitulo;

public class RegistroView extends JFrame {

    private JPanel contentPane;
    private JTextField txtUsuario;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    private LblAviso lblAvisoUsuario;
    private LblAviso lblAvisoCorreo;
    private LblAviso lblAvisoContra;
    private LblAviso lblAvisoConfirmar;
    BtnDirecion btnConfirmar2;
    JPanel panelFormulario;

    public static void main(String[] args) {
        new RegistroView();
    }

    public RegistroView() {
        setTitle("Saturnbucks.registro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 350, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/img/SATURN_BUCKS_51.png");
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

        resetearAvisos();
        
        setVisible(true);
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
        LblSubtitulo lblUser = new LblSubtitulo("Usuario:");
        lblUser.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblUser.setForeground(Color.WHITE);
        panel.add(lblUser, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 2;
        txtUsuario = new JTextField(20);
        panel.add(txtUsuario, c);


        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 4;
        LblSubtitulo lblCorreo = new LblSubtitulo("Correo electronico:");
        lblCorreo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblCorreo.setForeground(Color.WHITE);
        panel.add(lblCorreo, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 5;
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo, c);


        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 7;
        LblSubtitulo lblContra = new LblSubtitulo("Contrasena:");
        lblContra.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblContra.setForeground(Color.WHITE);
        panel.add(lblContra, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 8;
        txtContrasena = new JPasswordField(20);
        panel.add(txtContrasena, c);
        

        c.insets = new Insets(2, 5, 0, 5);
        c.gridy = 10;
        LblSubtitulo lblConf = new LblSubtitulo("Confirmar contrasena:");
        lblConf.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblConf.setForeground(Color.WHITE);
        panel.add(lblConf, c);
        
        c.insets = new Insets(0, 5, 15, 5);
        c.gridy = 11;
        txtConfirmarContrasena = new JPasswordField(20);
        panel.add(txtConfirmarContrasena, c);
    }

    private void generarAvisos(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.insets = new Insets(0, 5, 5, 4);
        Font fontAviso = new Font("Arial", Font.ITALIC, 10);

        lblAvisoUsuario = new LblAviso("");
        lblAvisoUsuario.setForeground(Color.RED); lblAvisoUsuario.setFont(fontAviso);
        c.gridy = 3; panel.add(lblAvisoUsuario, c);

        lblAvisoCorreo = new LblAviso("");
        lblAvisoCorreo.setForeground(Color.RED); lblAvisoCorreo.setFont(fontAviso);
        c.gridy = 6; panel.add(lblAvisoCorreo, c);

        lblAvisoContra = new LblAviso("");
        lblAvisoContra.setForeground(Color.RED); lblAvisoContra.setFont(fontAviso);
        c.gridy = 9; panel.add(lblAvisoContra, c);
        
        lblAvisoConfirmar = new LblAviso("");
        lblAvisoConfirmar.setForeground(Color.RED); lblAvisoConfirmar.setFont(fontAviso);
        c.gridy = 12; panel.add(lblAvisoConfirmar, c);
    }

    private void generarBotones(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 13;
        c.insets = new Insets(20, 5, 10, 5); 

        JButton btnRegistrar = new JButton("Registrarme");
        btnRegistrar.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnRegistrar.setBackground(new Color(48, 60, 26)); 
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBorder(new LineBorder(Color.GRAY, 3, true));
        

        btnRegistrar.addActionListener(e -> validarForm());
        
        panel.add(btnRegistrar, c);
        
        JLabel lblRegresar = new JLabel("<html><u>Regresar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 16;
        c.insets = new Insets(1, 1, 1, 1);
        contentPane.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	int option = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas regresar? Se perderán todos los datos");
    			
    			if(option == JOptionPane.YES_OPTION) {
    				new LoginView();
    				dispose();
    			} 
            }
        });

    }



    private void validarForm() {
        resetearAvisos();
        
        boolean valido = true;
        
        if (!verificarUsuario()) {
            valido = false; 
        }
        
        if (!verificarCorreo()) {
            valido = false;
        }
        
        if (!verificarPassword()) {
            valido = false;
        }
        
        if (!verificarConfirmarPassword()) {
            valido = false;
        }
        
        if (valido) {
            JOptionPane.showMessageDialog(this, "Cuenta registrada");
        }
    }
    
    private void resetearAvisos() {
        lblAvisoUsuario.setText("");
        lblAvisoCorreo.setText("");
        lblAvisoContra.setText("");
        lblAvisoConfirmar.setText("");
    }
    
    private boolean verificarUsuario() {
        if(txtUsuario.getText().trim().equals("")) {
            lblAvisoUsuario.setText("Usuario requerido");
            lblAvisoUsuario.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }

    private boolean verificarCorreo() {
        if(txtCorreo.getText().trim().equals("")) {
            lblAvisoCorreo.setText("Correo requerido");
            lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private boolean verificarPassword() {
        if(new String(txtContrasena.getPassword()).trim().equals("")) {
            lblAvisoContra.setText("Contrasena requerida");
            lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }

    private boolean verificarConfirmarPassword() {
        if(new String(txtConfirmarContrasena.getPassword()).trim().equals("")) {
            lblAvisoConfirmar.setText("Confirme su contrasena");
            lblAvisoConfirmar.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
}