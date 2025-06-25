package Modelos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FormularioBD {
    private Connection conexion;

    public FormularioBD(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean insertarFormulario(Formulario formulario) {
        String sql = "INSERT INTO Formulario (nombreFor, tema, video_url, "
                + "nroPregunta, pregunta, " + "opcion1, opcion2, opcion3, "
                + "opcion4, respuestaCorrecta) " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, formulario.getNombreFor());
            stmt.setString(2, formulario.getTema());
            stmt.setString(3, formulario.getVideoUrl());
            stmt.setInt(4, formulario.getNroPregunta());
            stmt.setString(5, formulario.getPregunta());
            stmt.setString(6, formulario.getOpcion1());
            stmt.setString(7, formulario.getOpcion2());
            stmt.setString(8, formulario.getOpcion3());
            stmt.setString(9, formulario.getOpcion4());
            stmt.setString(10, formulario.getRespuestaCorrecta());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al insertar formulario: " + e.getMessage());
            return false;
        }
    }
}
