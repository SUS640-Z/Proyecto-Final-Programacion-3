package components;

import javax.swing.*;
import java.awt.*;

public class IconoFlechaIzquierda implements Icon {
    private int width = 24;
    private int height = 24;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
    	Graphics2D g2 = (Graphics2D) g.create();
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	
    	if (c instanceof AbstractButton) {
            AbstractButton boton = (AbstractButton) c;
            if (boton.getModel().isRollover()) {
                // Un verdecito translúcido (R: 40, G: 180, B: 100, Alfa/Opacidad: 60)
                g2.setColor(new Color(40, 180, 100, 60)); 
                
                // Dibujamos un fondo circular suave alrededor de la flecha
                g2.fillOval(x, y, width, height); 
            }
        }
    	g2.setColor(Color.WHITE); 
    	g2.setStroke(new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

    	// Dibujar la flecha (<--)
    	g2.drawLine(x + 5, y + 12, x + 19, y + 12);
    	g2.drawLine(x + 5, y + 12, x + 12, y + 5);  
    	g2.drawLine(x + 5, y + 12, x + 12, y + 19); 
    	g2.dispose();
    }

    @Override public int getIconWidth() { return width; }
    @Override public int getIconHeight() { return height; }
}