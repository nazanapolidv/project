package DAO;
import BLL.Premio;
import java.util.List;

public interface PremioDAO {

    boolean guardarPremio(Premio premio);

    List<Premio> listarPremios();
}
