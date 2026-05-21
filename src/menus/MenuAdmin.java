package menus;

import javax.swing.JOptionPane;
import BLL.Tarea;
import DLL.TareaDLL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuAdmin implements Menu {

    private static final String[] Ejes = { "Agua", "Energía", "Residuos", "Insumos", "Gestión Integral" };

    @Override
    public void mostrar() {
        String[] opciones = {
                "Cargar Nueva Tarea",
                "Validar Evidencias",
                "Gestionar Eventos",
                "Ver Dashboard",
                "Cerrar Sesión"
        };

        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido, Administrador EcoTrack",
                    "Panel Administrador",
                    0, JOptionPane.DEFAULT_OPTION, null,
                    opciones, opciones[0]);

            switch (seleccion) {
                case 0 -> cargarTarea();
                case 1 -> validarEvidencias();
                case 2 -> gestionarEventos();
                case 3 -> verDashboard();
            }

        } while (seleccion != 4 && seleccion != JOptionPane.CLOSED_OPTION);
    }

    private void cargarTarea() {
        JOptionPane.showMessageDialog(null,
                "Completá los campos a continuación.",
                "Cargar Tarea", JOptionPane.INFORMATION_MESSAGE);

        String titulo = JOptionPane.showInputDialog(null, "Título de la tarea:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        if (titulo == null || titulo.trim().isEmpty()) return;

        String descripcion = JOptionPane.showInputDialog(null, "Descripción:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        if (descripcion == null) return;

        String puntosStr = JOptionPane.showInputDialog(null, "Puntos a otorgar:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        if (puntosStr == null || puntosStr.contains("-")) return;

        String fechaStr = JOptionPane.showInputDialog(null, "Fecha de caducidad (dd/mm/aaaa):", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        if (fechaStr == null) return;

        int ejeSeleccionado = JOptionPane.showOptionDialog(null,
                "Seleccioná el eje temático:",
                "Eje Temático",
                0, JOptionPane.QUESTION_MESSAGE, null,
                Ejes, Ejes[0]);

        if (ejeSeleccionado == JOptionPane.CLOSED_OPTION) return;

        String eje = Ejes[ejeSeleccionado];

        try {
            int puntos = Integer.parseInt(puntosStr);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date fechaCaducidad = sdf.parse(fechaStr);

            Tarea nuevaTarea = new Tarea(titulo, descripcion, puntos, fechaCaducidad, eje);
            TareaDLL tareaDLL = new TareaDLL();

            if (tareaDLL.crearTarea(nuevaTarea)) {
                JOptionPane.showMessageDialog(null,
                        "Cargaste la tarea correctamente y ya está disponible para los usuarios.",
                        "Cargar Tarea", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error, por favor, intentá nuevamente.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Los puntos deben ser un valor numérico.", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error: Formato de fecha incorrecto. Debe ser dd/mm/aaaa.", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarEvidencias() {
        JOptionPane.showMessageDialog(null,
                "── Evidencias Pendientes de Revisión ──\n\n" +
                        "Usuario : juan.perez@gmail.com\n" +
                        "Tarea   : Separación de residuos domiciliarios\n" +
                        "Archivo : foto_residuos.jpg\n" +
                        "Fecha   : 14/04/2026",
                "Validar Evidencias", JOptionPane.INFORMATION_MESSAGE);

        String[] acciones = { "Aprobar", "Rechazar", "Volver" };
        int accion = JOptionPane.showOptionDialog(null,
                "¿Qué desea hacer con esta evidencia?",
                "Validar Evidencia",
                0, JOptionPane.QUESTION_MESSAGE, null,
                acciones, acciones[0]);

        switch (accion) {
            case 0 -> JOptionPane.showMessageDialog(null,
                    "✔ Evidencia aprobada. Se acreditaron 50 puntos al usuario.",
                    "Validar Evidencias", JOptionPane.INFORMATION_MESSAGE);
            case 1 -> {
                JOptionPane.showInputDialog(null, "Motivo del rechazo:", "Rechazar Evidencia",
                        JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null,
                        "✘ Evidencia rechazada. Se notificó al usuario.",
                        "Validar Evidencias", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void gestionarEventos() {
        String[] opciones = { "Crear Nuevo Evento", "Ver Eventos Existentes", "Volver" };
        int seleccion = JOptionPane.showOptionDialog(null,
                "── Gestión de Eventos ──",
                "Eventos",
                0, JOptionPane.DEFAULT_OPTION, null,
                opciones, opciones[0]);

        switch (seleccion) {
            case 0 -> crearEvento();
            case 1 -> verEventosAdmin();
        }
    }

    private void crearEvento() {
        JOptionPane.showInputDialog(null, "Título del evento:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Fecha y hora (dd/mm/aaaa hh:mm):", "Nuevo Evento",
                JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Ubicación:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Cupo máximo:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Descripción:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);

        JOptionPane.showMessageDialog(null,
                "✔ Evento publicado. Los usuarios recibirán una notificación.",
                "Nuevo Evento", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verEventosAdmin() {
        JOptionPane.showMessageDialog(null,
                " Eventos Registrados \n\n" +
                        "1. Jornada de reciclaje  — 20/04/2026  — Plaza Belgrano  (Cupo: 30)\n" +
                        "2. Taller de compostaje  — 05/05/2026  — Online           (Cupo: 100)",
                "Eventos Existentes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verDashboard() {
        JOptionPane.showMessageDialog(null,
                " Dashboard EcoTrack \n\n" +
                        "Usuarios registrados  : 128\n" +
                        "Tareas activas        : 5\n" +
                        "Evidencias pendientes : 12\n" +
                        "Eventos próximos      : 2\n\n" +
                        "Evidencias esta semana por eje:\n" +
                        "  Agua             : 18\n" +
                        "  Energía          : 24\n" +
                        "  Residuos         : 31\n" +
                        "  Insumos          : 14\n" +
                        "  Gestión Integral : 9",
                "Dashboard", JOptionPane.INFORMATION_MESSAGE);
    }
}