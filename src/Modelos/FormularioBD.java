package Modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioBD {
    private Connection conexion;

    public FormularioBD(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean insertarFormularioYPreguntas(Formulario formulario, List<PreguntaFormulario> preguntas) {
        try {
            String sqlFormulario = "INSERT INTO Formulario (nombreFor, tema, video_url, idCurso) OUTPUT INSERTED.idFor VALUES (?, ?, ?, ?)";
            PreparedStatement psFormulario = conexion.prepareStatement(sqlFormulario);
            psFormulario.setString(1, formulario.getNombreFor());
            psFormulario.setString(2, formulario.getTema());
            psFormulario.setString(3, formulario.getVideoUrl());
            psFormulario.setInt(4, formulario.getIdCurso());

            ResultSet rs = psFormulario.executeQuery();
            int idFormulario = -1;

            if (rs.next()) {
                idFormulario = rs.getInt("idFor");
            } else {
                return false;
            }

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

    public Formulario obtenerFormularioPorId(int idFormulario) throws SQLException {
        String sql = "SELECT * FROM Formulario WHERE idFor = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idFormulario);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            return f;
        }
        return null;
    }

    public List<Formulario> obtenerTodosFormularios() throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Formulario";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            lista.add(f);
        }
        return lista;
    }

    public List<Formulario> obtenerFormulariosPorCurso(int idCurso) throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Formulario WHERE idCurso = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idCurso);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            lista.add(f);
        }
        return lista;
    }

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

    public boolean eliminarFormularioYPreguntas(int idFormulario) {
        try {
            String sqlResultados = "DELETE FROM ResultadoPractica WHERE idFormulario = ?";
            PreparedStatement psResultados = conexion.prepareStatement(sqlResultados);
            psResultados.setInt(1, idFormulario);
            int res = psResultados.executeUpdate();
            System.out.println("Resultados eliminados: " + res);

            String sqlPreguntas = "DELETE FROM PreguntaFormulario WHERE idFormulario = ?";
            PreparedStatement psPreguntas = conexion.prepareStatement(sqlPreguntas);
            psPreguntas.setInt(1, idFormulario);
            int filasPreguntas = psPreguntas.executeUpdate();
            System.out.println("Preguntas eliminadas: " + filasPreguntas);


            String sqlFormulario = "DELETE FROM Formulario WHERE idFor = ?";
            PreparedStatement psFormulario = conexion.prepareStatement(sqlFormulario);
            psFormulario.setInt(1, idFormulario);
            int filasFormulario = psFormulario.executeUpdate();
            System.out.println("Formulario eliminado: " + filasFormulario);

            return filasFormulario > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        return false;
        }
    }

    public boolean actualizarFormularioYPreguntas(Formulario formulario, List<PreguntaFormulario> preguntas) {
        try {
            String updateFormulario = "UPDATE Formulario SET nombreFor = ?, tema = ?, video_url = ?, idCurso = ? WHERE idFor = ?";
            PreparedStatement stmtFormulario = conexion.prepareStatement(updateFormulario);
            stmtFormulario.setString(1, formulario.getNombreFor());
            stmtFormulario.setString(2, formulario.getTema());
            stmtFormulario.setString(3, formulario.getVideoUrl());
            stmtFormulario.setInt(4, formulario.getIdCurso());
            stmtFormulario.setInt(5, formulario.getIdFor());
            stmtFormulario.executeUpdate();

            String deletePreguntas = "DELETE FROM PreguntaFormulario WHERE idFormulario = ?";
            PreparedStatement stmtDelete = conexion.prepareStatement(deletePreguntas);
            stmtDelete.setInt(1, formulario.getIdFor());
            stmtDelete.executeUpdate();

            String insertPregunta = "INSERT INTO PreguntaFormulario (idFormulario, nroPregunta, pregunta, opcion1, opcion2, opcion3, opcion4, respuestaCorrecta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtInsert = conexion.prepareStatement(insertPregunta);
            for (PreguntaFormulario p : preguntas) {
                stmtInsert.setInt(1, formulario.getIdFor());
                stmtInsert.setInt(2, p.getNroPregunta());
                stmtInsert.setString(3, p.getPregunta());
                stmtInsert.setString(4, p.getOpcion1());
                stmtInsert.setString(5, p.getOpcion2());
                stmtInsert.setString(6, p.getOpcion3());
                stmtInsert.setString(7, p.getOpcion4());
                stmtInsert.setString(8, p.getRespuestaCorrecta());
                stmtInsert.addBatch();
            }
            stmtInsert.executeBatch();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Formulario> obtenerFormulariosPorDocente(int idDocente) throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON f.idCurso = c.idCurso
            WHERE c.idDocente = ?
            ORDER BY f.idFor;
        """;

        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idDocente);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            lista.add(f);
        }

        return lista;
    }
    public List<Formulario> obtenerFormulariosPorEstudiante(int idEstudiante) throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON c.idCurso = f.idCurso
            JOIN AulaUsuario au ON au.idAula = c.idAula
            WHERE au.idUsuario = ?
        """;

        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idEstudiante);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            lista.add(f);
        }

        return lista;
    }
    
    public List<Formulario> obtenerFormulariosPorUsuarioYCurso(int idUsuario, int idCurso) throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON f.idCurso = c.idCurso
            JOIN AulaUsuario au ON au.idAula = c.idAula
            WHERE au.idUsuario = ? AND f.idCurso = ?
        """;

        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idUsuario);
        ps.setInt(2, idCurso);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Formulario f = new Formulario();
            f.setIdFor(rs.getInt("idFor"));
            f.setNombreFor(rs.getString("nombreFor"));
            f.setTema(rs.getString("tema"));
            f.setVideoUrl(rs.getString("video_url"));
            f.setIdCurso(rs.getInt("idCurso"));
            lista.add(f);
        }

        return lista;
    }
    
}
