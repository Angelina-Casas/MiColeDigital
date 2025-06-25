package Modelos;

import Conexion.ConexionBD;
import java.sql.*;
import java.util.*;

public class UsuarioBD {
    
    RolBD rolrs = new RolBD();
    public UsuarioBD(){}
    
    public List<Usuario> listarUsuario() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.password, " + 
                    "r.idRol, r.nombreRol " +
                    "FROM Usuario u " +
                    "INNER JOIN Rol r ON u.idRol = r.idRol ORDER BY idUsuario ";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("password"),
                    rolrs.obtenerRol(rs.getInt("idRol"))
                );
                lista.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Usuario obtenerUsuario(int idUsuario) {
        Usuario user = null;
        String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.password, " + 
                     "r.idRol, r.nombreRol " +
                     "FROM Usuario u " +
                     "INNER JOIN Rol r ON u.idRol = r.idRol " +
                     "WHERE u.idUsuario = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) 
                {
                    user = new Usuario(
                        rs.getInt("idUsuario"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rolrs.obtenerRol(rs.getInt("idRol"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
 
    public Usuario validarUsuario(String correo, String password)
    {
        Usuario usuario = null;
        
        String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.password, "
                + "r.idRol, r.nombreRol " +
                 "FROM Usuario u INNER JOIN Rol r ON u.idRol = r.idRol " +
                 "WHERE u.correo = ? AND u.password = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery())
            {   
                if (rs.next()) {
                    usuario = new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("password"),
                    rolrs.obtenerRol(rs.getInt("idRol"))
                );
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }
        
    
    
    
    public boolean insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nombre, correo, password, idRol) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getRol().getIdRol());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET nombre = ?, correo = ?, password = ?, "
                + "idRol = ? WHERE idUsuario = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setInt(4, usuario.getRol().getIdRol());
            ps.setInt(5, usuario.getIdUsuario());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerIdPorCorreo(String correo) {
        int idUsuario = -1;
        String sql = "SELECT idUsuario FROM Usuario WHERE correo = ?";

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getInt("idUsuario");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUsuario;
    }
    
    
    
    public List<Usuario> listarDocentes() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.password, " + 
                 "r.idRol, r.nombreRol " +
                 "FROM Usuario u " +
                 "INNER JOIN Rol r ON u.idRol = r.idRol " +
                 "WHERE r.idRol = 2 " + // Filtra solo los docentes
                 "ORDER BY idUsuario";

    try (Connection con = new ConexionBD().obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario(
                rs.getInt("idUsuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("password"),
                rolrs.obtenerRol(rs.getInt("idRol"))
            );
            lista.add(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

    
    public List<Usuario> listarEstudiantes() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT u.idUsuario, u.nombre, u.correo, u.password, " + 
                 "r.idRol, r.nombreRol " +
                 "FROM Usuario u " +
                 "INNER JOIN Rol r ON u.idRol = r.idRol " +
                 "WHERE r.idRol = 1 " + 
                 "ORDER BY idUsuario";

    try (Connection con = new ConexionBD().obtenerConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario(
                rs.getInt("idUsuario"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getString("password"),
                rolrs.obtenerRol(rs.getInt("idRol"))
            );
            lista.add(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
