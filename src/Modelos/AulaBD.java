package Modelos;

import Conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Aula.
 */
public class AulaBD {

    /**
     * Devuelve la lista de todas las aulas registradas.
     */
    public List<Aula> listarAulas() {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT idAula, grado, seccion FROM Aula";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula(
                    rs.getInt("idAula"),
                    rs.getInt("grado"),
                    rs.getString("seccion")
                );
                lista.add(aula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un aula por su ID.
     */
    public Aula obtenerAula(int idAula) {
        String sql = "SELECT idAula, grado, seccion FROM Aula WHERE idAula = ?";
        Aula aula = null;

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aula = new Aula(
                        rs.getInt("idAula"),
                        rs.getInt("grado"),
                        rs.getString("seccion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aula;
    }

    /**
     * Inserta una nueva aula.
     */
    public boolean insertarAula(Aula aula) {
        String sql = "INSERT INTO Aula (grado, seccion) VALUES (?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aula.getGrado());
            ps.setString(2, aula.getSeccion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los datos de un aula existente.
     */
    public boolean actualizarAula(Aula aula) {
        String sql = "UPDATE Aula SET grado = ?, seccion = ? WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aula.getGrado());
            ps.setString(2, aula.getSeccion());
            ps.setInt(3, aula.getIdAula());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un aula por su ID.
     */
    public boolean eliminarAula(int idAula) {
        String sql = "DELETE FROM Aula WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Asocia un estudiante a un aula.
     */
    public boolean agregarEstudianteAlAula(int idUsuario, int idAula) {
        String sql = "INSERT INTO AulaUsuario (idUsuario, idAula) VALUES (?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idAula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina la asociación entre un estudiante y un aula.
     */
    public boolean eliminarEstudianteDelAula(int idUsuario, int idAula) {
        String sql = "DELETE FROM AulaUsuario WHERE idUsuario = ? AND idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idAula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
