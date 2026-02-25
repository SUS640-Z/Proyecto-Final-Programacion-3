package views;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaBienvenida extends JFrame {
	
	public VentanaBienvenida() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setTitle("Saturnbucks.com");
        setBounds(200, 100, 640, 640); 
        setResizable(false);
        setLocationRelativeTo(null); 

        inicializarComponentes();
        setVisible(true);
    }

    public void inicializarComponentes() {

        JPanel panelFondo = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g); 
                Graphics2D g2 = (Graphics2D) g; 
                
                try {
                    Image image = ImageIO.read(new File("src/img/saturnbucksBienvenida.jpg"));
                    g2.drawImage(image, 0, 0, getWidth(), getHeight(), null); 
                } catch (IOException e) {
                    System.out.println("Image not found.");
                }
            }
        };

        panelFondo.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("SATURNBUCKS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 30));
        lblTitulo.setForeground(Color.WHITE); 
        panelFondo.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

        panelCampos.setOpaque(false); 
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        for (int i = 0; i < 5; i++) {
            JLabel lbl = new JLabel("Campo " + (i + 1));
            lbl.setForeground(Color.WHITE);
            panelCampos.add(lbl);
            
            JTextField txt = new JTextField(20);
            panelCampos.add(txt);
        }

        JScrollPane scroll = new JScrollPane(panelCampos);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false); 
        
        panelFondo.add(scroll, BorderLayout.CENTER);

        add(panelFondo);
    }
}