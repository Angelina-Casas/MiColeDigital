package Modelos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioBD {
    private final Connection conexion;

    public FormularioBD(Connection conexion) {
        this.conexion = conexion;
    }

    public boolean insertarFormularioYPreguntas(Formulario formulario, List<PreguntaFormulario> preguntas) {
        try {
            String sqlFormulario = """
                INSERT INTO Formulario (nombreFor, tema, video_url, idCurso)
                OUTPUT INSERTED.idFor
                VALUES (?, ?, ?, ?)
            """;
            PreparedStatement psFormulario = conexion.prepareStatement(sqlFormulario);
            psFormulario.setString(1, formulario.getNombreFor());
            psFormulario.setString(2, formulario.getTema());
            psFormulario.setString(3, formulario.getVideoUrl());
            psFormulario.setInt(4, formulario.getIdCurso());

            ResultSet rs = psFormulario.executeQuery();
            if (!rs.next()) return false;

            int idFormulario = rs.getInt("idFor");

            String sqlPregunta = """
                INSERT INTO PreguntaFormulario
                (idFormulario, nroPregunta, pregunta, opcion1, opcion2, opcion3, opcion4, respuestaCorrecta)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Formulario obtenerFormularioPorId(int idFormulario) throws SQLException {
        String sql = "SELECT * FROM Formulario WHERE idFor = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idFormulario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return cargarFormulario(rs);
            }
        }
        return null;
    }

    public List<PreguntaFormulario> obtenerPreguntas(int idFormulario) throws SQLException {
        List<PreguntaFormulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM PreguntaFormulario WHERE idFormulario = ? ORDER BY nroPregunta";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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
        }
        return lista;
    }

    public List<Formulario> obtenerTodosFormularios() throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Formulario";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(cargarFormulario(rs));
            }
        }
        return lista;
    }

    public List<Formulario> obtenerFormulariosPorCurso(int idCurso) throws SQLException {
        return obtenerFormulariosPorCondicion("WHERE idCurso = ?", ps -> ps.setInt(1, idCurso));
    }

    public List<Formulario> obtenerFormulariosPorDocente(int idDocente) throws SQLException {
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON f.idCurso = c.idCurso
            WHERE c.idDocente = ?
            ORDER BY f.idFor
        """;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            ResultSet rs = ps.executeQuery();
            List<Formulario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(cargarFormulario(rs));
            }
            return lista;
        }
    }

    public List<Formulario> obtenerFormulariosPorEstudiante(int idEstudiante) throws SQLException {
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON c.idCurso = f.idCurso
            JOIN AulaUsuario au ON au.idAula = c.idAula
            WHERE au.idUsuario = ?
        """;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            List<Formulario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(cargarFormulario(rs));
            }
            return lista;
        }
    }

    public List<Formulario> obtenerFormulariosPorUsuarioYCurso(int idUsuario, int idCurso) throws SQLException {
        String sql = """
            SELECT f.*
            FROM Formulario f
            JOIN Curso c ON f.idCurso = c.idCurso
            JOIN AulaUsuario au ON au.idAula = c.idAula
            WHERE au.idUsuario = ? AND f.idCurso = ?
        """;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idCurso);
            ResultSet rs = ps.executeQuery();
            List<Formulario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(cargarFormulario(rs));
            }
            return lista;
        }
    }

    // ---------- ACTUALIZAR Y ELIMINAR ----------
    public boolean actualizarFormularioYPreguntas(Formulario formulario, List<PreguntaFormulario> preguntas) {
        try {
            String sqlUpdate = "UPDATE Formulario SET nombreFor = ?, tema = ?, video_url = ?, idCurso = ? WHERE idFor = ?";
            PreparedStatement ps = conexion.prepareStatement(sqlUpdate);
            ps.setString(1, formulario.getNombreFor());
            ps.setString(2, formulario.getTema());
            ps.setString(3, formulario.getVideoUrl());
            ps.setInt(4, formulario.getIdCurso());
            ps.setInt(5, formulario.getIdFor());
            ps.executeUpdate();

            // Borrar preguntas anteriores
            String sqlDelete = "DELETE FROM PreguntaFormulario WHERE idFormulario = ?";
            try (PreparedStatement psDel = conexion.prepareStatement(sqlDelete)) {
                psDel.setInt(1, formulario.getIdFor());
                psDel.executeUpdate();
            }

            // Insertar nuevas preguntas
            String sqlInsert = """
                INSERT INTO PreguntaFormulario (idFormulario, nroPregunta, pregunta, opcion1, opcion2, opcion3, opcion4, respuestaCorrecta)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
            PreparedStatement psInsert = conexion.prepareStatement(sqlInsert);
            for (PreguntaFormulario pf : preguntas) {
                psInsert.setInt(1, formulario.getIdFor());
                psInsert.setInt(2, pf.getNroPregunta());
                psInsert.setString(3, pf.getPregunta());
                psInsert.setString(4, pf.getOpcion1());
                psInsert.setString(5, pf.getOpcion2());
                psInsert.setString(6, pf.getOpcion3());
                psInsert.setString(7, pf.getOpcion4());
                psInsert.setString(8, pf.getRespuestaCorrecta());
                psInsert.addBatch();
            }
            psInsert.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarFormularioYPreguntas(int idFormulario) {
        try {
            ejecutarDelete("DELETE FROM ResultadoPractica WHERE idFormulario = ?", idFormulario);
            ejecutarDelete("DELETE FROM PreguntaFormulario WHERE idFormulario = ?", idFormulario);
            return ejecutarDelete("DELETE FROM Formulario WHERE idFor = ?", idFormulario) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Formulario cargarFormulario(ResultSet rs) throws SQLException {
        Formulario f = new Formulario();
        f.setIdFor(rs.getInt("idFor"));
        f.setNombreFor(rs.getString("nombreFor"));
        f.setTema(rs.getString("tema"));
        f.setVideoUrl(rs.getString("video_url"));
        f.setIdCurso(rs.getInt("idCurso"));
        return f;
    }

    private List<Formulario> obtenerFormulariosPorCondicion(String condicion, ParamSetter setter) throws SQLException {
        List<Formulario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Formulario " + condicion;
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            setter.set(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(cargarFormulario(rs));
            }
        }
        return lista;
    }

    private int ejecutarDelete(String sql, int id) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    @FunctionalInterface
    private interface ParamSetter {
        void set(PreparedStatement ps) throws SQLException;
    }
}
