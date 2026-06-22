package vista;

import BLL.Hito;
import DLL.HitoDLL;
import DLL.UsuarioController;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

/**
 * Progreso del usuario: muestra los puntos acumulados y una JTable con los
 * hitos del sistema (HitoDLL), indicando cuáles ya alcanzó y cuántos puntos
 * le faltan para los siguientes.
 */
public class ProgresoFrame extends JFrame {

    private final int idUsuario;
    private final String email;

    public ProgresoFrame(int idUsuario, String email) {
        super("EcoTrack — Mi progreso");
        this.idUsuario = idUsuario;
        this.email = email;

        UI.configurarFrame(this, 640, 520);
        setLayout(new BorderLayout());

        int puntos = new UsuarioController().obtenerPuntosUsuario(idUsuario);

        add(UI.cabecera("Mi progreso", "Seguí tu camino en EcoTrack"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(construirSaldo(puntos), BorderLayout.NORTH);
        centro.add(construirTablaHitos(puntos), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel construirSaldo(int puntos) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(UI.BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UI.BORDE),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)));

        JLabel etiqueta = new JLabel("Puntos acumulados");
        etiqueta.setFont(UI.SUBTITULO);
        etiqueta.setForeground(UI.TEXTO_SUAVE);

        JLabel valor = new JLabel(puntos + " pts", SwingConstants.RIGHT);
        valor.setFont(new Font("SansSerif", Font.BOLD, 22));
        valor.setForeground(UI.VERDE);

        card.add(etiqueta, BorderLayout.WEST);
        card.add(valor, BorderLayout.EAST);
        return card;
    }

    private JPanel construirTablaHitos(int puntos) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(UI.FONDO);
        panel.add(UI.titulo("Hitos"), BorderLayout.NORTH);

        DefaultTableModel modelo = UI.modeloNoEditable(
                new String[] { "Hito", "Puntos requeridos", "Estado" });

        List<Hito> hitos = new HitoDLL().obtenerHitos();
        for (Hito h : hitos) {
            String estado = (puntos >= h.getPuntosRequeridos())
                    ? "Alcanzado"
                    : "Faltan " + (h.getPuntosRequeridos() - puntos) + " pts";
            modelo.addRow(new Object[] { h.getNombre(), h.getPuntosRequeridos(), estado });
        }

        JTable tabla = new JTable(modelo);
        panel.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);
        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuUsuarioFrame(idUsuario, email).setVisible(true);
            dispose();
        });
        barra.add(btnVolver);
        return barra;
    }
}
