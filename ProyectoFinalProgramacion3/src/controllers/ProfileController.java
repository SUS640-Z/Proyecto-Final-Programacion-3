package controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Address;
import models.User;
import repository.AddressRepository;
import repository.UserRepository;
import utils.Session;
import views.Dirreccion;
import views.InicioView;
import views.OrdenesView;
import views.ProfileEditDialog;
import views.ProfileView;

public class ProfileController {
	private ProfileView view;
	private AddressRepository addressRepo;
	private UserRepository userRepo;
	
	public ProfileController(ProfileView view) {
		this.view = view;
		this.addressRepo = new AddressRepository();
		this.userRepo = new UserRepository(); 
		
		registerListeners();
		cargarDirecciones();
	}
	
	private void registerListeners() {

		this.view.getLblInicio().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				new InicioView();
			}
		});
		
		this.view.getLblOrdenes().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				OrdenesView ordenesView = new OrdenesView();
				new OrdenesController(ordenesView);
			}
		});
		
		this.view.getLblCerrar().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Sesión cerrada exitosamente");
				view.dispose();
				Session.logout();
				new InicioView();
			}
		});
		

		this.view.getBtnAgregarDireccion().addActionListener(e -> {
			view.dispose();
			new Dirreccion(Session.getCurrentUser()).setVisible(true);
		});

		this.view.getBtnEditarPerfil().addActionListener(e -> {
		    ProfileEditDialog dialog = new ProfileEditDialog();
		    dialog.setVisible(true);
		    
		    if (dialog.isSaved()) {
		        try {
		            User usuarioCompleto = null;
		            List<User> listaUsuarios = userRepo.getUsers();
		            for (User u : listaUsuarios) {
		                if (u.getId() == Session.getId()) {
		                    usuarioCompleto = u;
		                    break;
		                }
		            }
		            
		            if (usuarioCompleto != null) {
		                usuarioCompleto.setTelefono(dialog.getNuevoTelefono());

		                String nuevaRuta = dialog.getRutaNuevaImagen();
		                if (nuevaRuta != null && !nuevaRuta.isEmpty()) {
		                    usuarioCompleto.setImagePath(nuevaRuta);
		                }

		                userRepo.update(usuarioCompleto.getId(), usuarioCompleto);

		                Session.getCurrentUser().setTelefono(usuarioCompleto.getTelefono());
		                if (nuevaRuta != null && !nuevaRuta.isEmpty()) {
		                    Session.getCurrentUser().setImagePath(usuarioCompleto.getImagePath());
		                }
		                
		                JOptionPane.showMessageDialog(view, "Perfil actualizado con éxito.");
		                view.dispose();
		                new ProfileController(new ProfileView());
		            } else {
		                JOptionPane.showMessageDialog(view, "Error: No se encontró el usuario en el sistema.");
		            }
		            
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(view, "Error al actualizar perfil: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
	}

	private void cargarDirecciones() {
		try {
			int idUsuario = Session.getId(); 
			List<Address> direcciones = addressRepo.getAddressesByUserId(idUsuario);
			
			JPanel pnlLista = view.getPnlListaDirecciones();
			pnlLista.removeAll();
			
			if (direcciones.isEmpty()) {
				JLabel lblVacio = new JLabel("Aún no tienes direcciones guardadas.");
				lblVacio.setForeground(Color.GRAY);
				pnlLista.add(lblVacio);
			} else {
				for (Address addr : direcciones) {
					pnlLista.add(crearTarjetaDireccion(addr));
					pnlLista.add(Box.createRigidArea(new Dimension(0, 10))); 
				}
			}
			
			pnlLista.revalidate();
			pnlLista.repaint();
			
		} catch (Exception e) {
			System.out.println("Error cargando direcciones: " + e.getMessage());
		}
	}
	
	private JPanel crearTarjetaDireccion(Address addr) {
		JPanel card = new JPanel(new BorderLayout(10, 5));
		card.setBackground(new Color(20, 25, 13));
		card.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(48, 60, 26), 1),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)
		));
		card.setMaximumSize(new Dimension(700, 80));
		
		String textoHtml = "<html><b style='color:#D2B48C;'>Colonia:</b> " + addr.getNeighborhood() + 
						   " | <b style='color:#D2B48C;'>Calle:</b> " + addr.getStreet() + "<br>" +
						   "<b style='color:#D2B48C;'>Ref:</b> " + addr.getReference() + " | <b style='color:#D2B48C;'>Instrucciones:</b> " + addr.getInstructions() + "</html>";
						   
		JLabel lblInfo = new JLabel(textoHtml);
		lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		lblInfo.setForeground(Color.WHITE);
		
		card.add(lblInfo, BorderLayout.CENTER);
		return card;
	}
}