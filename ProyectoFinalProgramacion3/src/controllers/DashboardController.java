package controllers;

import views.DashboardView;
import config.DataBaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {
    private DashboardView view;

    public DashboardController(DashboardView view) {
        this.view = view;
    }

    public void loadStats() {
        try (Connection conn = DataBaseConnection.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM orders"); 
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) view.lblTotalPedidos.setText(String.valueOf(rs.getInt(1)));
            } catch (Exception e) {
                System.out.println("Error en Pedidos Totales: " + e.getMessage());
            }

            String sqlIngresos = "SELECT SUM(total_amount) FROM orders WHERE status_order NOT IN ('cancelled', 'failed','refunded')";
            try (PreparedStatement ps = conn.prepareStatement(sqlIngresos); 
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    view.lblIngresos.setText(String.format("$%.2f", rs.getDouble(1)));
                }
            } catch (Exception e) {
                System.out.println("Error en conteo de Ingresos: " + e.getMessage());
                view.lblIngresos.setText("$0.00 (Error SQL)");
            }

            String sqlClientes = "SELECT COUNT(*) FROM users"; 
            try (PreparedStatement ps = conn.prepareStatement(sqlClientes); 
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) view.lblClientes.setText(String.valueOf(rs.getInt(1)));
            } catch (Exception e) {
                System.out.println("Error en conteo de Clientes: " + e.getMessage());
                view.lblClientes.setText("0 (Error SQL)");
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM orders WHERE status_order = 'pending'"); 
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) view.lblPendientes.setText(String.valueOf(rs.getInt(1)));
            } catch (Exception e) {
                System.out.println("Error en Pedidos Pendientes: " + e.getMessage());
            }

            String sqlTopProductos = "SELECT p.product_name, SUM(od.quantity) as ventas " +
                                     "FROM order_details od " +
                                     "JOIN product p ON od.product_id = p.product_id " +
                                     "GROUP BY p.product_id, p.product_name " +
                                     "ORDER BY ventas DESC LIMIT 5";
            try (PreparedStatement ps = conn.prepareStatement(sqlTopProductos); 
                 ResultSet rs = ps.executeQuery()) {
                StringBuilder htmlProductos = new StringBuilder("<html><ol style='margin-left: 20px; padding: 0;'>");
                while(rs.next()) {
                    htmlProductos.append("<li>").append(rs.getString(1)).append(" (").append(rs.getInt(2)).append(" unds)</li>");
                }
                htmlProductos.append("</ol></html>");
                view.lblTopProductos.setText(htmlProductos.toString());
            } catch (Exception e) {
                System.out.println("Error en Top Productos: " + e.getMessage());
                view.lblTopProductos.setText("<html>No hay datos</html>");
            }

            String sqlTopClientes = "SELECT u.user_name, COUNT(o.order_id) as compras " +
                                    "FROM orders o " +
                                    "JOIN users u ON o.user_id = u.user_id " +
                                    "GROUP BY u.user_id, u.user_name " +
                                    "ORDER BY compras DESC LIMIT 5";
            try (PreparedStatement ps = conn.prepareStatement(sqlTopClientes); 
                 ResultSet rs = ps.executeQuery()) {
                StringBuilder htmlClientes = new StringBuilder("<html><ol style='margin-left: 20px; padding: 0;'>");
                while(rs.next()) {
                    htmlClientes.append("<li>").append(rs.getString(1)).append(" (").append(rs.getInt(2)).append(" pedidos)</li>");
                }
                htmlClientes.append("</ol></html>");
                view.lblTopClientes.setText(htmlClientes.toString());
            } catch (Exception e) {
                System.out.println("Error en Top Clientes: " + e.getMessage());
                view.lblTopClientes.setText("<html>No hay datos</html>");
            }
            
        } catch (Exception e) {
            System.out.println("Error crítico de conexión en el Dashboard: " + e.getMessage());
        }
    }
}