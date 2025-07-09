package Conexion;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        Connection con = conexionBD.obtenerConexion();

        if (con != null) {
            System.out.println("Conexion exitosa a la base de datos. :)");
        } else {
            System.out.println("Error al conectar con la base de datos. :(");
        }
    }
}