package Docente;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Usuario;
import Modelos.Curso;

public class CursoDocente extends ComplementosFrameDocente {
    private JPanel contenedorCursos;

    public CursoDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        inicializarContenedorCursos();
        cargarCursosDesdeBD();

        panelDerecho.add(contenedorCursos);
        setVisible(true);
    }

    private void inicializarContenedorCursos() {
        contenedorCursos = new JPanel();
        contenedorCursos.setLayout(new BoxLayout(contenedorCursos, BoxLayout.Y_AXIS));
        contenedorCursos.setBounds(200, 160, 600, 400);
        contenedorCursos.setBackground(Color.WHITE);
        contenedorCursos.setOpaque(false);
    }

    private void cargarCursosDesdeBD() {
        String sql = """
            SELECT c.idCurso, c.nombre AS nombreCurso, a.grado, a.seccion
            FROM Curso c
            JOIN Aula a ON c.idAula = a.idAula
            WHERE c.idDocente = ?
        """;

        try (Connection conn = new ConexionBD().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuario.getIdUsuario());
            ResultSet rs = ps.executeQuery();

            boolean tieneCursos = false;

            while (rs.next()) {
                tieneCursos = true;
                int idCurso = rs.getInt("idCurso");
                String nombreCurso = rs.getString("nombreCurso");
                int grado = rs.getInt("grado");
                String seccion = rs.getString("seccion");

                JButton btnCurso = crearBotonCurso(idCurso, nombreCurso, grado, seccion);
                contenedorCursos.add(Box.createRigidArea(new Dimension(0, 20)));
                contenedorCursos.add(btnCurso);
            }

            if (!tieneCursos) {
                JLabel lbl = new JLabel("No tienes cursos asignados.");
                lbl.setFont(new Font("SansSerif", Font.BOLD, 18));
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                contenedorCursos.add(lbl);
            }

        } catch (Exception e) {
            mostrarError("Error al cargar cursos del docente: " + e.getMessage());
        }
    }

    private JButton crearBotonCurso(int idCurso, String nombre, int grado, String seccion) {
        String texto = nombre + " - " + grado + "°" + seccion;

        JButton btn = new JButton(texto);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(400, 80));
        btn.setBackground(new Color(254, 234, 157));
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));

        btn.addActionListener(e -> {
            Curso curso = new Curso();
            curso.setIdCurso(idCurso);
            curso.setNombre(nombre);
            curso.setGrado(grado);
            curso.setSeccion(seccion);

            new ContenidoDocente(usuario, curso).setVisible(true);
            dispose();
        });

        return btn;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
