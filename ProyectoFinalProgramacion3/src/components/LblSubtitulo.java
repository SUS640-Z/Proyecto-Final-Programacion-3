package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class LblSubtitulo extends JLabel {
	public LblSubtitulo(String mensaje) {
  	  super(mensaje);
  	  setForeground(Color.WHITE);
  	  setFont(new Font("Times New Roman", Font.PLAIN, 20));
  }
}
