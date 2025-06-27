package Modelos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioBD {
    private Connection conexion;

    public FormularioBD(Connection conexion) {
        this.conexion = conexion;
    }

    // Inserta un formulario y sus preguntas asociadas
    public boolean insertarFormularioYPreguntas(Formulario formulario, List<PreguntaFormulario> preguntas) {
        try {
            // Insertar formulario y obtener el id generado
            String sqlFormulario = "INSERT INTO Formulario (nombreFor, tema, video_url) OUTPUT INSERTED.idFor VALUES (?, ?, ?)";
            PreparedStatement psFormulario = conexion.prepareStatement(sqlFormulario);
            psFormulario.setString(1, formulario.getNombreFor());
            psFormulario.setString(2, formulario.getTema());
            psFormulario.setString(3, formulario.getVideoUrl());
            ResultSet rs = psFormulario.executeQuery();

            int idFormulario = -1;
            if (rs.next()) {
                idFormulario = rs.getInt("idFor");
            } else {
                return false;
            }

            // Insertar preguntas asociadas
            String sqlPregunta = "INSERT INTO PreguntaFormulario (idFormulario, nroPregunta, pregunta, opcion1, opcion2, opcion3, opcion4, respuestaCorrecta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPregunta = conexion.prepareStatement(sqlPregunta);

            for (PreguntaFormulario pf : preguntas) {
                psPregunta.setInt(1, idFormulario);
                psPregunta.setInt(2, pf.getNroPregunta());
                psPregunta.setString(3, pf.getPregunta());
                psPregunta.setString(4, pf.getOpcion1());
                psPregunta.setString(5, pf.getOpcion2());
                psPregunta.setString(6, pf.getOpcion3());
                psPregunta.setString(7, pf.getOpcion4());
                psPregunta.setString(8, pf.getRespuestaCorrecta());
                psPregunta.addBatch();
            }

            psPregunta.executeBatch();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener un formulario por ID
    public Formulario obtenerFormulario(int idFormulario) throws SQLException {
        String sql = "SELECT * FROM Formulario WHERE idFor = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idFormulario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Formulario(
                rs.getString("nombreFor"),
                rs.getString("tema"),
                rs.getString("video_url")
            );
        }
        return null;
    }

    // Obtener todos los formularios
    public List<Formulario> obtenerTodosFormularios() {
        List<Formulario> formularios = new ArrayList<>();
        String sql = "SELECT idFor, nombreFor FROM Formulario";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Formulario f = new Formulario();
                f.setIdFor(rs.getInt("idFor"));
                f.setNombreFor(rs.getString("nombreFor"));
                formularios.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formularios;
    }

    // Obtener preguntas por formulario
    public List<PreguntaFormulario> obtenerPreguntas(int idFormulario) throws SQLException {
        List<PreguntaFormulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM PreguntaFormulario WHERE idFormulario = ? ORDER BY nroPregunta";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idFormulario);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new PreguntaFormulario(
                rs.getInt("nroPregunta"),
                rs.getString("pregunta"),
                rs.getString("opcion1"),
                rs.getString("opcion2"),
                rs.getString("opcion3"),
                rs.getString("opcion4"),
                rs.getString("respuestaCorrecta")
            ));
        }
        return lista;
    }
}