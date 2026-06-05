package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
	        
	        lblCerrar.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent e) { 
	                JOptionPane.showMessageDialog(null, "Sesión cerrada exitosamente", "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
	            }
	        });

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
	         avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
	         panelAvatar.add(avatar);
	         panelAvatar.add(Box.createRigidArea(new Dimension(0, 10)));

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
	         
	         String telefono= Session.getPhone();
	         String genero = Session.getGender();
	         String fechaNacimiento = Session.getBithDate();
	         String correo = Session.getEmail();
	         
	         if(genero.equals("Masculine")) {
	        	 genero="Masculino";
	         }else {
	        	 genero="FemeNino";
	         }

	         agregarCampoInfo(panelInfo, gbc, 0, 0, "Correo Electrónico:", correo);
	         agregarCampoInfo(panelInfo, gbc, 1, 0, "Fecha de Nacimiento:",fechaNacimiento);
	         agregarCampoInfo(panelInfo, gbc, 0, 1, "Género:", genero);
	         agregarCampoInfo(panelInfo, gbc, 1, 1, "Teléfono móvil:", telefono);

	         contenedorPrincipal.add(panelInfo);
	         contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

	         JScrollPane scroll = new JScrollPane(contenedorPrincipal);
	         scroll.setBorder(null);
	         scroll.getVerticalScrollBar().setUnitIncrement(16);
	         scroll.setBackground(new Color(15, 19, 9));
	         
	         contentPane.add(scroll, BorderLayout.CENTER);
	     }
	     
	     
	     private void agregarCampoInfo(JPanel panel, GridBagConstraints gbc, int col, int fila, String etiqueta, String valor) {
	         gbc.gridx = col * 2;
	         gbc.gridy = fila;
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



		 public JLabel getLblInicio() {
			 return lblInicio;
		 }


		 public void setLblInicio(JLabel lblInicio) {
			 this.lblInicio = lblInicio;
		 }


		 public JLabel getLblOrdenes() {
			 return lblOrdenes;
		 }


		 public void setLblOrdenes(JLabel lblOrdenes) {
			 this.lblOrdenes = lblOrdenes;
		 }


		 public JLabel getLblCerrar() {
			 return lblCerrar;
		 }


		 public void setLblCerrar(JLabel lblCerrar) {
			 this.lblCerrar = lblCerrar;
		 }
		 
		 
	     
	     
	     
	     
}
