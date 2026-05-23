package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import config.DataBaseConnection; 
import models.User;

public class UserRepository {

	public void save(User user) throws SQLException {
	    String sql = "INSERT INTO Users (user_name, last_name, email, phone, password_hash, image_path, rol_id, gender, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    int idRol = obtenerIdPorNombreRol(user.getRol());
	    
	    if (idRol == -1) {
	        idRol = 2; 
	    }
	    
	    try(Connection connection = DataBaseConnection.getConnection();
	        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setString(1, user.getName());         
	        pst.setString(2, user.getLastName());       
	        pst.setString(3, user.getEmail());          
	        pst.setString(4, user.getTelefono());     
	        pst.setString(5, user.getPassword());       
	        pst.setString(6, user.getImagePath());      
	        pst.setInt(7, idRol);                    
	        pst.setString(8, user.getGenero());      
	        pst.setString(9, user.getFechaNacimiento()); 
	        
	        pst.executeUpdate();

	        try (ResultSet rs = pst.getGeneratedKeys()) { 
	            if(rs.next()) {
	                user.setId(rs.getInt(1)); 
	            }
	        }
	    }
	}

	public List<User> getUsers() throws SQLException {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM Users AS u INNER JOIN Rol AS r ON u.rol_id=r.rol_id";
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				User user = new User(
					rs.getInt("user_id"), 
					rs.getString("user_name"), 
					rs.getString("last_name"),
					rs.getString("email"),
					rs.getString("password_hash"),
					rs.getString("image_path"),
					rs.getString("phone"),
					rs.getString("gender"),
					rs.getString("birth_date"),
					rs.getString("rol_name")
				);
				users.add(user);
			}
		}
		return users;		
	}

	public boolean delete(int id) {
		deleteAddress(id);
		String sql="DELETE FROM users WHERE user_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteAddress(int id) {
		String sql="DELETE FROM client_address WHERE user_id = ?";
		try(Connection connection = DataBaseConnection.getConnection();
			PreparedStatement pst = connection.prepareStatement(sql)) {
			pst.setInt(1, id);
			return pst.executeUpdate() > 0;
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	

	public boolean update(int id, User updatedUser) {
	    String sql = "UPDATE Users SET user_name = ?, last_name = ?, email = ?, password_hash = ?, image_path = ?, rol_id = ?, phone = ?, gender = ?, birth_date = ? WHERE user_id = ?";
	    
	    int idRol = obtenerIdPorNombreRol(updatedUser.getRol());
	    if (idRol == -1) {
	        idRol = 2;
	    }
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setString(1, updatedUser.getName());
	        pst.setString(2, updatedUser.getLastName());
	        pst.setString(3, updatedUser.getEmail());
	        pst.setString(4, updatedUser.getPassword());
	        pst.setString(5, updatedUser.getImagePath());
	        pst.setInt(6, idRol); 
	        pst.setString(7, updatedUser.getTelefono());     
	        pst.setString(8, updatedUser.getGenero());      
	        pst.setString(9, updatedUser.getFechaNacimiento()); 
	        pst.setInt(10, id);                              
	        
	        return pst.executeUpdate() > 0;
	    } catch(SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public List<String> obtenerRoles() throws SQLException {
		List<String> roles = new ArrayList<>();
		String sql = "SELECT rol_name FROM rol"; 
		
		try(Connection connection = DataBaseConnection.getConnection();
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(sql)) {
			
			while(rs.next()) {
				roles.add(rs.getString("rol_name"));
			}
		}
		return roles;
	}
	
	private int obtenerIdPorNombreRol(String nombreRol) {
	    if (nombreRol == null || nombreRol.equals("Seleccionar")) return -1;
	    String sql = "SELECT rol_id FROM rol WHERE rol_name = ?";
	    
	    try (Connection connection = DataBaseConnection.getConnection();
	         PreparedStatement pst = connection.prepareStatement(sql)) {
	        
	        pst.setString(1, nombreRol);
	        try (ResultSet rs = pst.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("rol_id"); 
	            }
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return -1;
	}
}