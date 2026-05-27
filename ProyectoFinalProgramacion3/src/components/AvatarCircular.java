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
    private Color fondoAzul = new Color(30, 144, 255); // Azul Dodgerr o el azul que prefieras

    // Constructor para cuando NO hay imagen (solo usa el nombre)
    public AvatarCircular(String nombre, int tamaño) {
        this.nombre = nombre;
        this.imagenPerfil = null;
        Dimension dim = new Dimension(tamaño, tamaño);
        setPreferredSize(dim);
        setSize(dim);
    }

    // Constructor para cuando SÍ hay una imagen de perfil
    public AvatarCircular(Image imagenPerfil, int tamaño) {
        this.nombre = "";
        this.imagenPerfil = imagenPerfil;
        Dimension dim = new Dimension(tamaño, tamaño);
        setPreferredSize(dim);
        setSize(dim);
    }

    // Método para cambiar la imagen dinámicamente si el usuario sube una después
    public void setImagenPerfil(Image imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
        repaint(); // Forzar a Swing a redibujar el componente
    }

    // Método para cambiar el nombre dinámicamente
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

        // CASO 1: Si hay una imagen, la recortamos y dibujamos en círculo
        if (imagenPerfil != null) {
            // Creamos una máscara circular para recortar la imagen
            g2.setClip(new Ellipse2D.Float(x, y, diametro, diametro));
            // Dibujamos la imagen estirada al tamaño del componente
            g2.drawImage(imagenPerfil, x, y, diametro, diametro, null);
        } 
        // CASO 2: Si no hay imagen, dibujamos el fondo azul con la letra inicial
        else {
            // Dibujar el círculo de fondo
            g2.setColor(fondoAzul);
            g2.fillOval(x, y, diametro, diametro);

            // Obtener la primera letra (en mayúscula)
            String inicial = " ";
            if (nombre != null && !nombre.trim().isEmpty()) {
                inicial = nombre.trim().substring(0, 1).toUpperCase();
            }

            // Configurar la fuente (adaptable automáticamente al tamaño del avatar)
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, diametro / 2));

            // Centrar la letra perfectamente en el eje X y Y del círculo
            FontMetrics fm = g2.getFontMetrics();
            int letraX = x + (diametro - fm.stringWidth(inicial)) / 2;
            int letraY = y + ((diametro - fm.getHeight()) / 2) + fm.getAscent();
            
            g2.drawString(inicial, letraX, letraY);
        }

        g2.dispose();
    }
}