package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import components.AvatarCircular;
import utils.Session;

public class ProfileView extends JFrame{
	private JPanel contentPane;
	JLabel lblInicio;
	JLabel lblOrdenes;
	JLabel lblCerrar;
	
	private JPanel pnlListaDirecciones; 
	private JButton btnAgregarDireccion; 
	private JButton btnEditarPerfil;
	
	public ProfileView() {
		setTitle("Saturnbucks - Mi Perfil");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 650); 
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(new Color(15, 19, 9));
		setContentPane(contentPane);
		
		generarMenu();
		generarContenidoPerfil();
		generarFooter();
		
		setVisible(true);
	}
	
	private void generarMenu() {
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(15, 19, 9)); 
		panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26))); 

		lblInicio = crearItemMenu("Inicio");
		lblOrdenes = crearItemMenu("Órdenes");
		lblCerrar = crearItemMenu("Cerrar Sesión");
		
		JLabel lblSeparador1 = new JLabel("  |  ");
		lblSeparador1.setForeground(Color.DARK_GRAY);
		JLabel lblSeparador2 = new JLabel("  |  ");
		lblSeparador2.setForeground(Color.DARK_GRAY);

		panelMenu.add(lblInicio);
		panelMenu.add(lblSeparador1);
		panelMenu.add(lblOrdenes);
		panelMenu.add(lblSeparador2);
		panelMenu.add(lblCerrar);
		
		contentPane.add(panelMenu, BorderLayout.NORTH);
	}

	private JLabel crearItemMenu(String texto) {
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setForeground(new Color(210, 180, 140)); 
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		label.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) { label.setForeground(Color.WHITE); } 
			public void mouseExited(MouseEvent e) { label.setForeground(new Color(210, 180, 140)); } 
		});
		return label;
	}
	 
	private void generarFooter() {
		JPanel panelFooter = new JPanel();
		panelFooter.setBackground(new Color(15, 19, 9));
		panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

		JLabel lblFooter = new JLabel("© 2026 Saturnbucks Co. | La Paz, B.C.S.");
		lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
		lblFooter.setForeground(Color.GRAY);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);

		panelFooter.add(lblFooter);
		contentPane.add(panelFooter, BorderLayout.SOUTH);
	}
	 
	private void generarContenidoPerfil() {
	    JPanel contenedorPrincipal = new JPanel();
	    contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
	    contenedorPrincipal.setBackground(new Color(15, 19, 9));
	    contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
	   
	    JPanel panelAvatar = new JPanel();
	    panelAvatar.setLayout(new BoxLayout(panelAvatar, BoxLayout.Y_AXIS));
	    panelAvatar.setBackground(new Color(15, 19, 9));
	    panelAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

	    AvatarCircular avatar = new AvatarCircular(Session.getName(), 100);
	    String rutaFoto = Session.getCurrentUser().getImagePath();
	    
	    if (rutaFoto != null && !rutaFoto.trim().isEmpty()) {
	        java.io.File archivo = new java.io.File(rutaFoto);
	        if (archivo.exists()) {
	            try {
	                java.awt.Image imgPerfil = javax.imageio.ImageIO.read(archivo);
	                avatar.setImagenPerfil(imgPerfil);
	            } catch (Exception ex) {
	                System.out.println("Error al cargar la foto de perfil: " + ex.getMessage());
	            }
	        }
	    }
	    
	    avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelAvatar.add(avatar);
	    panelAvatar.add(Box.createRigidArea(new Dimension(0, 10)));

	    btnEditarPerfil = new JButton("Editar Perfil");
	    btnEditarPerfil.setBackground(new Color(48, 60, 26));
	    btnEditarPerfil.setForeground(Color.WHITE);
	    btnEditarPerfil.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panelAvatar.add(btnEditarPerfil);

	    contenedorPrincipal.add(panelAvatar);
	    contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

	    JPanel panelInfo = new JPanel(new GridBagLayout());
	    panelInfo.setBackground(new Color(20, 25, 13)); 
	    panelInfo.setBorder(BorderFactory.createCompoundBorder(
	            new LineBorder(new Color(48, 60, 26), 1, true),
	            BorderFactory.createEmptyBorder(15, 20, 15, 20)
	    ));
	    panelInfo.setMaximumSize(new Dimension(700, 150));

	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(6, 15, 6, 15);
	    gbc.anchor = GridBagConstraints.WEST;
	     
	    String genero = Session.getGender().equals("Masculine") ? "Masculino" : "Femenino";

	    agregarCampoInfo(panelInfo, gbc, 0, 0, "Correo Electrónico:", Session.getEmail());
	    agregarCampoInfo(panelInfo, gbc, 1, 0, "Fecha de Nacimiento:", Session.getBithDate());
	    agregarCampoInfo(panelInfo, gbc, 0, 1, "Género:", genero);
	    agregarCampoInfo(panelInfo, gbc, 1, 1, "Teléfono móvil:", Session.getPhone());

	    contenedorPrincipal.add(panelInfo);
	    contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 40)));

	    JPanel pnlHeaderDirecciones = new JPanel(new BorderLayout());
	    pnlHeaderDirecciones.setOpaque(false);
	    pnlHeaderDirecciones.setMaximumSize(new Dimension(700, 40));
	    
	    JLabel lblMisDirecciones = new JLabel("Mis Direcciones Guardadas");
	    lblMisDirecciones.setFont(new Font("Times New Roman", Font.BOLD, 22));
	    lblMisDirecciones.setForeground(new Color(210, 180, 140));
	    
	    btnAgregarDireccion = new JButton("+ Nueva Dirección");
	    btnAgregarDireccion.setBackground(new Color(48, 60, 26));
	    btnAgregarDireccion.setForeground(Color.WHITE);
	    btnAgregarDireccion.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    
	    pnlHeaderDirecciones.add(lblMisDirecciones, BorderLayout.WEST);
	    pnlHeaderDirecciones.add(btnAgregarDireccion, BorderLayout.EAST);
	    
	    contenedorPrincipal.add(pnlHeaderDirecciones);
	    contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

	    pnlListaDirecciones = new JPanel();
	    pnlListaDirecciones.setLayout(new BoxLayout(pnlListaDirecciones, BoxLayout.Y_AXIS));
	    pnlListaDirecciones.setBackground(new Color(15, 19, 9));
	    
	    JScrollPane scrollDirecciones = new JScrollPane(pnlListaDirecciones);
	    scrollDirecciones.setMaximumSize(new Dimension(700, 250));
	    scrollDirecciones.setBorder(null);
	    scrollDirecciones.getViewport().setBackground(new Color(15, 19, 9));
	    scrollDirecciones.getVerticalScrollBar().setUnitIncrement(16);
	    
	    contenedorPrincipal.add(scrollDirecciones);

	    JScrollPane scroll = new JScrollPane(contenedorPrincipal);
	    scroll.setBorder(null);
	    scroll.getVerticalScrollBar().setUnitIncrement(16);
	    scroll.setBackground(new Color(15, 19, 9));
	     
	    contentPane.add(scroll, BorderLayout.CENTER);
	}
	 
	private void agregarCampoInfo(JPanel panel, GridBagConstraints gbc, int col, int fila, String etiqueta, String valor) {
		gbc.gridx = col * 2; gbc.gridy = fila;
		JLabel lblEtiqueta = new JLabel(etiqueta);
		lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 14));
		lblEtiqueta.setForeground(new Color(210, 180, 140));
		panel.add(lblEtiqueta, gbc);

		gbc.gridx = (col * 2) + 1;
		JLabel lblValor = new JLabel(valor);
		lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
		lblValor.setForeground(Color.WHITE);
		panel.add(lblValor, gbc);
	}

	public JLabel getLblInicio() { return lblInicio; }
	public JLabel getLblOrdenes() { return lblOrdenes; }
	public JLabel getLblCerrar() { return lblCerrar; }
	public JButton getBtnAgregarDireccion() { return btnAgregarDireccion; }
	public JPanel getPnlListaDirecciones() { return pnlListaDirecciones; }
	public JButton getBtnEditarPerfil() { return btnEditarPerfil; }
}