package vista;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.net.URL;

public final class UI {

    private UI() { }
    public static final Color VERDE        = new Color(0x2E7D5B);
    public static final Color VERDE_OSCURO = new Color(0x1F5A41);
    public static final Color VERDE_CLARO  = new Color(0xA5D6BC);
    public static final Color FONDO        = new Color(0xF4F7F5);
    public static final Color BLANCO       = new Color(0xFFFFFF);
    public static final Color TEXTO        = new Color(0x1E2A24);
    public static final Color TEXTO_SUAVE  = new Color(0x6B7B72);
    public static final Color PELIGRO      = new Color(0xC0492F);
    public static final Color BORDE        = new Color(0xD8E2DC);
    public static final Font TITULO    = new Font("SansSerif", Font.BOLD, 22);
    public static final Font SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font ETIQUETA  = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BOTON     = new Font("SansSerif", Font.BOLD, 14);
    public static final Font TABLA     = new Font("SansSerif", Font.PLAIN, 13);

    public static void configurarFrame(JFrame f, int ancho, int alto) {
        f.setSize(ancho, alto);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.getContentPane().setBackground(FONDO);
        URL logo = UI.class.getResource("/img/logo.png");
        if (logo != null) {
            f.setIconImage(new ImageIcon(logo).getImage());
        }
    }
    public static JPanel cabecera(String titulo, String subtitulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(VERDE_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel lTit = new JLabel(titulo);
        lTit.setFont(TITULO);
        lTit.setForeground(BLANCO);
        textos.add(lTit);

        if (subtitulo != null && !subtitulo.isEmpty()) {
            JLabel lSub = new JLabel(subtitulo);
            lSub.setFont(SUBTITULO);
            lSub.setForeground(new Color(0xCDE8DC));
            textos.add(Box.createVerticalStrut(3));
            textos.add(lSub);
        }
        p.add(textos, BorderLayout.WEST);
        return p;
    }

    public static JButton botonPrimario(String texto)   { return boton(texto, VERDE, BLANCO); }
    public static JButton botonSecundario(String texto) { return boton(texto, BLANCO, VERDE_OSCURO); }
    public static JButton botonPeligro(String texto)    { return boton(texto, PELIGRO, BLANCO); }

    private static JButton boton(String texto, Color fondo, Color frente) {
        JButton b = new JButton(texto);
        b.setFont(BOTON);
        b.setForeground(frente);
        b.setBackground(fondo);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(ETIQUETA);
        l.setForeground(TEXTO);
        return l;
    }

    public static JLabel titulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("SansSerif", Font.BOLD, 16));
        l.setForeground(VERDE_OSCURO);
        return l;
    }

    public static JTextField campo() {
        JTextField c = new JTextField(18);
        prepararCampo(c);
        return c;
    }

    public static JPasswordField campoPassword() {
        JPasswordField c = new JPasswordField(18);
        prepararCampo(c);
        return c;
    }

    private static void prepararCampo(JTextField c) {
        c.setFont(ETIQUETA);
        c.setBackground(BLANCO);
        c.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
    }

    public static JPanel contenedor() {
        JPanel p = new JPanel();
        p.setBackground(FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        return p;
    }

    public static DefaultTableModel modeloNoEditable(String[] columnas) {
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    public static JScrollPane tablaConEstilo(JTable t) {
        t.setFont(TABLA);
        t.setRowHeight(28);
        t.setGridColor(BORDE);
        t.setShowVerticalLines(false);
        t.setSelectionBackground(VERDE_CLARO);
        t.setSelectionForeground(TEXTO);
        t.setFillsViewportHeight(true);

        JTableHeader h = t.getTableHeader();
        h.setBackground(VERDE);
        h.setForeground(BLANCO);
        h.setFont(new Font("SansSerif", Font.BOLD, 13));
        h.setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(t);
        sp.setBorder(BorderFactory.createLineBorder(BORDE));
        sp.getViewport().setBackground(BLANCO);
        return sp;
    }
}
