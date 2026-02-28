package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class BtnDirecion extends JButton{
	public BtnDirecion(String mensaje,int tamanioBoton,int tamanioborde) {
		super(mensaje);
		setFont(new Font("Times New Roman", Font.PLAIN, tamanioBoton));
		setBackground(new Color(48, 60, 26));
		setForeground(Color.WHITE);
		setBorder(new LineBorder(Color.GRAY, tamanioborde, true));
	}
}
