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
            
        } catch (Exception e) {
            System.out.println("Error crítico de conexión en el Dashboard: " + e.getMessage());
        }
    }
}