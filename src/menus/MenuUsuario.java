package menus;

import javax.swing.JOptionPane;
import DLL.UsuarioController;

public class MenuUsuario implements Menu {

    private final int idUsuarioLogueado = 1; 

    @Override
    public void mostrar() {
        UsuarioController usuarioController = new UsuarioController();

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
            int puntosReales = usuarioController.obtenerPuntosUsuario(idUsuarioLogueado);

            seleccion = JOptionPane.showOptionDialog(
                    null,
                    "Bienvenido a EcoTrack  |  Puntos acumulados: " + puntosReales + " pts",
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
                "Separar residuos en origen (Residuos)",
                "Registrar consumo de agua semanal (Agua)",
                "Apagar dispositivos en stand-by (Energía)",
                "Evitar plásticos de un solo uso (Insumos)"
        };
        
        int tareaSeleccionada = JOptionPane.showOptionDialog(null,
                "Seleccione la tarea ambiental que completó:",
                "Subir Evidencia",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                tareas, tareas[0]);

        if (tareaSeleccionada == -1) return;

        int idTareaBD = tareaSeleccionada + 1;

        String[] tipoArchivo = { "Foto (JPG/PNG)", "Documento (PDF)" };
        int seleccionTipo = JOptionPane.showOptionDialog(null,
                "Seleccione el tipo de comprobante a adjuntar:",
                "Subir Evidencia",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                tipoArchivo, tipoArchivo[0]);
                
        if (seleccionTipo == -1) return;

        String rutaArchivo = JOptionPane.showInputDialog(null, 
                "Ingrese el nombre o la ruta de su archivo de comprobante (Ej: panel_solar.png):", 
                "Adjuntar Archivo", 
                JOptionPane.PLAIN_MESSAGE);

        if (rutaArchivo == null || rutaArchivo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se adjuntó ningún archivo. Operación cancelada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BLL.Evidencia nuevaEvidencia = new BLL.Evidencia();
        nuevaEvidencia.setIdCliente(idUsuarioLogueado);
        nuevaEvidencia.setIdTarea(idTareaBD);
        nuevaEvidencia.setArchivoUrl(rutaArchivo);

        DLL.EvidenciaDLL evDLL = new DLL.EvidenciaDLL();
        boolean guardadoExitoso = evDLL.agregarEvidencia(nuevaEvidencia);

        if (guardadoExitoso) {
            JOptionPane.showMessageDialog(null,
                    "✔ ¡Evidencia registrada con éxito en la Base de Datos!\nEstado actual: Pendiente de revisión.",
                    "Envío Exitoso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "❌ Error crítico: No se pudo conectar con la base de datos para salvar el registro.",
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verProgreso() {
        UsuarioController usuarioController = new UsuarioController();
        int puntosReales = usuarioController.obtenerPuntosUsuario(idUsuarioLogueado);
        
        String nivelActual = "Compromiso";
        int puntosSiguienteNivel = 300;
        String siguienteNivel = "Bronce";

        if (puntosReales >= 300 && puntosReales < 600) {
            nivelActual = "Bronce";
            puntosSiguienteNivel = 600;
            siguienteNivel = "Plata";
        } else if (puntosReales >= 600) {
            nivelActual = "Plata";
            puntosSiguienteNivel = 1000;
            siguienteNivel = "Oro";
        }

        JOptionPane.showMessageDialog(null,
                "Mi Progreso Real 🌱\n\n" +
                        "Puntos acumulados : " + puntosReales + " pts\n" +
                        "Nivel actual      : " + nivelActual + "\n\n" +
                        "Progreso al siguiente hito:\n" +
                        puntosReales + " / " + puntosSiguienteNivel + " pts  →  " + siguienteNivel,
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
        UsuarioController usuarioController = new UsuarioController();
        int puntosReales = usuarioController.obtenerPuntosUsuario(idUsuarioLogueado);

        JOptionPane.showMessageDialog(null,
                "── Premios Disponibles ──\n\n" +
                        "Sus puntos actuales: " + puntosReales + " pts\n\n" +
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

        
        int[] costos = { 100, 200, 350, 500 };

        
        if (seleccion >= 0 && seleccion <= 3) {
            int costoPremio = costos[seleccion];
            String nombrePremio = premios[seleccion];

            
            if (puntosReales >= costoPremio) {
                
                
                boolean canjeExitoso = usuarioController.reducirPuntosUsuario(idUsuarioLogueado, costoPremio);

                if (canjeExitoso) {
                    int puntosRestantes = puntosReales - costoPremio;
                    JOptionPane.showMessageDialog(null,
                            "🎉 ¡Canje exitoso!\nHas adquirido: " + nombrePremio + 
                            "\nSe descontaron " + costoPremio + " pts.\nSaldo restante: " + puntosRestantes + " pts.",
                            "Canjear Puntos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "❌ Error al procesar el canje en la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "❌ Puntos insuficientes. Te faltan " + (costoPremio - puntosReales) + " pts para este premio.",
                        "Canjear Puntos", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}