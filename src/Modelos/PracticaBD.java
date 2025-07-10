package Modelos;

import Conexion.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PracticaBD {

    public boolean practicaYaEnviada(int idUsuario, int idFormulario) {
        String sql = "SELECT 1 FROM ResultadoPractica WHERE idUsuario = ? AND idFormulario = ?";
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idFormulario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerNotaAnterior(int idUsuario, int idFormulario) {
        String sql = "SELECT nota FROM ResultadoPractica WHERE idUsuario = ? AND idFormulario = ?";
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idFormulario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nota");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    public List<PreguntaFormulario> obtenerPreguntasFormulario(int idFormulario) {
        List<PreguntaFormulario> preguntas = new ArrayList<>();
        String sql = "SELECT * FROM PreguntaFormulario WHERE idFormulario = ? ORDER BY nroPregunta";
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFormulario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PreguntaFormulario pregunta = new PreguntaFormulario(
                        rs.getInt("nroPregunta"),
                        rs.getString("pregunta"),
                        rs.getString("opcion1"),
                        rs.getString("opcion2"),
                        rs.getString("opcion3"),
                        rs.getString("opcion4"),
                        rs.getString("respuestaCorrecta")
                );
                preguntas.add(pregunta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preguntas;
    }

    public boolean insertarResultadoPractica(int idUsuario, int idFormulario, int nota) {
        String sql = "INSERT INTO ResultadoPractica (idUsuario, idFormulario, nota) VALUES (?, ?, ?)";
        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idFormulario);
            ps.setInt(3, nota);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}