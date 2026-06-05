package utils;

import models.User;

public class Session {
	
	private static User currentUser;
	
	public static void login(User user) {
		currentUser = user;
	}
	
	public static User getCurrentUser() {
		return currentUser;
	}
	
	public static void logout() {
		currentUser = null;
	}
	
	public static boolean isLoggedIn() {
		return currentUser != null;
	}
	
	
	public static String getRol( ) {
		return currentUser.getRol();
	}
	
	public static String getName( ) {
		return currentUser.getName();
	}
	
	public static String getImage() {
	    if (currentUser != null && currentUser.getImagePath() != null) {
	        return currentUser.getImagePath();
	    }
	    return ""; 
	}
	public static String getEmail( ) {
		return currentUser.getEmail();
	}
	
	public static String getPhone( ) {
		return currentUser.getTelefono();
	}
	
	public static String getGender( ) {
		return currentUser.getGenero();
	}
	
	
	public static String getBithDate( ) {
		return currentUser.getFechaNacimiento();
	}
	
	public static int getId() {
		return currentUser.getId();
	}
	

	
	

}