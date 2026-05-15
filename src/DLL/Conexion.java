package DLL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/ecotrack";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private Connection conexion;
    private static Conexion instance;

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error fatal al conectar a la base de datos: " + e.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public static Conexion getInstance() {
        try {
            if (instance == null || instance.getConexion() == null || instance.getConexion().isClosed()) {
                instance = new Conexion();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar el estado de la conexión: " + e.getMessage());
        }
        return instance;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}