package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import config.DataBaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

	public User login(String email, String password) {
	    String sql = "SELECT Users.user_id, Users.email, Users.password_hash, Users.user_name, Users.phone, " +
	                 "Users.gender, Users.birth_date, Users.image_path, Rol.rol_name " + 
	                 "FROM users Users " +
	                 "INNER JOIN rol Rol ON Users.rol_id = Rol.rol_id " +
	                 "WHERE Users.email = ?";

	    try (Connection conn = DataBaseConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, email);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if(rs.next()) {
	                String hashedPassword = rs.getString("password_hash");
	                if(!PasswordUtils.checkPassword(password, hashedPassword)) return null;

	                User user = new User();
	                user.setId(rs.getInt("user_id"));
	                user.setEmail(rs.getString("email"));
	                user.setName(rs.getString("user_name"));
	                user.setRol(rs.getString("rol_name"));
	                user.setGenero(rs.getString("gender"));
	                user.setTelefono(rs.getString("phone"));
	                user.setFechaNacimiento(rs.getString("birth_date"));
	                user.setImagePath(rs.getString("image_path"));

	                return user;
	            }
	        }
	    } catch (SQLException e) { e.printStackTrace(); }
	    return null;
	}
	
}