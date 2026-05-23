package menus;

import javax.swing.JOptionPane;
import DLL.DashboardDLL;
import BLL.Tarea;
import BLL.Evento;
import BLL.Evidencia;
import DLL.EvidenciaDLL;
import DLL.TareaDLL;
import DLL.EventoDLL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        EvidenciaDLL evidenciaDLL = new EvidenciaDLL();
        List<Evidencia> pendientes = evidenciaDLL.obtenerEvidenciasPendientes();

        if (pendientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "No hay evidencias pendientes de revisión.", 
                "Validar Evidencias", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Evidencia ev : pendientes) {
            String mensaje = "── Evidencias Pendientes ──\n\n" +
                    "Cliente : " + ev.getNombreCliente() + "\n" +
                    "Tarea   : " + ev.getTituloTarea() + "\n" +
                    "Puntos  : " + ev.getPuntosTarea() + "\n" +
                    "Archivo : " + ev.getArchivoUrl() + "\n" +
                    "Fecha   : " + ev.getFechaSubida() + "\n\n" +
                    "¿Qué desea hacer con esta evidencia?";

            String[] acciones = { "Aprobar", "Rechazar", "Salir de revisión" };
            int accion = JOptionPane.showOptionDialog(null,
                    mensaje,
                    "Validar Evidencia",
                    0, JOptionPane.QUESTION_MESSAGE, null,
                    acciones, acciones[0]);

            if (accion == 0) { 
                if (evidenciaDLL.procesarEvidencia(ev.getIdEvidencia(), ev.getIdCliente(), ev.getPuntosTarea(), true)) {
                    JOptionPane.showMessageDialog(null,
                            "Evidencia aprobada. Se sumaron " + ev.getPuntosTarea() + " puntos al usuario.",
                            "Validar Evidencias", JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (accion == 1) {
                String motivo = JOptionPane.showInputDialog(null, "Motivo del rechazo:", "Rechazar Evidencia", JOptionPane.PLAIN_MESSAGE);
                if (motivo != null) { 
                    if (evidenciaDLL.procesarEvidencia(ev.getIdEvidencia(), ev.getIdCliente(), 0, false)) {
                        JOptionPane.showMessageDialog(null,
                                "Evidencia rechazada. (Motivo: " + motivo + ")",
                                "Validar Evidencias", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (accion == JOptionPane.CLOSED_OPTION) {
                break;
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
        DashboardDLL dashDLL = new DashboardDLL();

        int usuarios = dashDLL.getCantidadUsuarios();
        int tareasActivas = dashDLL.getTareasActivas();
        int evidenciasPend = dashDLL.getEvidenciasPendientes();
        int eventosProximos = dashDLL.getEventosProximos();
        String metricasEje = dashDLL.getEvidenciasPorEje();

        String mensajeDashboard = " Dashboard EcoTrack \n\n" +
                "Usuarios registrados  : " + usuarios + "\n" +
                "Tareas activas        : " + tareasActivas + "\n" +
                "Evidencias pendientes : " + evidenciasPend + "\n" +
                "Eventos próximos      : " + eventosProximos + "\n\n" +
                "Evidencias pendientes esta semana por eje:\n" +
                metricasEje;

        JOptionPane.showMessageDialog(null,
                mensajeDashboard,
                "Dashboard General", JOptionPane.INFORMATION_MESSAGE);
    }
}