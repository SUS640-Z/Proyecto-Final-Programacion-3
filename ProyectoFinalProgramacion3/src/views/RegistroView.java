package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JComponent;
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
    private JTextField txtName;
    private JTextField txtLastName;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    private LblAviso lblAvisoName;
    private LblAviso lblAvisoLastName;
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 350, 645);
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
        aplicarEventoFocus();
        resetearAvisos();
        eventoCerradoVentana();
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
    
	private void eventoCerradoVentana() {
    		addWindowListener(new WindowAdapter() {
    			public void windowClosing(WindowEvent e) {
    				int opcion = JOptionPane.showConfirmDialog(
    		                 null,
    		                 "¿Seguro que deseas cerrar la aplicación?",
    		                 "Confirmar salida",
    		                 JOptionPane.YES_NO_OPTION
    		         );

    		         if(opcion == JOptionPane.YES_OPTION){
    		             dispose(); 
    		         }
    	        }
    		});
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
        eventosCampos();
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

        JButton btnRegistrar = new JButton("Registrarme");
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
        

        btnRegistrar.addActionListener(e -> validarForm());
        
        panel.add(btnRegistrar, c);
        
        JLabel lblRegresar = new JLabel("<html><u>Regresar</u></html>");
        lblRegresar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblRegresar.setForeground(Color.WHITE);
        lblRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegresar.setAlignmentX(JLabel.CENTER);
        
        c.gridy = 17;
        c.insets = new Insets(1, 1, 1, 1);
        contentPane.add(lblRegresar, c);
        
        lblRegresar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que deseas regresar?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION
                );

                if(opcion == JOptionPane.YES_OPTION){
                    dispose(); 
                    LoginView ventana = new LoginView();
                }
            }
            
            public void mouseEntered(MouseEvent e){
            		lblRegresar.setForeground(new Color(204, 207, 198));
            }
        
        		public void mouseExited(MouseEvent e){
        			lblRegresar.setForeground(Color.WHITE);
        		}
        });

    }

    

    private void validarForm() {
        resetearAvisos();
        
        boolean valido = true;
        
        if (!verificarName()) {
            valido = false; 
        }
        
        if (!verificarLastName()) {
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
        lblAvisoName.setText("");
        lblAvisoCorreo.setText("");
        lblAvisoContra.setText("");
        lblAvisoConfirmar.setText("");
    }
    
    private boolean verificarName() {
        if(txtName.getText().trim().equals("")) {
            lblAvisoName.setText("Nombre requerido");
            lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private boolean verificarLastName() {
        if(txtLastName.getText().trim().equals("")) {
            lblAvisoLastName.setText("Apellido requerido");
            lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
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
        if(new String(txtConfirmarContrasena.getPassword()).trim().equals("") && new String(txtConfirmarContrasena.getPassword()).equals(new String(txtContrasena.getPassword()))){
            lblAvisoConfirmar.setText("Confirme su contrasena");
            lblAvisoConfirmar.setFont(new Font("Arial", Font.ITALIC, 10));
            return false;
        }
        return true;
    }
    
    private void eventosCampos(){
    		txtName.getDocument().addDocumentListener(new DocumentListener() {
    			@Override
    			public void removeUpdate(DocumentEvent e) {
    				verificarInstaName();
    			}
    			
    			@Override
    			public void insertUpdate(DocumentEvent e) {
    				verificarInstaName();
    			}
    			
    			@Override
    			public void changedUpdate(DocumentEvent e) {
    				verificarInstaName();
    			}
    		});
    		
    		txtLastName.getDocument().addDocumentListener(new DocumentListener() {
    			@Override
    			public void removeUpdate(DocumentEvent e) {
    				verificarInstaLastName();
    			}
    			
    			@Override
    			public void insertUpdate(DocumentEvent e) {
    				verificarInstaLastName();
    			}
    			
    			@Override
    			public void changedUpdate(DocumentEvent e) {
    				verificarInstaLastName();
    			}
    		});

    		
        txtCorreo.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarInstaCorreo();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarInstaCorreo();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarInstaCorreo();
			}
		});
        
        txtContrasena.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarInstaPassword();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarInstaPassword();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarInstaPassword();
			}
		});
        
        txtConfirmarContrasena.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarInstaConfiPassword();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarInstaConfiPassword();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarInstaConfiPassword();
			}
		});
    }
    
    private void verificarInstaName() {
    		lblAvisoName.setText("");
        if(txtName.getText().trim().equals("")) {
            lblAvisoName.setText("Nombres requerido");
            lblAvisoName.setFont(new Font("Arial", Font.ITALIC, 10));
        }else {
        	if(txtName.getText().matches(".*\\d.*")) {
      			 lblAvisoName.setText("No debe contener numeros");
      	}
       		
        		txtName.addKeyListener(new KeyAdapter() {
        			public void keyTyped(KeyEvent e) {
        				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
        					e.consume();
        				}
        				
        				if(txtName.getText().length() > 25) {
        					lblAvisoName.setText("No puedes tener mas 25 caractereres");
        					e.consume();
        				}
        				
        				char c = e.getKeyChar();
        				
        				if(Character.isLowerCase(c)) {
        					e.setKeyChar(Character.toUpperCase(c));
        				}
        			}
        		
        		});
        		
        }

    }
    
    private void verificarInstaLastName() {
		lblAvisoLastName.setText("");
	    if(txtLastName.getText().trim().equals("")) {
	        lblAvisoLastName.setText("Apellidos requerido");
	        lblAvisoLastName.setFont(new Font("Arial", Font.ITALIC, 10));
	    }else {
    		
    		if(txtLastName.getText().matches(".*\\d.*")) {
   			 lblAvisoLastName.setText("No debe contener numeros");
   		}
    		
    		txtLastName.addKeyListener(new KeyAdapter() {
    			public void keyTyped(KeyEvent e) {
    				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
    					e.consume();
    				}
    				
    				if(txtLastName.getText().length() > 25) {
    					lblAvisoLastName.setText("No puedes tener mas 25 caractereres");
    					e.consume();
    				}
    				
    				char c = e.getKeyChar();
    				
    				if(Character.isLowerCase(c)) {
    					e.setKeyChar(Character.toUpperCase(c));
    				}
    			}
    		});
    		
    }
}
    
    private void verificarInstaCorreo() {
	    	lblAvisoCorreo.setText(" ");
	    	if(txtCorreo.getText().trim().equals("") ) {
	            lblAvisoCorreo.setText("Correo requerido");
	            lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
	    	}
	    	if(!txtCorreo.getText().contains("@")) {
	            lblAvisoCorreo.setText("Correo Invalido");
	            lblAvisoCorreo.setFont(new Font("Arial", Font.ITALIC, 10));
	    	}
	    	
	    	txtCorreo.addKeyListener(new KeyAdapter() {
    			public void keyTyped(KeyEvent e) {
    				if(e.getKeyChar() == ' ') {
    					e.consume();
    				}
    				
    				if(txtCorreo.getText().length() > 50) {
    					lblAvisoCorreo.setText("No puedes tener mas 50 caractereres");
    					e.consume();
    				}
    				
    				char c = e.getKeyChar();
    				
    				if(Character.isLowerCase(c)) {
    					e.setKeyChar(Character.toUpperCase(c));
    				}
    			}
    		
    		});
    }
    
    private void verificarInstaPassword() {
    		boolean mayuscula=false;
    		boolean numeros=false;
    		boolean longitud=false;
	    	lblAvisoContra.setText(" ");
	    	if(new String(txtContrasena.getPassword()).trim().equals("")) {
	            lblAvisoContra.setText("Contrasena requerida");
	            lblAvisoContra.setFont(new Font("Arial", Font.ITALIC, 10));
	    }else {
		   for(int i = 0; i < new String(txtContrasena.getPassword()).length(); i++) {
			   if (Character.isUpperCase(new String(txtContrasena.getPassword()).charAt(i))) {
		            mayuscula=true; 
		        }
			   
			   if(new String(txtContrasena.getPassword()).matches(".*\\d.*")) {
				   numeros=true;
			   }
			   if(new String(txtContrasena.getPassword()).trim().length() >= 8 ) {
				   longitud=true;
      		}
		   }
		   
		   if(!mayuscula) {
			   lblAvisoContra.setText("Se necesita al menos una mayuscula");
		   }
		   
		   if(!numeros) {
			   lblAvisoContra.setText("Se necesita al menos un numero");
		   }
		   
		   if(!longitud) {
			   lblAvisoContra.setText("Debe contener almenos 8 caracteres");
		   }
	    }
	    	
	    		txtContrasena.addKeyListener(new KeyAdapter() {
    			public void keyTyped(KeyEvent e) {
    				if(e.getKeyChar() == ' ') {
    					e.consume();
    				}
    				
    				if((new String(txtContrasena.getPassword())).length() > 20) {
    					lblAvisoContra.setText("No puedes tener mas 20 caractereres");
    					e.consume();
    				}
    				
    				char c = e.getKeyChar();
    				
    				if(Character.isLowerCase(c)) {
    					e.setKeyChar(Character.toUpperCase(c));
    				}
    			}
    		
    		});
	    	
    }
    
    private void verificarInstaConfiPassword() {
    		lblAvisoConfirmar.setText(" ");
	    	if(new String(txtConfirmarContrasena.getPassword()).trim().equals("") || !(new String(txtConfirmarContrasena.getPassword()).equals(new String(txtContrasena.getPassword())))){
	            lblAvisoConfirmar.setText("Las contraseñas no son iguales");
	            lblAvisoConfirmar.setFont(new Font("Arial", Font.ITALIC, 10));  
	     }
    }
    

}