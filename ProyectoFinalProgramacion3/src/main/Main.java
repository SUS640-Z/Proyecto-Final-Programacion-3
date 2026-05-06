package main;
import com.formdev.flatlaf.FlatLightLaf;

import utils.ThemeManager;
import views.InicioView;

public class Main {
	public static void main(String[] args) {
		
		ThemeManager.applySavedTheme();
		new InicioView ();

	}
}