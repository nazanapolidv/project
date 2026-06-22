package vista;

import BLL.Evidencia;
import DAO.EvidenciaDAO;
import DAO.EvidenciaDAOImpl;

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
 * Validación de evidencias para el administrador. Muestra las evidencias
 * pendientes en una JTable y permite Aprobar o Rechazar la fila seleccionada,
 * delegando en EvidenciaDAO (patrón DAO ya aplicado en el proyecto).
 */
public class ValidarEvidenciasFrame extends JFrame {

    private final EvidenciaDAO evidenciaDAO = new EvidenciaDAOImpl();
    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Usuario", "Tarea", "Archivo", "Puntos", "Fecha" });
    private final JTable tabla = new JTable(modelo);
    private List<Evidencia> pendientes = new ArrayList<>();

    public ValidarEvidenciasFrame() {
        super("EcoTrack — Validar evidencias");
        UI.configurarFrame(this, 820, 520);
        setLayout(new BorderLayout());

        add(UI.cabecera("Validar evidencias", "Aprobá o rechazá las evidencias pendientes"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarPendientes();
    }

    private JPanel construirBotones() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnAprobar = UI.botonPrimario("Aprobar");
        btnAprobar.addActionListener(e -> procesar(true));

        JButton btnRechazar = UI.botonPeligro("Rechazar");
        btnRechazar.addActionListener(e -> procesar(false));

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuAdminFrame().setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnRechazar);
        barra.add(btnAprobar);
        return barra;
    }

    private void cargarPendientes() {
        modelo.setRowCount(0);
        pendientes = evidenciaDAO.obtenerEvidenciasPendientes();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Evidencia ev : pendientes) {
            String fecha = (ev.getFechaSubida() != null) ? sdf.format(ev.getFechaSubida()) : "—";
            modelo.addRow(new Object[] {
                    ev.getIdEvidencia(),
                    ev.getNombreCliente(),
                    ev.getTituloTarea(),
                    ev.getArchivoUrl(),
                    ev.getPuntosTarea(),
                    fecha });
        }

        if (pendientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay evidencias pendientes de revisión.",
                    "Sin pendientes", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void procesar(boolean aprobado) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccioná una evidencia de la tabla primero.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Evidencia ev = pendientes.get(fila);
        int puntos = aprobado ? Math.max(ev.getPuntosTarea(), 0) : 0;

        if (!aprobado) {
            String motivo = JOptionPane.showInputDialog(this, "Motivo del rechazo (opcional):",
                    "Rechazar evidencia", JOptionPane.PLAIN_MESSAGE);
            if (motivo == null) return; // canceló
        }

        boolean ok = evidenciaDAO.procesarEvidencia(ev.getIdEvidencia(), ev.getIdCliente(), puntos, aprobado);
        if (ok) {
            String msg = aprobado
                    ? "Evidencia aprobada. Se otorgaron " + puntos + " puntos al usuario."
                    : "Evidencia rechazada.";
            JOptionPane.showMessageDialog(this, msg, "Listo", JOptionPane.INFORMATION_MESSAGE);
            cargarPendientes();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo procesar la evidencia en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
