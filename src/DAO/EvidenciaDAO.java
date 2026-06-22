package DAO;

import BLL.Evidencia;
import java.util.List;

public interface EvidenciaDAO {
    
    boolean agregarEvidencia(Evidencia evidencia);
    
    
    List<Evidencia> obtenerEvidenciasPendientes();
    
    
    boolean procesarEvidencia(int idEvidencia, int idCliente, int puntos, boolean aprobado);
}