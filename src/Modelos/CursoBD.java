package Modelos;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoBD {
    private AulaBD aulaBD = new AulaBD();
    private UsuarioBD usuarioBD = new UsuarioBD();

    public CursoBD() {}

    // Listar todos los cursos con su aula y docente completo
    public List<Curso> listarCurso() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT idCurso, nombre, idDocente, idAula FROM Curso";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                    int idCurso = rs.getInt("idCurso");
                    String nombreCurso = rs.getString("nombre");

                    // Corrección: usa el nombre correcto de las columnas
                    Usuario docente = usuarioBD.obtenerUsuario(rs.getInt("idDocente"));
                    Aula aula = aulaBD.obtenerAula(rs.getInt("idAula"));

                    Curso curso = new Curso(idCurso, nombreCurso, docente, aula);
                lista.add(curso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener un curso por su ID
    public Curso obtenerCurso(int idCurso) {
        Curso curso = null;
        String sql = "SELECT idCurso, nombre, idDocente, idAula FROM Curso WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCurso);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) 
                {
                    String nombreCurso = rs.getString("nombre");

                    Usuario docente = usuarioBD.obtenerUsuario(rs.getInt("idDocente"));
                    Aula aula = aulaBD.obtenerAula(rs.getInt("idAula"));

                    curso = new Curso(idCurso, nombreCurso, docente, aula);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return curso;
    }

    // Agregar un nuevo curso
    public boolean agregarCurso(Curso curso) {
        String sql = "INSERT INTO Curso (nombre, idDocente, idAula) VALUES (?, ?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getDocente().getIdUsuario());
            ps.setInt(3, curso.getAula().getIdAula());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar un curso existente
    public boolean actualizarCurso(Curso curso) {
        String sql = "UPDATE Curso SET nombre = ?, idDocente = ?, idAula = ? WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setInt(2, curso.getDocente().getIdUsuario());
            ps.setInt(3, curso.getAula().getIdAula());
            ps.setInt(4, curso.getIdCurso());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un curso
    public boolean eliminarCurso(int idCurso) {
        String sql = "DELETE FROM Curso WHERE idCurso = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCurso);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
