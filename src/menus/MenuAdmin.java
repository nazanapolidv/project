package menus;

import javax.swing.JOptionPane;

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

        } while (seleccion != 4);
    }

    private void cargarTarea() {
        JOptionPane.showMessageDialog(null,
                "── Cargar Nueva Tarea ──\nComplete los campos en los próximos pasos.",
                "Cargar Tarea", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showInputDialog(null, "Título de la tarea:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Descripción:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Puntos a otorgar:", "Cargar Tarea", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showInputDialog(null, "Fecha de caducidad (dd/mm/aaaa):", "Cargar Tarea",
                JOptionPane.PLAIN_MESSAGE);

        JOptionPane.showOptionDialog(null,
                "Seleccione el eje temático (Sello Verde GCBA):",
                "Eje Temático",
                0, JOptionPane.QUESTION_MESSAGE, null,
                Ejes, Ejes[0]);

        JOptionPane.showMessageDialog(null,
                "✔ Tarea publicada exitosamente para todos los usuarios.",
                "Cargar Tarea", JOptionPane.INFORMATION_MESSAGE);
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
