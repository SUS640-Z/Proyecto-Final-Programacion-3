package components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class LblAviso extends JLabel {

    public LblAviso(String mensaje) {
    	  super(mensaje);
          setFont(new Font("Times New Roman", Font.PLAIN, 16));
          setForeground(new Color(195, 136, 93));
    }
}
