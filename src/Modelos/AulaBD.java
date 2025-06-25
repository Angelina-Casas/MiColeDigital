package Modelos;
import Conexion.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaBD {
    
    public AulaBD(){}
    public List<Aula> listarAulas() {
        List<Aula> lista = new ArrayList<>();
        String sql = "SELECT idAula, grado, seccion, promocion FROM Aula";
        
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aula aula = new Aula(
                    rs.getInt("idAula"),
                    rs.getInt("grado"),
                    rs.getString("seccion"),
                    rs.getDate("promocion").toLocalDate()
                );
                lista.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Aula obtenerAula(int idAula) {
        Aula aula = null;
        String sql = "SELECT idAula, grado, seccion, promocion FROM Aula WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aula = new Aula(
                        rs.getInt("idAula"),
                        rs.getInt("grado"),
                        rs.getString("seccion"),
                        rs.getDate("promocion").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aula;
    }

    public boolean insertarAula(Aula aula) {
        String sql = "INSERT INTO Aula (grado, seccion, promocion) VALUES (?, ?, ?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aula.getGrado());
            ps.setString(2, aula.getSeccion());
            ps.setDate(3, Date.valueOf(aula.getPromocion()));

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarAula(Aula aula) {
        String sql = "UPDATE Aula SET grado = ?, seccion = ?, promocion = ? WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, aula.getGrado());
            ps.setString(2, aula.getSeccion());
            ps.setDate(3, Date.valueOf(aula.getPromocion()));
            ps.setInt(4, aula.getIdAula());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarAula(int idAula) {
        String sql = "DELETE FROM Aula WHERE idAula = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAula);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}