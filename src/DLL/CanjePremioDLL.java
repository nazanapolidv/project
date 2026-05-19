package DLL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CanjePremioDLL {

	public boolean ejecutarCanje(int idCliente, int idPremio, int costoPuntos) {
	    String sqlCanje = "INSERT INTO canje_premio (id_cliente, id_premio, fecha_canje) VALUES (?, ?, CURRENT_TIMESTAMP)";
	    String sqlRestarPuntos = "UPDATE cliente SET puntos_acumulados = puntos_acumulados - ? WHERE id_cliente = ?";
	    
	    Connection con = null;
	    try {
	        
	        con = Conexion.getInstance().getConexion();
	        con.setAutoCommit(false);
	        try (PreparedStatement psCanje = con.prepareStatement(sqlCanje)) {
	            psCanje.setInt(1, idCliente);
	            psCanje.setInt(2, idPremio);
	            psCanje.executeUpdate();
	        }
	        
	        try (PreparedStatement psPuntos = con.prepareStatement(sqlRestarPuntos)) {
	            psPuntos.setInt(1, costoPuntos);
	            psPuntos.setInt(2, idCliente);
	            psPuntos.executeUpdate();
	        }

	        con.commit(); 
	        return true;
	        
	    } catch (SQLException e) {
	        System.out.println("Error en Transacción de Canje: " + e.getMessage());
	        if (con != null) {
	            try { 
	                con.rollback(); 
	            } catch (SQLException ex) { 
	                ex.printStackTrace(); 
	            }
	        }
	    }
	    return false;
	} }