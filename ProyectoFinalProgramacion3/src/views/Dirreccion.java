package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;

import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Dirreccion extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtCalle;
	private JTextField txtCodigoPostal;
	private JTextField txtNumeroTelefono;

	public Dirreccion() {
		setTitle("Saturnbucks.direccion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image icono = tk.getImage("src/img/coffe.png");
		setIconImage(icono);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(210, 180, 140));
		

		Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border panelTitledBorder = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2),
				"DATOS DE ENVIO", 
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 14),
				Color.BLACK);
		
		contentPane.setBorder(BorderFactory.createCompoundBorder(emptyBorder, panelTitledBorder));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JPanel panelFormulario = new JPanel();
		panelFormulario.setOpaque(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(panelFormulario, gbc_panel);

		GridBagLayout gbl_form = new GridBagLayout();
		panelFormulario.setLayout(gbl_form);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 5, 2, 5); 
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;

		JLabel lblTitulo = new JLabel("Ingresa tu direccion");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
		c.gridy = 0;
		c.insets = new Insets(10, 5, 20, 5);
		panelFormulario.add(lblTitulo, c);

		c.insets = new Insets(2, 5, 2, 5);


		c.gridy = 1;
		panelFormulario.add(new JLabel("País/Región:"), c);
		JComboBox cboxPaises = new JComboBox();
		cboxPaises.setModel(new DefaultComboBoxModel(new String[] {"Mexico", "Estados Unidos", "Canada", "Peru"}));
		c.gridy = 2;
		panelFormulario.add(cboxPaises, c);

		c.gridy = 3;
		panelFormulario.add(new JLabel("Nombre completo:"), c);
		txtNombre = new JTextField();
		c.gridy = 4;
		panelFormulario.add(txtNombre, c);

		c.gridy = 5;
		panelFormulario.add(new JLabel("Direccion (col, calle, num): "), c);
		txtCalle = new JTextField();
		c.gridy = 6;
		panelFormulario.add(txtCalle, c);

		c.gridy = 7;
		panelFormulario.add(new JLabel("Codigo Postal:"), c);
		txtCodigoPostal = new JTextField();
		c.gridy = 8;
		panelFormulario.add(txtCodigoPostal, c);

		c.fill = GridBagConstraints.NONE; 
		c.anchor = GridBagConstraints.CENTER; 
		c.gridy = 9;
		c.insets = new Insets(5, 5, 10, 5);
		
		JButton btnValidarCP = new JButton("Validar Codigo Postal");
		btnValidarCP.setFont(new Font("Times New Roman", Font.PLAIN, 12)); 
		btnValidarCP.setBackground(new Color(48, 60, 26)); 
		btnValidarCP.setForeground(Color.WHITE);
		btnValidarCP.setBorder(new LineBorder(Color.GRAY, 2, true));
		panelFormulario.add(btnValidarCP, c);

		c.fill = GridBagConstraints.HORIZONTAL; 
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(2, 5, 2, 5);
		c.gridy = 10;
		panelFormulario.add(new JLabel("Numero de telefono:"), c);
		txtNumeroTelefono = new JTextField();
		c.gridy = 11;
		panelFormulario.add(txtNumeroTelefono, c);

		c.gridy = 12;
		c.insets = new Insets(15, 5, 2, 5);
		panelFormulario.add(new JLabel("Instrucciones de entrega:"), c);
		
		JTextArea txtDetalles = new JTextArea(4, 20);
		JScrollPane scroll = new JScrollPane(txtDetalles);
		c.gridy = 13;
		c.insets = new Insets(2, 5, 5, 5);
		panelFormulario.add(scroll, c);

		JCheckBox chckbx = new JCheckBox("Usar como predeterminada");
		chckbx.setOpaque(false);
		c.gridy = 14;
		panelFormulario.add(chckbx, c);

		JButton btnConfirmar = new JButton("Confirmar Direccion");
		btnConfirmar.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		btnConfirmar.setBackground(new Color(48, 60, 26));
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setBorder(new LineBorder(Color.GRAY, 3, true));
		c.gridy = 15;
		c.insets = new Insets(20, 5, 10, 5);
		panelFormulario.add(btnConfirmar, c);

		setVisible(true);
	}
}