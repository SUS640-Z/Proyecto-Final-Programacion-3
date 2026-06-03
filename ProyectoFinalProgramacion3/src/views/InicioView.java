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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import components.AvatarCircular;
import controllers.LoginController;
import controllers.MenuController;
//import controllers.RegistroController;
import utils.Session;
import models.User;

public class InicioView extends JFrame {

    private JPanel contentPane;
    //private User loggedUser; 


    public static void main(String[] args) {
        new InicioView(null);
    }

    public InicioView() {
        this(null);
    }

    public InicioView(User loggedUser) {
        //this.loggedUser = loggedUser;
        
        setTitle("Saturnbucks - Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 525);
        setResizable(false);
        setLocationRelativeTo(null);
        
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image icono = tk.getImage("src/assets/img/SATURN_BUCKS_51.png");
            setIconImage(icono);
        } catch (Exception e) {}

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);
		
		
		
        if (Session.getCurrentUser() != null && "Cliente".equals(Session.getRol())) {
        	generarMenu();
        } else {
        	generarMenuPersonalizado();
        }


        generarContenidoPagina();
        generarFooter(); 
        
        setVisible(true);
    }

    private void generarMenuPersonalizado() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9)); 
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26))); 

        JLabel lblLogin = crearItemMenu("Iniciar Sesión");
        JLabel lblRegistro = crearItemMenu("Crear Cuenta");
        //JLabel lblDireccion = crearItemMenu("Ordena Aquí");
        
        JLabel lblSeparador1 = new JLabel("  |  ");
        lblSeparador1.setForeground(Color.DARK_GRAY);


        lblLogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            	new LoginController(new LoginWindow().getLoginView());
                dispose(); 
            }
        });

        lblRegistro.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                RegistroView ventanaRegistro = new RegistroView();
                new controllers.RegistroController(ventanaRegistro); 
                dispose(); 
            }
        });
        
        
        panelMenu.add(lblLogin);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblRegistro);
   

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

    private void generarContenidoPagina() {
        JPanel panelCentral = new JPanel(new GridBagLayout());
        panelCentral.setBackground(new Color(15, 19, 9)); 

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

       
        c.gridy = 0;
        c.insets = new Insets(30, 20, 10, 20); 
        JLabel lblBienvenida = new JLabel("BIENVENIDO A SATURNBUCKS");
        lblBienvenida.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblBienvenida.setForeground(new Color(210, 180, 140)); 
        lblBienvenida.setHorizontalAlignment(JLabel.CENTER);
        panelCentral.add(lblBienvenida, c);

       
        c.gridy = 1;
        c.insets = new Insets(0, 20, 30, 20);
        JLabel lblSlogan = new JLabel("El mejor café de la galaxia.");
        lblSlogan.setFont(new Font("Arial", Font.ITALIC, 16));
        lblSlogan.setForeground(Color.LIGHT_GRAY);
        lblSlogan.setHorizontalAlignment(JLabel.CENTER);
        panelCentral.add(lblSlogan, c);

      
        c.gridy = 2;
        c.insets = new Insets(10, 20, 10, 20);
        JLabel lblTituloHistoria = new JLabel("Nuestra Historia");
        lblTituloHistoria.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblTituloHistoria.setForeground(Color.WHITE);
        lblTituloHistoria.setHorizontalAlignment(JLabel.CENTER);
        panelCentral.add(lblTituloHistoria, c);

       
        c.gridy = 3;
        c.insets = new Insets(10, 40, 30, 40); 
        JLabel txtHistoria = new JLabel(
            "<html><div style='text-align: center;'>" +
            "Fundada desde La Paz B.C.S con pasión por el grano perfecto.<br>" +
            "Cada taza es una órbita de sabor, y cada cliente es el centro de nuestro universo.<br><br>" +
            "¡Únete a nuestra tripulación y descubre el sabor del cosmos!<br>" +
            "Preparamos cada bebida cuidando la temperatura, el tueste y la esencia de las estrellas." +
            "</div></html>"
        );
        txtHistoria.setForeground(Color.WHITE);
        txtHistoria.setFont(new Font("Arial", Font.PLAIN, 15));
        txtHistoria.setHorizontalAlignment(JLabel.CENTER);
        panelCentral.add(txtHistoria, c);
        
      
        if (Session.getCurrentUser() != null && "Cliente".equals(Session.getRol())) {
            c.gridy = 4;
          
            c.fill = GridBagConstraints.NONE; 
    
            c.insets = new Insets(10, 40, 40, 40); 
            
            JButton btnOrdenar = new JButton("Ordenar");
            btnOrdenar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
            btnOrdenar.setBackground(new Color(48, 60, 26)); 
            btnOrdenar.setForeground(Color.WHITE);
            btnOrdenar.setBorder(new LineBorder(Color.GRAY, 3, true));
            btnOrdenar.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnOrdenar.putClientProperty("btnOrdenar.buttonType", "toolBarButton"); 
            
            btnOrdenar.addActionListener(e -> {
            	dispose();
            	MenuView menuVista = new MenuView();;
                MenuController control = new MenuController(menuVista); 
            });
            
           
            btnOrdenar.setPreferredSize(new java.awt.Dimension(250, 45));
            
           
            panelCentral.add(btnOrdenar, c);
        }

        contentPane.add(panelCentral, BorderLayout.CENTER);
    }
    private void generarFooter() {
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(15, 19, 9));
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        JLabel lblFooter = new JLabel("© 2026 Saturnbucks Co. | La Paz, B.C.S.");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(JLabel.CENTER);

        panelFooter.add(lblFooter);

        contentPane.add(panelFooter, BorderLayout.SOUTH);
    }
 
    
    private void generarMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9)); 
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26))); 

        JLabel lblPerfil = crearItemMenu("Mi perfil");
        JLabel lblOrdenes= crearItemMenu("Ordenes");
        JLabel lblCerrar = crearItemMenu("Cerrar Sesion");
        
        JLabel lblSeparador1 = new JLabel("  |  ");
        lblSeparador1.setForeground(Color.DARK_GRAY);
        JLabel lblSeparador2 = new JLabel("  |  ");
        lblSeparador2.setForeground(Color.DARK_GRAY);

        panelMenu.add(lblPerfil);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblOrdenes);
        panelMenu.add(lblSeparador2);
        panelMenu.add(lblCerrar);
        
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
            	
            	JOptionPane.showMessageDialog(
                          null, 
                          "Sesión cerrada exitosamente", 
                          "Cierre de Sesión", 
                         JOptionPane.INFORMATION_MESSAGE
                );
            	
                dispose();
                Session.logout();
                
                
                InicioView ey = new InicioView();
                
            }
        });
        

        contentPane.add(panelMenu, BorderLayout.NORTH);
    }
    
}