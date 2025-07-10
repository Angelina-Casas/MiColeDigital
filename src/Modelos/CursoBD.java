package Modelos;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoBD {
    private final AulaBD aulaBD = new AulaBD();
    private final UsuarioBD usuarioBD = new UsuarioBD();

    public CursoBD() {}

    public List<Curso> listarCurso() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT idCurso, nombre, idDocente, idAula FROM Curso";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCurso(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Curso obtenerCurso(int idCurso) {
        Curso curso = null;
        String sql = "SELECT idCurso, nombre, idDocente, idAula FROM Curso WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCurso);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    curso = mapearCurso(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return curso;
    }

    public boolean agregarCurso(Curso curso) {
        String sql = "INSERT INTO Curso (nombre, idDocente, idAula) VALUES (?, ?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getDocente().getIdUsuario());
            ps.setInt(3, curso.getAula().getIdAula());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCurso(Curso curso) {
        String sql = "UPDATE Curso SET nombre = ?, idDocente = ?, idAula = ? WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getDocente().getIdUsuario());
            ps.setInt(3, curso.getAula().getIdAula());
            ps.setInt(4, curso.getIdCurso());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCurso(int idCurso) {
        String eliminarRelacion = "DELETE FROM CursoEstudiante WHERE idCurso = ?";
        String eliminarCurso = "DELETE FROM Curso WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion()) {
            try (PreparedStatement ps1 = con.prepareStatement(eliminarRelacion)) {
                ps1.setInt(1, idCurso);
                ps1.executeUpdate();
            }

            try (PreparedStatement ps2 = con.prepareStatement(eliminarCurso)) {
                ps2.setInt(1, idCurso);
                return ps2.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Curso> listarCursosPorEstudiante(int idUsuario) {
        List<Curso> lista = new ArrayList<>();
        String sql = """
            SELECT c.idCurso, c.nombre, c.idDocente, c.idAula
            FROM Curso c
            JOIN AulaUsuario au ON au.idAula = c.idAula
            WHERE au.idUsuario = ?""";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCurso(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private Curso mapearCurso(ResultSet rs) throws SQLException {
        int idCurso = rs.getInt("idCurso");
        String nombreCurso = rs.getString("nombre");
        Usuario docente = usuarioBD.obtenerUsuario(rs.getInt("idDocente"));
        Aula aula = aulaBD.obtenerAula(rs.getInt("idAula"));
        return new Curso(idCurso, nombreCurso, docente, aula);
    }
}
