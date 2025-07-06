package Modelos;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.*;

public class RolBD {

    public RolBD() {}

    // Listar todos los roles
    public List<Rol> listarRoles() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT idRol, nombreRol FROM Rol";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("idRol"), rs.getString("nombreRol"));
                lista.add(rol);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener un rol por su ID
    public Rol obtenerRol(int idRol) {
        Rol rol = null;
        String sql = "SELECT idRol, nombreRol FROM Rol WHERE idRol = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol(rs.getInt("idRol"), rs.getString("nombreRol"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }

    // Buscar rol por nombre
    public Rol buscarRol(String nombreRol) {
        Rol rol = null;
        String sql = "SELECT idRol, nombreRol FROM Rol WHERE nombreRol = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreRol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = new Rol(rs.getInt("idRol"), rs.getString("nombreRol"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rol;
    }

    // Insertar un nuevo rol
    public boolean insertarRol(Rol rol) {
        String sql = "INSERT INTO Rol (nombreRol) VALUES (?)";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar un rol existente
    public boolean actualizarRol(Rol rol) {
        String sql = "UPDATE Rol SET nombreRol = ? WHERE idRol = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            ps.setInt(2, rol.getIdRol());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un rol por su ID
    public boolean eliminarRol(int idRol) {
        String sql = "DELETE FROM Rol WHERE idRol = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idRol);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
