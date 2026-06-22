package vista;
import BLL.Evidencia;
import BLL.Tarea;
import DAO.EvidenciaDAO;
import DAO.EvidenciaDAOImpl;
import DAO.TareaDAO;
import DAO.TareaDAOImpl;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TareasUsuarioFrame extends JFrame {

    private final int idUsuario;
    private final String email;

    private final DefaultTableModel modelo = UI.modeloNoEditable(
            new String[] { "ID", "Título", "Eje", "Puntos", "Vence" });
    private final JTable tabla = new JTable(modelo);
    private final JTextField txtArchivo = UI.campo();
    private final TareaDAO tareaDAO = new TareaDAOImpl();
    private List<Tarea> tareas = new ArrayList<>();

    public TareasUsuarioFrame(int idUsuario, String email) {
        super("EcoTrack — Tareas disponibles");
        this.idUsuario = idUsuario;
        this.email = email;

        UI.configurarFrame(this, 780, 540);
        setLayout(new BorderLayout());

        add(UI.cabecera("Tareas disponibles", "Elegí una tarea y subí tu evidencia"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 16));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        centro.add(UI.tablaConEstilo(tabla), BorderLayout.CENTER);
        centro.add(construirPanelEvidencia(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
        cargarTareas();
    }

    private JPanel construirPanelEvidencia() {
        JPanel sur = new JPanel(new BorderLayout(0, 10));
        sur.setBackground(UI.FONDO);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        fila.setBackground(UI.FONDO);
        fila.add(UI.etiqueta("Archivo de evidencia:"));
        fila.add(txtArchivo);
        sur.add(fila, BorderLayout.CENTER);

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        barra.setBackground(UI.FONDO);

        JButton btnSubir = UI.botonPrimario("Subir evidencia");
        btnSubir.addActionListener(e -> subirEvidencia());

        JButton btnVolver = UI.botonSecundario("Volver");
        btnVolver.addActionListener(e -> {
            new MenuUsuarioFrame(idUsuario, email).setVisible(true);
            dispose();
        });

        barra.add(btnVolver);
        barra.add(btnSubir);
        sur.add(barra, BorderLayout.SOUTH);
        return sur;
    }

    private void cargarTareas() {
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tareas = tareaDAO.listarTareas();
        for (Tarea t : tareas) {
            String vence = (t.getFechaCaducidad() != null) ? sdf.format(t.getFechaCaducidad()) : "—";
            modelo.addRow(new Object[] {
                    t.getIdTarea(), t.getTitulo(), t.getEje(), t.getPuntosOtorgados(), vence });
        }
    }

    private void subirEvidencia() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccioná la tarea que completaste en la tabla.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ruta = txtArchivo.getText().trim();
        if (ruta.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Indicá el nombre o la ruta del archivo (ej: panel_solar.png).",
                    "Falta el archivo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Tarea tarea = tareas.get(fila);

        Evidencia evidencia = new Evidencia();
        evidencia.setIdCliente(idUsuario);
        evidencia.setIdTarea(tarea.getIdTarea());
        evidencia.setArchivoUrl(ruta);

        EvidenciaDAO evidenciaDAO = new EvidenciaDAOImpl();
        if (evidenciaDAO.agregarEvidencia(evidencia)) {
            JOptionPane.showMessageDialog(this,
                    "Evidencia registrada con éxito.\nEstado: Pendiente de revisión.",
                    "Envío exitoso", JOptionPane.INFORMATION_MESSAGE);
            txtArchivo.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar la evidencia en la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
