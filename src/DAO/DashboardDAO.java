package DAO;

public interface DashboardDAO {

    int getCantidadUsuarios();

    int getTareasActivas();

    int getEvidenciasPendientes();

    int getEventosProximos();

    String getEvidenciasPorEje();
}
