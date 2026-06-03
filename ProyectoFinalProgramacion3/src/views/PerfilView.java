package views;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class PerfilView extends JFrame {

    private JPanel contentPane;

    // Método main para ejecutar esta vista de forma 100% independiente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PerfilView();
        });
    }

    public PerfilView() {
        setTitle("Saturnbucks - Mi Perfil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 850, 650); 
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(15, 19, 9));
        setContentPane(contentPane);

        generarMenu();
        generarContenidoPerfil();
        generarFooter();

        setVisible(true);
    }

    private void generarMenu() {
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(15, 19, 9)); 
        panelMenu.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(48, 60, 26))); 

        JLabel lblInicio = crearItemMenu("Inicio");
        JLabel lblOrdenes = crearItemMenu("Órdenes");
        JLabel lblCerrar = crearItemMenu("Cerrar Sesión");
        
        JLabel lblSeparador1 = new JLabel("  |  ");
        lblSeparador1.setForeground(Color.DARK_GRAY);
        JLabel lblSeparador2 = new JLabel("  |  ");
        lblSeparador2.setForeground(Color.DARK_GRAY);

        panelMenu.add(lblInicio);
        panelMenu.add(lblSeparador1);
        panelMenu.add(lblOrdenes);
        panelMenu.add(lblSeparador2);
        panelMenu.add(lblCerrar);
        
        lblCerrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { 
                JOptionPane.showMessageDialog(null, "Sesión cerrada exitosamente", "Cierre de Sesión", JOptionPane.INFORMATION_MESSAGE);
            }
        });

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

    private void generarContenidoPerfil() {
        JPanel contenedorPrincipal = new JPanel();
        contenedorPrincipal.setLayout(new BoxLayout(contenedorPrincipal, BoxLayout.Y_AXIS));
        contenedorPrincipal.setBackground(new Color(15, 19, 9));
        contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // --- Datos de prueba independientes ---
        String nombreUsuario = "Juan Pérez"; 
        String correo = "juan.perez@galaxia.com";
        String fechaNac = "12 / 04 / 1998";
        String genero = "Masculino";
        String telefono = "+52 612 123 4567";

        // ================= SECCIÓN AVATAR Y NOMBRE =================
        JPanel panelAvatar = new JPanel();
        panelAvatar.setLayout(new BoxLayout(panelAvatar, BoxLayout.Y_AXIS));
        panelAvatar.setBackground(new Color(15, 19, 9));
        panelAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Uso del componente interno temporal
        AvatarCircularLocal avatar = new AvatarCircularLocal(nombreUsuario, 100);
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelAvatar.add(avatar);
        panelAvatar.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblNombreCompleto = new JLabel(nombreUsuario.toUpperCase());
        lblNombreCompleto.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblNombreCompleto.setForeground(new Color(210, 180, 140));
        lblNombreCompleto.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelAvatar.add(lblNombreCompleto);

        contenedorPrincipal.add(panelAvatar);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        // ================= SECCIÓN INFORMACIÓN PERSONAL =================
        JPanel panelInfo = new JPanel(new GridBagLayout());
        panelInfo.setBackground(new Color(20, 25, 13)); 
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(48, 60, 26), 1, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panelInfo.setMaximumSize(new Dimension(700, 150));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 15, 6, 15);
        gbc.anchor = GridBagConstraints.WEST;

        agregarCampoInfo(panelInfo, gbc, 0, 0, "Correo Electrónico:", correo);
        agregarCampoInfo(panelInfo, gbc, 1, 0, "Fecha de Nacimiento:", fechaNac);
        agregarCampoInfo(panelInfo, gbc, 0, 1, "Género:", genero);
        agregarCampoInfo(panelInfo, gbc, 1, 1, "Teléfono móvil:", telefono);

        contenedorPrincipal.add(panelInfo);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        // ================= SECCIÓN DIRECCIONES =================
        JLabel lblTituloDirecciones = new JLabel("Mis Direcciones de Envío");
        lblTituloDirecciones.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblTituloDirecciones.setForeground(Color.WHITE);
        lblTituloDirecciones.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorPrincipal.add(lblTituloDirecciones);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel panelTarjetas = new JPanel(new GridLayout(0, 2, 15, 15));
        panelTarjetas.setBackground(new Color(15, 19, 9));
        panelTarjetas.setMaximumSize(new Dimension(700, 160));

        panelTarjetas.add(crearTarjetaDireccion("Hogar 🏠", "Calle de las Estrellas #412, Col. Cosmos, La Paz, B.C.S."));
        panelTarjetas.add(crearTarjetaDireccion("Trabajo 💼", "Av. Revolución Metálica #10, Zona Central, La Paz, B.C.S."));

        contenedorPrincipal.add(panelTarjetas);
        contenedorPrincipal.add(Box.createRigidArea(new Dimension(0, 25)));

        // ================= BOTÓN AÑADIR DIRECCIÓN =================
        JButton btnAgregarDir = new JButton("Añadir Dirección");
        btnAgregarDir.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        btnAgregarDir.setBackground(new Color(48, 60, 26));
        btnAgregarDir.setForeground(Color.WHITE);
        btnAgregarDir.setBorder(new LineBorder(Color.GRAY, 2, true));
        btnAgregarDir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarDir.setPreferredSize(new Dimension(220, 40));
        btnAgregarDir.setMaximumSize(new Dimension(220, 40));
        btnAgregarDir.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btnAgregarDir.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Formulario para nueva dirección en desarrollo.");
        });

        contenedorPrincipal.add(btnAgregarDir);

        JScrollPane scroll = new JScrollPane(contenedorPrincipal);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(15, 19, 9));
        
        contentPane.add(scroll, BorderLayout.CENTER);
    }

    private void agregarCampoInfo(JPanel panel, GridBagConstraints gbc, int col, int fila, String etiqueta, String valor) {
        gbc.gridx = col * 2;
        gbc.gridy = fila;
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        lblEtiqueta.setForeground(new Color(210, 180, 140));
        panel.add(lblEtiqueta, gbc);

        gbc.gridx = (col * 2) + 1;
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
        lblValor.setForeground(Color.WHITE);
        panel.add(lblValor, gbc);
    }

    private JPanel crearTarjetaDireccion(String etiqueta, String infoDireccion) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(new Color(25, 32, 16)); 
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        JLabel lblTag = new JLabel(etiqueta);
        lblTag.setFont(new Font("Arial", Font.BOLD, 15));
        lblTag.setForeground(new Color(210, 180, 140));
        lblTag.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea txtDir = new JTextArea(infoDireccion);
        txtDir.setFont(new Font("Arial", Font.PLAIN, 13));
        txtDir.setForeground(Color.LIGHT_GRAY);
        txtDir.setEditable(false);
        txtDir.setLineWrap(true);
        txtDir.setWrapStyleWord(true);
        txtDir.setBackground(new Color(0,0,0,0)); 
        txtDir.setAlignmentX(Component.LEFT_ALIGNMENT);

        tarjeta.add(lblTag);
        tarjeta.add(Box.createRigidArea(new Dimension(0, 6)));
        tarjeta.add(txtDir);

        return tarjeta;
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

    // ================= COMPONENTE AVATAR INTERNO (LOCAL) =================
    // Duplicado aquí abajo de forma privada para que el archivo sea autónomo.
    private class AvatarCircularLocal extends JComponent {
        private String nombre;
        private Color fondoSaturn = new Color(48, 60, 26); 

        public AvatarCircularLocal(String nombre, int tamano) {
            this.nombre = nombre;
            Dimension dim = new Dimension(tamano, tamano);
            setPreferredSize(dim);
            setSize(dim);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int diametro = Math.min(getWidth(), getHeight());
            int x = (getWidth() - diametro) / 2;
            int y = (getHeight() - diametro) / 2;

            g2.setColor(fondoSaturn);
            g2.fillOval(x, y, diametro, diametro);

            String inicial = " ";
            if (nombre != null && !nombre.trim().isEmpty()) {
                inicial = nombre.trim().substring(0, 1).toUpperCase();
            }

            g2.setColor(new Color(210, 180, 140)); 
            g2.setFont(new Font("Times New Roman", Font.BOLD, diametro / 2));

            FontMetrics fm = g2.getFontMetrics();
            int letraX = x + (diametro - fm.stringWidth(inicial)) / 2;
            int letraY = y + ((diametro - fm.getHeight()) / 2) + fm.getAscent();
            
            g2.drawString(inicial, letraX, letraY);
            g2.dispose();
        }
    }
}