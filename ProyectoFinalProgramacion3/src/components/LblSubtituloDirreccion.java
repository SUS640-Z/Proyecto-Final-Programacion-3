package components;

import java.awt.Font;

import javax.swing.JLabel;

public class LblSubtituloDirreccion extends JLabel{
	
	public LblSubtituloDirreccion(String mensaje){
		super(mensaje);
		setFont(new Font("Tahoma", Font.BOLD, 22));
	}
}
