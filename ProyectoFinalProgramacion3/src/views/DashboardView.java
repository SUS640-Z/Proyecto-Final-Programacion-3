package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardView extends JPanel {
    public JLabel lblTotalPedidos, lblIngresos, lblClientes, lblPendientes;
    public JLabel lblTopProductos, lblTopClientes;

    public DashboardView() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(15, 19, 9));
        setBorder(new EmptyBorder(20, 30, 30, 30));

        JLabel titulo = new JLabel("Estadísticas Generales", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 2, 20, 20));
        grid.setOpaque(false);

        // -- FILA 1 --
        lblTotalPedidos = new JLabel("0", SwingConstants.CENTER);
        lblTotalPedidos.setFont(new Font("Arial", Font.BOLD, 45)); 
        grid.add(crearTarjeta("Pedidos Totales", lblTotalPedidos, new Color(48, 60, 26)));

        lblIngresos = new JLabel("$0.00", SwingConstants.CENTER);
        lblIngresos.setFont(new Font("Arial", Font.BOLD, 45));
        grid.add(crearTarjeta("Ingresos Generados", lblIngresos, new Color(78, 59, 49)));

        // -- FILA 2 --
        lblClientes = new JLabel("0", SwingConstants.CENTER);
        lblClientes.setFont(new Font("Arial", Font.BOLD, 45));
        grid.add(crearTarjeta("Usuarios Registrados", lblClientes, new Color(114, 155, 121)));

        lblPendientes = new JLabel("0", SwingConstants.CENTER);
        lblPendientes.setFont(new Font("Arial", Font.BOLD, 45));
        grid.add(crearTarjeta("Pedidos Pendientes", lblPendientes, new Color(210, 180, 140)));

        lblTopProductos = new JLabel("<html>Cargando...</html>");
        lblTopProductos.setHorizontalAlignment(SwingConstants.LEFT);
        lblTopProductos.setFont(new Font("Arial", Font.PLAIN, 15)); 
        grid.add(crearTarjeta("Top 5 Productos", lblTopProductos, new Color(30, 40, 20)));

        lblTopClientes = new JLabel("<html>Cargando...</html>");
        lblTopClientes.setHorizontalAlignment(SwingConstants.LEFT);
        lblTopClientes.setFont(new Font("Arial", Font.PLAIN, 15));
        grid.add(crearTarjeta("Mejores Clientes", lblTopClientes, new Color(50, 40, 30)));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String tituloTexto, JLabel lblValor, Color colorFondo) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(colorFondo);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            new EmptyBorder(10, 20, 10, 20) 
        ));

        JLabel titulo = new JLabel(tituloTexto, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(colorFondo.equals(new Color(210, 180, 140)) ? Color.BLACK : Color.WHITE);

        lblValor.setForeground(colorFondo.equals(new Color(210, 180, 140)) ? Color.BLACK : Color.WHITE);

        card.add(titulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }
}