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
		
		String sql = "SELECT Users.user_id,Users.email, Users.password_hash,Users.user_name,Rol.rol_name FROM Users INNER JOIN Rol WHERE email = ? and Users.rol_id = Rol.rol_id ";
		
		try (
			Connection conn = DataBaseConnection.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				String hashedPassword = rs.getString("password_hash");
			    System.out.println("Hash almacenado: " + hashedPassword);
			    System.out.println("Contraseña ingresada: " + password);
			    System.out.println("Contraseña ingresada hasheada: " + PasswordUtils.hashPassword(password));
			    
			    boolean correctPassword = PasswordUtils.checkPassword(password, hashedPassword);
			    System.out.println("Contraseña correcta? " + correctPassword);
			    
				if(!correctPassword) 
					return null;
				
				User user = new User();
				user.setId(rs.getInt("user_id"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("user_name"));
				user.setRol(rs.getString("rol_name"));
				
				return user;
			}
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
}
