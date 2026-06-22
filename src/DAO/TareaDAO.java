package DAO;
import BLL.Tarea;
import java.util.List;

public interface TareaDAO {

    boolean crearTarea(Tarea tarea);

    List<Tarea> listarTareas();
}
