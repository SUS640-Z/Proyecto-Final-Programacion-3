package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class DashboardView extends JPanel {
    public JLabel lblTotalPedidos, lblIngresos, lblClientes, lblPendientes;

    public DashboardView() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(15, 19, 9));
        setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel titulo = new JLabel("Estadísticas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2, 2, 30, 30));
        grid.setOpaque(false);

        lblTotalPedidos = new JLabel("0", SwingConstants.CENTER);
        grid.add(crearTarjeta("Pedidos Totales", lblTotalPedidos, new Color(48, 60, 26)));

        lblIngresos = new JLabel("$0.00", SwingConstants.CENTER);
        grid.add(crearTarjeta("Ingresos Generados", lblIngresos, new Color(78, 59, 49)));

        lblClientes = new JLabel("0", SwingConstants.CENTER);
        grid.add(crearTarjeta("Usuarios registrados", lblClientes, new Color(114, 155, 121)));

        lblPendientes = new JLabel("0", SwingConstants.CENTER);
        grid.add(crearTarjeta("Pedidos Pendientes", lblPendientes, new Color(210, 180, 140)));

        add(grid, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(String tituloTexto, JLabel lblValor, Color colorFondo) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(colorFondo);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 2),
            new EmptyBorder(30, 20, 30, 20)
        ));

        JLabel titulo = new JLabel(tituloTexto, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(colorFondo.equals(new Color(210, 180, 140)) ? Color.BLACK : Color.WHITE);

        lblValor.setFont(new Font("Arial", Font.BOLD, 55));
        lblValor.setForeground(colorFondo.equals(new Color(210, 180, 140)) ? Color.BLACK : Color.WHITE);

        card.add(titulo, BorderLayout.NORTH);
        card.add(lblValor, BorderLayout.CENTER);
        return card;
    }
}