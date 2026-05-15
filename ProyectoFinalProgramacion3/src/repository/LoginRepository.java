package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

import config.DataBaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

	public User login(String email, String password) {
		
		/*String sql = "SELECT id, email, password FROM users WHERE email = '" 
				+ email + "' AND password = '" + password + "'";*/
		
		String sql = "SELECT id_cliente, correo, password, nombres FROM Cliente WHERE correo = ?";
		
		try (
			Connection conn = DataBaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("password");
			    System.out.println("Hash almacenado: " + hashedPassword);
			    System.out.println("Contraseña ingresada: " + password);
			    
			    boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);
			    System.out.println("Contraseña correcta? " + correctPassword);
			    
				if(!correctPassword) 
					return null;
				
				User user = new User();
				user.setId(rs.getInt("id_cliente"));
				user.setEmail(rs.getString("correo"));
				user.setName(rs.getString("nombres"));
				/*
				user.setRole(rs.getString("role"));
				*/
				
				return user;
			}
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
}
