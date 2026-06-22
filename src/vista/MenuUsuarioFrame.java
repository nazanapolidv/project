package vista;
import DAO.UsuarioDAOImpl;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class MenuUsuarioFrame extends JFrame {

    private final int idUsuario;
    private final String email;

    public MenuUsuarioFrame(int idUsuario, String email) {
        super("EcoTrack — Panel Usuario");
        this.idUsuario = idUsuario;
        this.email = email;

        UI.configurarFrame(this, 520, 500);
        setLayout(new BorderLayout());

        add(UI.cabecera("Hola, " + (email != null ? email : "usuario"),
                "Sumá puntos completando tareas ambientales"), BorderLayout.NORTH);
        add(construirContenido(), BorderLayout.CENTER);
    }

    private JPanel construirContenido() {
        JPanel panel = UI.contenedor();
        panel.setLayout(new BorderLayout(0, 16));

        panel.add(construirTarjetaPuntos(), BorderLayout.NORTH);
        panel.add(construirOpciones(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirTarjetaPuntos() {
        int puntos = new UsuarioDAOImpl().obtenerPuntosUsuario(idUsuario);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UI.BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UI.BORDE),
                BorderFactory.createEmptyBorder(14, 18, 14, 18)));

        JLabel etiqueta = new JLabel("Puntos acumulados");
        etiqueta.setFont(UI.SUBTITULO);
        etiqueta.setForeground(UI.TEXTO_SUAVE);

        JLabel valor = new JLabel(puntos + " pts", SwingConstants.RIGHT);
        valor.setFont(new Font("SansSerif", Font.BOLD, 24));
        valor.setForeground(UI.VERDE);

        card.add(etiqueta, BorderLayout.WEST);
        card.add(valor, BorderLayout.EAST);
        return card;
    }

    private JPanel construirOpciones() {
        JPanel grid = new JPanel(new GridLayout(5, 1, 0, 12));
        grid.setBackground(UI.FONDO);

        JButton btnTareas = UI.botonPrimario("Ver tareas y subir evidencia");
        btnTareas.addActionListener(e -> abrir(new TareasUsuarioFrame(idUsuario, email)));

        JButton btnEventos = UI.botonPrimario("Ver eventos e inscribirme");
        btnEventos.addActionListener(e -> abrir(new EventosUsuarioFrame(idUsuario, email)));

        JButton btnCanje = UI.botonPrimario("Canjear puntos");
        btnCanje.addActionListener(e -> abrir(new CanjearPuntosFrame(idUsuario, email)));

        JButton btnProgreso = UI.botonPrimario("Mi progreso");
        btnProgreso.addActionListener(e -> abrir(new ProgresoFrame(idUsuario, email)));

        JButton btnSalir = UI.botonPeligro("Cerrar sesión");
        btnSalir.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        grid.add(btnTareas);
        grid.add(btnEventos);
        grid.add(btnCanje);
        grid.add(btnProgreso);
        grid.add(btnSalir);
        return grid;
    }

    private void abrir(JFrame ventana) {
        ventana.setVisible(true);
        dispose();
    }
}
