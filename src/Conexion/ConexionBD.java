package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de gestionar la conexión a la base de datos SQL Server.
 */
public class ConexionBD {

    private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String URL = "jdbc:sqlserver://DBMiColeDigital.mssql.somee.com:1433;"
                                    + "databaseName=DBMiColeDigital;"
                                    + "encrypt=true;"
                                    + "trustServerCertificate=true;";
    private static final String USUARIO = "angiecasas_SQLLogin_1";
    private static final String CONTRASENA = "65w2espf7m"; // ⚠ Recomendación: usar variable de entorno o archivo de configuración externo.

    private Connection conexion;

    /**
     * Establece y retorna una nueva conexión con la base de datos.
     */
    private Connection conectar() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retorna una conexión activa a la base de datos.
     * Si no existe o está cerrada, intenta crear una nueva.
     */
    public Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = conectar();
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar la conexión: " + e.getMessage());
            conexion = conectar();
        }
        return conexion;
    }
}
