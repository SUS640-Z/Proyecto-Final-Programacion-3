package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

public class AvatarCircular extends JComponent {

    private String nombre;
    private Image imagenPerfil;
    private Color fondoSaturn = new Color(48, 60, 26); 

    public AvatarCircular(String nombre, int tamano) {
        this.nombre = nombre;
        this.imagenPerfil = null;
        Dimension dim = new Dimension(tamano, tamano);
        setPreferredSize(dim);
        setSize(dim);
    }

    public AvatarCircular(Image imagenPerfil, int tamano) {
        this.nombre = "";
        this.imagenPerfil = imagenPerfil;
        Dimension dim = new Dimension(tamano, tamano);
        setPreferredSize(dim);
        setSize(dim);
    }

    public void setImagenPerfil(Image imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
        repaint(); 
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int diametro = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;

        if (imagenPerfil != null) {
            g2.setClip(new Ellipse2D.Float(x, y, diametro, diametro));
            g2.drawImage(imagenPerfil, x, y, diametro, diametro, null);
        } else {
            g2.setColor(fondoSaturn);
            g2.fillOval(x, y, diametro, diametro);

            String inicial = " ";
            if (nombre != null && !nombre.trim().isEmpty()) {
                inicial = nombre.trim().substring(0, 1).toUpperCase();
            }

            g2.setColor(new Color(210, 180, 140)); 
            g2.setFont(new Font("Times New Roman", Font.BOLD, diametro / 2));

            FontMetrics fm = g2.getFontMetrics();
            int letraX = x + (diametro - fm.stringWidth(inicial)) / 2;
            int letraY = y + ((diametro - fm.getHeight()) / 2) + fm.getAscent();
            
            g2.drawString(inicial, letraX, letraY);
        }

        g2.dispose();
    }
}