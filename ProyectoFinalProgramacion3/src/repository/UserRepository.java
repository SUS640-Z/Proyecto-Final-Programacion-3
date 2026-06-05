package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import config.DataBaseConnection;
import models.User;

public class UserRepository {

    public void save(User user) throws SQLException {
        String sql = "INSERT INTO users (user_name, last_name, email, phone, password_hash, image_path, rol_id, gender, birth_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idRol = obtenerIdPorNombreRol(user.getRol());
        if (idRol == -1) idRol = 2; 
        
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
                if(rs.next()) user.setId(rs.getInt(1)); 
            }
        }
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users AS u INNER JOIN rol AS r ON u.rol_id=r.rol_id";
        try(Connection connection = DataBaseConnection.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) {
                users.add(new User(rs.getInt("user_id"), rs.getString("user_name"), rs.getString("last_name"),
                    rs.getString("email"), rs.getString("password_hash"), rs.getString("image_path"),
                    rs.getString("phone"), rs.getString("gender"), rs.getString("birth_date"), rs.getString("rol_name")));
            }
        }
        return users;
    }

    public boolean update(int id, User updatedUser) {

        String rutaImagen = (updatedUser.getImagePath() == null || updatedUser.getImagePath().isEmpty()) 
                             ? obtenerRutaActual(id) : updatedUser.getImagePath();

        String sql = "UPDATE users SET user_name = ?, last_name = ?, email = ?, phone = ?, image_path = ?, gender = ?, birth_date = ? WHERE user_id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, updatedUser.getName());
            pst.setString(2, updatedUser.getLastName());
            pst.setString(3, updatedUser.getEmail());
            pst.setString(4, updatedUser.getTelefono());
            pst.setString(5, rutaImagen);
            pst.setString(6, updatedUser.getGenero());
            pst.setString(7, updatedUser.getFechaNacimiento());
            pst.setInt(8, id);
            return pst.executeUpdate() > 0;
        } catch(SQLException ex) { ex.printStackTrace(); }
        return false;
    }

    private String obtenerRutaActual(int id) {
        String sql = "SELECT image_path FROM users WHERE user_id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) return rs.getString("image_path");
        } catch (SQLException e) { e.printStackTrace(); }
        return "";
    }

    public boolean delete(int id) {
        deleteAddress(id);
        String sql="DELETE FROM users WHERE user_id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch(SQLException ex) { ex.printStackTrace(); }
        return false;
    }
    
    private void deleteAddress(int id) {
        String sql="DELETE FROM client_address WHERE user_id = ?";
        try(Connection connection = DataBaseConnection.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch(SQLException ex) { ex.printStackTrace(); }
    }

    public List<String> obtenerRoles() throws SQLException {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT rol_name FROM rol"; 
        try(Connection connection = DataBaseConnection.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while(rs.next()) roles.add(rs.getString("rol_name"));
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
                if (rs.next()) return rs.getInt("rol_id"); 
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return -1;
    }
}