package menus;

import javax.swing.JOptionPane;

public class MenuUsuario implements Menu {

    @Override
    public void mostrar() {
        String[] opciones = {
                "Ver Tareas Disponibles",
                "Subir Evidencia",
                "Mi Progreso y Puntos",
                "Ver Eventos",
                "Canjear Puntos",
                "Cerrar Sesión"
        };

        int seleccion;
        do {
            seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a EcoTrack  |  Puntos acumulados: 150 pts",
                    "Panel Usuario",
                    0, JOptionPane.DEFAULT_OPTION, null,
                    opciones, opciones[0]);

            switch (seleccion) {
                case 0 -> verTareas();
                case 1 -> subirEvidencia();
                case 2 -> verProgreso();
                case 3 -> verEventos();
                case 4 -> canjearPuntos();
            }

        } while (seleccion != 5);
    }

    private void verTareas() {
        JOptionPane.showMessageDialog(null,
                " Tareas Disponibles esta Semana \n\n" +
                        "1. [Residuos]  Separar residuos en casa           — 50 pts  — Vence: 20/04\n" +
                        "2. [Agua]      Registrar consumo de agua semanal  — 40 pts  — Vence: 20/04\n" +
                        "3. [Energía]   Apagar dispositivos en stand-by    — 30 pts  — Vence: 20/04\n" +
                        "4. [Insumos]   Usar bolsa reutilizable             — 25 pts  — Vence: 20/04",
                "Tareas Disponibles", JOptionPane.INFORMATION_MESSAGE);
    }

    private void subirEvidencia() {
        String[] tareas = {
                "Separar residuos en casa",
                "Registrar consumo de agua semanal",
                "Apagar dispositivos en stand-by",
                "Usar bolsa reutilizable"
        };
        JOptionPane.showOptionDialog(null,
                "Seleccione la tarea que completó:",
                "Subir Evidencia",
                0, JOptionPane.QUESTION_MESSAGE, null,
                tareas, tareas[0]);

        String[] tipoArchivo = { "Foto (JPG/PNG)", "Documento (PDF)" };
        JOptionPane.showOptionDialog(null,
                "Tipo de archivo a adjuntar:",
                "Subir Evidencia",
                0, JOptionPane.QUESTION_MESSAGE, null,
                tipoArchivo, tipoArchivo[0]);

        JOptionPane.showInputDialog(null, "Ruta o nombre del archivo:", "Adjuntar Archivo", JOptionPane.PLAIN_MESSAGE);

        JOptionPane.showMessageDialog(null,
                "✔ Evidencia enviada correctamente.\nEstado: Pendiente de revisión.",
                "Subir Evidencia", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verProgreso() {
        JOptionPane.showMessageDialog(null,
                "Mi Progreso\n\n" +
                        "Puntos acumulados : 150\n" +
                        "Nivel actual      : Compromiso\n\n" +
                        "Progreso al siguiente nivel:\n" +
                        "150 / 300 pts  →  Bronce\n\n" +
                        "Tareas aprobadas  : 4\n" +
                        "Tareas pendientes : 2\n" +
                        "Eventos asistidos : 1",
                "Mi Progreso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verEventos() {
        JOptionPane.showMessageDialog(null,
                "Eventos Próximos\n\n" +
                        "1. Jornada de reciclaje\n" +
                        "   Fecha: 20/04/2026  |  Plaza Belgrano\n" +
                        "   Cupo disponible: 18 / 30\n\n" +
                        "2. Taller de compostaje\n" +
                        "   Fecha: 05/05/2026  |  Online (Zoom)\n" +
                        "   Cupo disponible: 87 / 100",
                "Eventos", JOptionPane.INFORMATION_MESSAGE);

        String[] acciones = { "Inscribirme", "Volver" };
        int seleccion = JOptionPane.showOptionDialog(null,
                "¿Desea inscribirse a algún evento?",
                "Eventos",
                0, JOptionPane.QUESTION_MESSAGE, null,
                acciones, acciones[0]);

        if (seleccion == 0) {
            inscribirseEvento();
        }
    }

    private void inscribirseEvento() {
        String[] eventos = {
                "Jornada de reciclaje — 20/04/2026",
                "Taller de compostaje — 05/05/2026"
        };
        JOptionPane.showOptionDialog(null,
                "Seleccione el evento:",
                "Inscripción",
                0, JOptionPane.QUESTION_MESSAGE, null,
                eventos, eventos[0]);

        JOptionPane.showMessageDialog(null,
                "✔ Inscripción confirmada.\nRecibirá un recordatorio por e-mail.",
                "Inscripción", JOptionPane.INFORMATION_MESSAGE);
    }

    private void canjearPuntos() {
        JOptionPane.showMessageDialog(null,
                "── Premios Disponibles ──\n\n" +
                        "Sus puntos actuales: 150 pts\n\n" +
                        "1. Descuento 10% en tienda EcoShop        —  100 pts\n" +
                        "2. Bolsa reutilizable EcoTrack             —  200 pts\n" +
                        "3. Sesión de asesoría ambiental            —  350 pts\n" +
                        "4. Kit de compostaje doméstico             —  500 pts",
                "Canjear Puntos", JOptionPane.INFORMATION_MESSAGE);

        String[] premios = {
                "Descuento 10% EcoShop (100 pts)",
                "Bolsa reutilizable (200 pts)",
                "Asesoría ambiental (350 pts)",
                "Kit de compostaje (500 pts)",
                "Volver"
        };
        int seleccion = JOptionPane.showOptionDialog(null,
                "Seleccione el premio a canjear:",
                "Canjear Puntos",
                0, JOptionPane.QUESTION_MESSAGE, null,
                premios, premios[0]);

        if (seleccion == 0) {
            JOptionPane.showMessageDialog(null,
                    "¡Premio canjeado!\nSe descontaron 100 pts. Puntos restantes: 50 pts.",
                    "Canjear Puntos", JOptionPane.INFORMATION_MESSAGE);
        } else if (seleccion >= 1 && seleccion <= 3) {
            JOptionPane.showMessageDialog(null,
                    "Puntos insuficientes.",
                    "Canjear Puntos", JOptionPane.WARNING_MESSAGE);
        }
    }
}
