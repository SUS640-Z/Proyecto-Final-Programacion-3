package controllers;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import utils.Session;
import views.InicioView;
import views.ProfileView;

public class ProfileController {
	private ProfileView view;
	
	public ProfileController(ProfileView view) {
		this.view = view;
		registerListeners();
	}
	
	private void registerListeners() {
		this.view.getLblInicio().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
            	InicioView inicioView = new InicioView();
			}
		});
		
		this.view.getLblCerrar().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				view.dispose();
				Session.logout();
            	InicioView inicioView = new InicioView();
			}
		});
	}
	
	

}
