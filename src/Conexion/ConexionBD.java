package Conexion;
import java.sql.*;
import java.sql.DriverManager;

public class ConexionBD {
    Connection con;

    public Connection conectar() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String url = "jdbc:sqlserver://DBMiColeDigital.mssql.somee.com:1433;"
                      + "databaseName=DBMiColeDigital;"
                      + "user=angiecasas_SQLLogin_1;"
                      + "password=65w2espf7m;"
                      + "encrypt=true;"
                      + "trustServerCertificate=true;";

                       con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con;
    }
        public Connection obtenerConexion() {
        if (con == null) {
            return conectar();
        }
        return con;
    }
}