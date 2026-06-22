package vista;
import DAO.DashboardDAO;
import DAO.DashboardDAOImpl;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class DashboardFrame extends JFrame {

    public DashboardFrame() {
        super("EcoTrack — Dashboard");
        UI.configurarFrame(this, 720, 560);
        setLayout(new BorderLayout());

        DashboardDAO dash = new DashboardDAOImpl();

        add(UI.cabecera("Dashboard", "Indicadores generales de EcoTrack"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 18));
        centro.setBackground(UI.FONDO);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        centro.add(construirTarjetas(dash), BorderLayout.NORTH);
        centro.add(construirTablaEjes(dash), BorderLayout.CENTER);
        centro.add(construirBotones(), BorderLayout.SOUTH);

        add(centro, BorderLayout.CENTER);
    }

    private JPanel construirTarjetas(DashboardDAO dash) {
        JPanel grid = new JPanel(new GridLayout(1, 4, 14, 0));
        grid.setBackground(UI.FONDO);
        grid.add(tarjeta("Usuarios", dash.getCantidadUsuarios()));
        grid.add(tarjeta("Tareas activas", dash.getTareasActivas()));
        grid.add(tarjeta("Evidencias pend.", dash.getEvidenciasPendientes()));
        grid.add(tarjeta("Eventos próximos", dash.getEventosProximos()));
        return grid;
    }

    private JPanel tarjeta(String titulo, int valor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UI.BLANCO);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UI.BORDE),
                BorderFactory.createEmptyBorder(16, 12, 16, 12)));

        JLabel lValor = new JLabel(String.valueOf(valor), SwingConstants.CENTER);
        lValor.setFont(new Font("SansSerif", Font.BOLD, 30));
        lValor.setForeground(UI.VERDE);
        lValor.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lTit = new JLabel(titulo, SwingConstants.CENTER);
        lTit.setFont(UI.SUBTITULO);
        lTit.setForeground(UI.TEXTO_SUAVE);
        lTit.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lValor);
        card.add(lTit);
        return card;
    }

    private JPanel construirTablaEjes(DashboardDAO dash) {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(UI.FONDO);
        panel.add(UI.titulo("Evidencias pendientes por eje"), BorderLayout.NORTH);

        DefaultTableModel modelo = UI.modeloNoEditable(new String[] { "Eje temático", "Cantidad" });
        // getEvidenciasPorEje() devuelve líneas con formato "  Eje : cantidad"
        String texto = dash.getEvidenciasPorEje();
        for (String linea : texto.split("\n")) {
            if (linea == null || !linea.contains(":")) continue;
            String[] partes = linea.split(":");
            String eje = partes[0].trim();
            String cant = partes.length > 1 ? partes[1].trim() : "0";
            if (!eje.isEmpty()) {
                modelo.addRow(new Object[] { eje, cant });
            }
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
            new MenuAdminFrame().setVisible(true);
            dispose();
        });
        barra.add(btnVolver);
        return barra;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(720, 560);
    }
}
