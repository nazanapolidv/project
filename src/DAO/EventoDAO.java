package DAO;
import BLL.Evento;
import java.util.List;


public interface EventoDAO {

    boolean crearEvento(Evento evento);

    List<Evento> listarEventos();
}
