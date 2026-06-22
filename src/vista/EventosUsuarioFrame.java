package vista;

import BLL.Evento;
import DLL.AsistenciaEventoDLL;
import DLL.EventoDLL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Eventos disponibles para el usuario. Lista los eventos en una JTable y
 * permite inscribirse al evento seleccionado (AsistenciaEventoDLL).
 */
public class EventosUsuarioFrame extends JFrame {

    private final int idUsuario;
    private final String email;

    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Título", "Fecha", "Cupo", "Ubicación" });
    private final JTable tabla = new JTable(modelo);
    private final EventoDLL eventoDLL = new EventoDLL();
    private List<Evento> eventos = new ArrayList<>();

    public EventosUsuarioFrame(int idUsuario, String email) {
        super("EcoTrack — Eventos");
        this.idUsuario = idUsuario;
        this.email = email;

        UI.configurarFrame(this, 780, 520);
        setLayout(new BorderLayout());

        add(UI.cabecera("Eventos", "Inscribite a los próximos eventos de EcoTrack"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarEventos();
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnInscribir = UI.botonPrimario("Inscribirme");
        btnInscribir.addActionListener(e -> inscribirse());

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuUsuarioFrame(idUsuario, email).setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnInscribir);
        return barra;
    }

    private void cargarEventos() {
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        eventos = eventoDLL.listarEventos();
        for (Evento ev : eventos) {
            String fecha = (ev.getFecha() != null) ? sdf.format(ev.getFecha()) : "—";
            modelo.addRow(new Object[] {
                    ev.getIdEvento(), ev.getTitulo(), fecha, ev.getCupoMaximo(), ev.getUbicacion() });
        }
    }

    private void inscribirse() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccioná un evento de la tabla.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Evento ev = eventos.get(fila);
        AsistenciaEventoDLL asistencia = new AsistenciaEventoDLL();
        if (asistencia.registrarAsistencia(idUsuario, ev.getIdEvento())) {
            JOptionPane.showMessageDialog(this,
                    "Inscripción confirmada a: " + ev.getTitulo(),
                    "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la inscripción. Quizás ya estás inscripto.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
