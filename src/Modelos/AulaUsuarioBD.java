package Modelos;

import Conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AulaUsuarioBD {

    public List<Integer> listarUsuariosPorAula(int idAula) {
        List<Integer> usuarios = new ArrayList<>();
        String sql = "SELECT idUsuario FROM AulaUsuario WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usuarios.add(rs.getInt("idUsuario"));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios por aula: " + e.getMessage());
        }

        return usuarios;
    }

    public boolean insertarAulaUsuario(int idUsuario, int idAula) {
        String sql = "INSERT INTO AulaUsuario (idUsuario, idAula) VALUES (?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idAula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar AulaUsuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuarioDeAula(int idUsuario, int idAula) {
        String sql = "DELETE FROM AulaUsuario WHERE idUsuario = ? AND idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idAula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario de aula: " + e.getMessage());
            return false;
        }
    }
}
