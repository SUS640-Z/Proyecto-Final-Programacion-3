package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;

public class GribBagLayout extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GribBagLayout frame = new GribBagLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GribBagLayout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 694, 630);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		GridBagLayout gbl_contentPane = new GridBagLayout();

		gbl_contentPane.columnWidths = new int[]{0, 0}; 
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0}; 
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE}; 
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panel, gbc_panel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Green Coffe");
		lblNewLabel_3.setBackground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		panel.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		contentPane.add(panel_1, gbc_panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Iniciar Sesion");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 40));
		panel_1.add(lblNewLabel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		contentPane.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JPanel panel_4_1_1 = new JPanel();
		panel_4_1_1.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_4_1_1 = new GridBagConstraints();
		gbc_panel_4_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4_1_1.gridx = 0;
		gbc_panel_4_1_1.gridy = 0;
		panel_2.add(panel_4_1_1, gbc_panel_4_1_1);
		GridBagLayout gbl_panel_4_1_1 = new GridBagLayout();
		gbl_panel_4_1_1.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel_4_1_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_4_1_1.columnWeights = new double[]{0.0, 1.0, 4.9E-324, 0.0, Double.MIN_VALUE};
		gbl_panel_4_1_1.rowWeights = new double[]{0.0, 0.0, 4.9E-324, Double.MIN_VALUE};
		panel_4_1_1.setLayout(gbl_panel_4_1_1);
		
		JLabel lblNewLabel_4_1_1 = new JLabel("Usuario");
		lblNewLabel_4_1_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_4_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_4_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4_1_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_4_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1_1.gridx = 0;
		gbc_lblNewLabel_4_1_1.gridy = 0;
		panel_4_1_1.add(lblNewLabel_4_1_1, gbc_lblNewLabel_4_1_1);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField.setColumns(25);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_4_1_1.add(textField, gbc_textField);
		
		JLabel lblNewLabel_5_1_1 = new JLabel("Usuario Requerido");
		lblNewLabel_5_1_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_1_1.setBackground(new Color(255, 255, 255));
		lblNewLabel_5_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_5_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_1_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5_1_1.gridwidth = 2;
		gbc_lblNewLabel_5_1_1.gridx = 2;
		gbc_lblNewLabel_5_1_1.gridy = 2;
		panel_4_1_1.add(lblNewLabel_5_1_1, gbc_lblNewLabel_5_1_1);
		
		JPanel panel_2_1 = new JPanel();
		panel_2_1.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_2_1 = new GridBagConstraints();
		gbc_panel_2_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2_1.fill = GridBagConstraints.BOTH;
		gbc_panel_2_1.gridx = 0;
		gbc_panel_2_1.gridy = 3;
		contentPane.add(panel_2_1, gbc_panel_2_1);
		GridBagLayout gbl_panel_2_1 = new GridBagLayout();
		gbl_panel_2_1.columnWidths = new int[]{0, 0};
		gbl_panel_2_1.rowHeights = new int[]{0, 0};
		gbl_panel_2_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_2_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2_1.setLayout(gbl_panel_2_1);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_4_1 = new GridBagConstraints();
		gbc_panel_4_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4_1.gridx = 0;
		gbc_panel_4_1.gridy = 0;
		panel_2_1.add(panel_4_1, gbc_panel_4_1);
		GridBagLayout gbl_panel_4_1 = new GridBagLayout();
		gbl_panel_4_1.columnWidths = new int[]{0, 0};
		gbl_panel_4_1.rowHeights = new int[]{0, 0,};
		gbl_panel_4_1.columnWeights = new double[]{0.0, 1.0, 4.9E-324, 0.0};
		gbl_panel_4_1.rowWeights = new double[]{0.0, 0.0, 4.9E-324};
		panel_4_1.setLayout(gbl_panel_4_1);
		
		JLabel lblNewLabel_4_1 = new JLabel("Contraseña");
		lblNewLabel_4_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_4_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel_4_1 = new GridBagConstraints();
		gbc_lblNewLabel_4_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblNewLabel_4_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4_1.gridx = 0;
		gbc_lblNewLabel_4_1.gridy = 0;
		panel_4_1.add(lblNewLabel_4_1, gbc_lblNewLabel_4_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		textField_1.setColumns(25);
		textField_1.setBounds(95,245, 140, 25);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 4;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 0;
		gbc_textField_1.gridy = 1;
		panel_4_1.add(textField_1, gbc_textField_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("Contraseña Requerida");
		lblNewLabel_5_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_5_1.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_5_1 = new GridBagConstraints();
		gbc_lblNewLabel_5_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_5_1.gridwidth = 2;
		gbc_lblNewLabel_5_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5_1.gridx = 2;
		gbc_lblNewLabel_5_1.gridy = 2;
		panel_4_1.add(lblNewLabel_5_1, gbc_lblNewLabel_5_1);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 0));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 4;
		contentPane.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{85, 0};
		gbl_panel_3.rowHeights = new int[]{21, 13, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JButton btnConfimar = new JButton("Ingresar");
		btnConfimar.setForeground(new Color(255, 255, 255));
		btnConfimar.setFont(new Font("Times New Roman", Font.PLAIN, 23));
		btnConfimar.setBackground(new Color(48, 60, 26));
		btnConfimar.setBounds(95,245,140,35);
		btnConfimar.setBorder(new LineBorder(Color.GRAY, 3, true));
		GridBagConstraints gbc_btnConfimar = new GridBagConstraints();
		gbc_btnConfimar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnConfimar.anchor = GridBagConstraints.SOUTH;
		gbc_btnConfimar.insets = new Insets(0, 0, 5, 0);
		gbc_btnConfimar.gridx = 0;
		gbc_btnConfimar.gridy = 0;
		panel_3.add(btnConfimar, gbc_btnConfimar);
		
		JLabel lblNewLabel = new JLabel("Usuario y/o Contraseña son incorrectos");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);
		
		/*
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.anchor = GridBagConstraints.BASELINE;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 5;
		contentPane.add(panel_5, gbc_panel_5);
		
		/*
		JLabel lblCafeImg = new JLabel("");
		lblCafeImg.setBounds(125, 320, 350, 350);
		lblCafeImg.setIcon(cargarIcono("../img/cafe.png", 350, 350));
		panel_5.add(lblCafeImg);
		*/
		

	}
	
	private ImageIcon cargarIcono(String ruta, int ancho, int largo) {

		try {
			Image icono = ImageIO.read(getClass().getResource(ruta));
			icono = icono.getScaledInstance(ancho, largo, Image.SCALE_SMOOTH);
			return new ImageIcon(icono);
		}catch(Exception ex) {
			System.out.println("No está la imagen del ícono");
		}
		
		return null;
	}

}
