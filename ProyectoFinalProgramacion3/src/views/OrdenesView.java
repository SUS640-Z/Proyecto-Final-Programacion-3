package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrdenesView extends JFrame {

    private JPanel contentPane;
    private JPanel pnlGridOrdenes; 
    
    JLabel lblInicio, lblPerfil, lblCerrar;

    public OrdenesView() {
        setTitle("Saturnbucks - Mis Órdenes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650);
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);

        generarMenu();
        generarContenidoOrdenes();
        generarFooter();
    }

    private void generarMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9));
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26)));

        lblInicio = crearItemMenu("Inicio");
        lblPerfil = crearItemMenu("Mi Perfil");
        lblCerrar = crearItemMenu("Cerrar Sesión");

        JLabel lblSeparador1 = new JLabel("  |  "); lblSeparador1.setForeground(Color.DARK_GRAY);
        JLabel lblSeparador2 = new JLabel("  |  "); lblSeparador2.setForeground(Color.DARK_GRAY);

        panelMenu.add(lblInicio);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblPerfil);
        panelMenu.add(lblSeparador2);
        panelMenu.add(lblCerrar);

        contentPane.add(panelMenu, BorderLayout.NORTH);
    }

    private JLabel crearItemMenu(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(210, 180, 140));
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { label.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e) { label.setForeground(new Color(210, 180, 140)); }
        });
        return label;
    }

    private void generarContenidoOrdenes() {
        JPanel contenedorPrincipal = new JPanel();
        contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
        contenedorPrincipal.setBackground(new Color(15, 19, 9));
        contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JLabel lblTitulo = new JLabel("HISTORIAL DE ÓRDENES");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblTitulo.setForeground(new Color(210, 180, 140));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorPrincipal.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("Haz clic sobre una orden para auditar los detalles y artículos adquiridos.");
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 13));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        contenedorPrincipal.add(lblSubtitulo);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        pnlGridOrdenes = new JPanel(new GridLayout(0, 2, 20, 20));
        pnlGridOrdenes.setBackground(new Color(15, 19, 9));
        contenedorPrincipal.add(pnlGridOrdenes);

        JScrollPane scroll = new JScrollPane(contenedorPrincipal);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(15, 19, 9));

        contentPane.add(scroll, BorderLayout.CENTER);
    }

    private void generarFooter() {
        JPanel panelFooter = new JPanel();
        panelFooter.setBackground(new Color(15, 19, 9));
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        JLabel lblFooter = new JLabel("© 2026 Saturnbucks Co. | La Paz, B.C.S.");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFooter.setForeground(Color.GRAY);
        lblFooter.setHorizontalAlignment(JLabel.CENTER);

        panelFooter.add(lblFooter);
        contentPane.add(panelFooter, BorderLayout.SOUTH);
    }

    public JLabel getLblInicio() { return lblInicio; }
    public JLabel getLblPerfil() { return lblPerfil; }
    public JLabel getLblCerrar() { return lblCerrar; }
    public JPanel getPnlGridOrdenes() { return pnlGridOrdenes; }
}