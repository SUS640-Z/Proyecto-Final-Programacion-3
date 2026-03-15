package views;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


public class InicioView extends JFrame {

    private JPanel contentPane;

    public static void main(String[] args) {
        new InicioView();
    }

    public InicioView() {
        setTitle("Saturnbucks.inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
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
                BorderFactory.createLineBorder(new Color(210, 180, 140), 2), 
                "BIENVENIDO A SATURNBUCKS", 
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(210, 180, 140)); 

        contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        generarMenu();
        generarHistoria();
        
        setVisible(true);
    }

    private void generarMenu() {

        JMenuBar barraMenu = new JMenuBar();
        barraMenu.setBackground(new Color(48, 60, 26)); 

        JMenu menuNavegacion = new JMenu("= = =");
        menuNavegacion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuNavegacion.setFont(new Font("Times New Roman", Font.BOLD, 14));
        menuNavegacion.setForeground(Color.WHITE);
        menuNavegacion.setBorder(new LineBorder(Color.GRAY, 3, true));

        JMenuItem itemLogin = new JMenuItem("Iniciar Sesión");
        itemLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuItem itemDireccion = new JMenuItem("Ordena aqui");
        itemDireccion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        itemLogin.addActionListener(e -> {
            new LoginView().setVisible(true); 
            dispose(); 
        });

        itemDireccion.addActionListener(e -> {
            new Dirreccion().setVisible(true);
            dispose();
        });

        menuNavegacion.add(itemLogin);
        menuNavegacion.addSeparator(); 
        menuNavegacion.add(itemDireccion);

        barraMenu.add(menuNavegacion);
        setJMenuBar(barraMenu);
    }

    private void generarHistoria() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;

        c.gridy = 0;
        c.insets = new Insets(10, 20, 10, 20);
        JLabel lblTitulo = new JLabel("Nuestra Historia");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(lblTitulo, c);

        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0; 
        c.insets = new Insets(10, 30, 30, 30);
        
        JTextArea txtHistoria = new JTextArea();
        txtHistoria.setText(
            "Saturnbucks nació de una idea sencilla: llevar el mejor café de la galaxia a tus manos.\n\n" +
            "Fundada desde La Paz B.C.S con pasión por el grano perfecto cafe. Cada taza es una órbita de sabor, y " +
            "cada cliente es el centro de nuestro universo.\n\n" +
            "¡Únete a nuestra tripulación y descubre el sabor del cosmos!"
        );

        txtHistoria.setEditable(false);
        txtHistoria.setOpaque(false);
        txtHistoria.setForeground(Color.WHITE);
        txtHistoria.setFont(new Font("Arial", Font.PLAIN, 16));
        txtHistoria.setLineWrap(true);       
        txtHistoria.setWrapStyleWord(true);  

        contentPane.add(txtHistoria, c);
    }
}