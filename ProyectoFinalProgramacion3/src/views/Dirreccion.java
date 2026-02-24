package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Dirreccion extends Jframe{
	
	setBounds(100, 100, 531, 483);
	contentPane = new JPanel();
	contentPane.setBackground(new Color(255, 255, 128));
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	GridBagLayout gbl_contentPane = new GridBagLayout();
	gbl_contentPane.columnWidths = new int[]{0, 0};
	gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
	gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
	contentPane.setLayout(gbl_contentPane);
	
	JPanel panelSuperior = new JPanel();
	GridBagConstraints gbc_panelSuperior = new GridBagConstraints();
	gbc_panelSuperior.anchor = GridBagConstraints.NORTH;
	gbc_panelSuperior.fill = GridBagConstraints.HORIZONTAL;
	gbc_panelSuperior.insets = new Insets(0, 0, 5, 0);
	gbc_panelSuperior.gridx = 0;
	gbc_panelSuperior.gridy = 0;
	contentPane.add(panelSuperior, gbc_panelSuperior);
	GridBagLayout gbl_panelSuperior = new GridBagLayout();
	gbl_panelSuperior.columnWidths = new int[]{0, 0};
	gbl_panelSuperior.rowHeights = new int[]{0, 0, 0, 0, 0};
	gbl_panelSuperior.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panelSuperior.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
	panelSuperior.setLayout(gbl_panelSuperior);
	
	JLabel lblNewLabel = new JLabel("Ingresa Dirrecion");
	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
	GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
	gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
	gbc_lblNewLabel.gridx = 0;
	gbc_lblNewLabel.gridy = 0;
	panelSuperior.add(lblNewLabel, gbc_lblNewLabel);
	
	JComboBox cboxPaises = new JComboBox();
	cboxPaises.setFont(new Font("Tahoma", Font.PLAIN, 16));
	cboxPaises.setModel(new DefaultComboBoxModel(new String[] {"Mexico", "Estados Unidos", "Canada", "Peru", "Pueto Rico", "España"}));
	GridBagConstraints gbc_cboxPaises = new GridBagConstraints();
	gbc_cboxPaises.insets = new Insets(0, 0, 5, 0);
	gbc_cboxPaises.fill = GridBagConstraints.HORIZONTAL;
	gbc_cboxPaises.gridx = 0;
	gbc_cboxPaises.gridy = 1;
	panelSuperior.add(cboxPaises, gbc_cboxPaises);
	
	txtNombre = new JTextField();
	txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 16));
	txtNombre.setToolTipText("Nombre Completo(nombre y apellido)");
	txtNombre.putClientProperty("JTextField.placeholderText", "PEPEPPEPEPEP");
	GridBagConstraints gbc_txtNombre = new GridBagConstraints();
	gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
	gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
	gbc_txtNombre.gridx = 0;
	gbc_txtNombre.gridy = 2;
	panelSuperior.add(txtNombre, gbc_txtNombre);
	txtNombre.setColumns(30);
	
	JPanel panelCentral = new JPanel();
	GridBagConstraints gbc_panelCentral = new GridBagConstraints();
	gbc_panelCentral.fill = GridBagConstraints.HORIZONTAL;
	gbc_panelCentral.anchor = GridBagConstraints.NORTH;
	gbc_panelCentral.insets = new Insets(0, 0, 5, 0);
	gbc_panelCentral.gridx = 0;
	gbc_panelCentral.gridy = 1;
	contentPane.add(panelCentral, gbc_panelCentral);
	GridBagLayout gbl_panelCentral = new GridBagLayout();
	gbl_panelCentral.columnWidths = new int[]{0, 0};
	gbl_panelCentral.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
	gbl_panelCentral.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panelCentral.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
	panelCentral.setLayout(gbl_panelCentral);
	
	JSeparator separator = new JSeparator();
	GridBagConstraints gbc_separator = new GridBagConstraints();
	gbc_separator.insets = new Insets(0, 0, 5, 0);
	gbc_separator.gridx = 0;
	gbc_separator.gridy = 0;
	panelCentral.add(separator, gbc_separator);
	
	txtCalle = new JTextField();
	txtCalle.setFont(new Font("Tahoma", Font.PLAIN, 16));
	GridBagConstraints gbc_txtCalle = new GridBagConstraints();
	gbc_txtCalle.insets = new Insets(0, 0, 5, 0);
	gbc_txtCalle.fill = GridBagConstraints.HORIZONTAL;
	gbc_txtCalle.gridx = 0;
	gbc_txtCalle.gridy = 1;
	panelCentral.add(txtCalle, gbc_txtCalle);
	txtCalle.setColumns(30);
	
	txtCodigoPostal = new JTextField();
	txtCodigoPostal.setToolTipText("CodigoPostal");
	txtCodigoPostal.setFont(new Font("Tahoma", Font.PLAIN, 16));
	GridBagConstraints gbc_txtCodigoPostal = new GridBagConstraints();
	gbc_txtCodigoPostal.insets = new Insets(0, 0, 5, 0);
	gbc_txtCodigoPostal.fill = GridBagConstraints.HORIZONTAL;
	gbc_txtCodigoPostal.gridx = 0;
	gbc_txtCodigoPostal.gridy = 2;
	panelCentral.add(txtCodigoPostal, gbc_txtCodigoPostal);
	txtCodigoPostal.setColumns(10);
	
	JButton btnValidarCodigoPostal = new JButton("Validar Codigo Postal");
	btnValidarCodigoPostal.setFont(new Font("Tahoma", Font.PLAIN, 16));
	GridBagConstraints gbc_btnValidarCodigoPostal = new GridBagConstraints();
	gbc_btnValidarCodigoPostal.insets = new Insets(0, 0, 5, 0);
	gbc_btnValidarCodigoPostal.gridx = 0;
	gbc_btnValidarCodigoPostal.gridy = 3;
	panelCentral.add(btnValidarCodigoPostal, gbc_btnValidarCodigoPostal);
	
	JLabel lblNewLabel_3 = new JLabel("Tienes que propocionar un codigo postal");
	GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
	gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
	gbc_lblNewLabel_3.gridx = 0;
	gbc_lblNewLabel_3.gridy = 4;
	panelCentral.add(lblNewLabel_3, gbc_lblNewLabel_3);
	
	txtNumeroTelefono = new JTextField();
	txtNumeroTelefono.setFont(new Font("Tahoma", Font.PLAIN, 16));
	GridBagConstraints gbc_txtNumeroTelefono = new GridBagConstraints();
	gbc_txtNumeroTelefono.fill = GridBagConstraints.HORIZONTAL;
	gbc_txtNumeroTelefono.gridx = 0;
	gbc_txtNumeroTelefono.gridy = 5;
	panelCentral.add(txtNumeroTelefono, gbc_txtNumeroTelefono);
	txtNumeroTelefono.setColumns(10);
	
	JPanel panelInferior = new JPanel();
	GridBagConstraints gbc_panelInferior = new GridBagConstraints();
	gbc_panelInferior.fill = GridBagConstraints.HORIZONTAL;
	gbc_panelInferior.anchor = GridBagConstraints.NORTH;
	gbc_panelInferior.insets = new Insets(0, 0, 5, 0);
	gbc_panelInferior.gridx = 0;
	gbc_panelInferior.gridy = 2;
	contentPane.add(panelInferior, gbc_panelInferior);
	GridBagLayout gbl_panelInferior = new GridBagLayout();
	gbl_panelInferior.columnWidths = new int[]{0, 0};
	gbl_panelInferior.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
	gbl_panelInferior.columnWeights = new double[]{1.0, Double.MIN_VALUE};
	gbl_panelInferior.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
	panelInferior.setLayout(gbl_panelInferior);
	
	JSeparator separator_1 = new JSeparator();
	GridBagConstraints gbc_separator_1 = new GridBagConstraints();
	gbc_separator_1.insets = new Insets(0, 0, 5, 0);
	gbc_separator_1.gridx = 0;
	gbc_separator_1.gridy = 0;
	panelInferior.add(separator_1, gbc_separator_1);
	
	JLabel lblInstrucciones = new JLabel("Agregar Instrucciones de entrega");
	lblInstrucciones.setFont(new Font("Tahoma", Font.PLAIN, 20));
	GridBagConstraints gbc_lblInstrucciones = new GridBagConstraints();
	gbc_lblInstrucciones.insets = new Insets(0, 0, 5, 0);
	gbc_lblInstrucciones.gridx = 0;
	gbc_lblInstrucciones.gridy = 1;
	panelInferior.add(lblInstrucciones, gbc_lblInstrucciones);
	
	JLabel lblInstrucion2 = new JLabel("Descripcion de la casa");
	lblInstrucion2.setFont(new Font("Tahoma", Font.PLAIN, 18));
	GridBagConstraints gbc_lblInstrucion2 = new GridBagConstraints();
	gbc_lblInstrucion2.insets = new Insets(0, 0, 5, 0);
	gbc_lblInstrucion2.gridx = 0;
	gbc_lblInstrucion2.gridy = 2;
	panelInferior.add(lblInstrucion2, gbc_lblInstrucion2);
	
	JTextArea txtDetallesCasa = new JTextArea();
	txtDetallesCasa.setFont(new Font("Courier New", Font.PLAIN, 12));
	txtDetallesCasa.setColumns(60);
	GridBagConstraints gbc_txtDetallesCasa = new GridBagConstraints();
	gbc_txtDetallesCasa.insets = new Insets(0, 0, 5, 0);
	gbc_txtDetallesCasa.fill = GridBagConstraints.BOTH;
	gbc_txtDetallesCasa.gridx = 0;
	gbc_txtDetallesCasa.gridy = 3;
	panelInferior.add(txtDetallesCasa, gbc_txtDetallesCasa);
	
	JCheckBox chckbxNewCheckBox = new JCheckBox("Usar mi dirrecion como predeterminada");
	GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
	gbc_chckbxNewCheckBox.gridx = 0;
	gbc_chckbxNewCheckBox.gridy = 4;
	panelInferior.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
	setBackground(new Color(15, 19, 9));
}
