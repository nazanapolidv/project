package menus;

import javax.swing.JOptionPane;
import BLL.Tarea;
import BLL.Evento;
import DLL.TareaDLL;
import DLL.EventoDLL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuAdmin implements Menu {

    private static final String[] Ejes = { "Agua", "Energía", "Residuos", "Insumos", "Gestión Integral" };

    @Override
    public void mostrar() {
        String[] opciones = {
                "Cargar nueva tarea",
                "Validar evidencias",
                "Crear evento",
                "Ver Dashboard",
                "Cerrar sesión"
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
                case 2 -> crearEvento();
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

    private void crearEvento() {
        String titulo = JOptionPane.showInputDialog(null, "Título del evento:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        if (titulo == null || titulo.trim().isEmpty()) return;

        String fechaStr = JOptionPane.showInputDialog(null, "Fecha y hora (dd/MM/yyyy HH:mm):", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        if (fechaStr == null) return;

        String ubicacion = JOptionPane.showInputDialog(null, "Ubicación:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        if (ubicacion == null) return;

        String cupoStr = JOptionPane.showInputDialog(null, "Cupo máximo:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        if (cupoStr == null) return;

        String descripcion = JOptionPane.showInputDialog(null, "Descripción:", "Nuevo Evento", JOptionPane.PLAIN_MESSAGE);
        if (descripcion == null) return;

        try {
            int cupoMaximo = Integer.parseInt(cupoStr);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf.setLenient(false);
            Date fecha = sdf.parse(fechaStr);

            Evento nuevoEvento = new Evento(0, titulo, fecha, cupoMaximo, ubicacion, descripcion);
            EventoDLL eventoDLL = new EventoDLL();

            if (eventoDLL.crearEvento(nuevoEvento)) {
                JOptionPane.showMessageDialog(null,
                        "Evento publicado. Los usuarios tendrán disponibles este nuevo evento.",
                        "Nuevo Evento", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Ocurrió un error al guardar el evento en la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El cupo máximo debe ser un número entero.", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error: Formato de fecha/hora incorrecto. Debe ser dd/MM/yyyy HH:mm.", "Error de Ingreso", JOptionPane.ERROR_MESSAGE);
        }
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