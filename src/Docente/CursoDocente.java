package Docente;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Usuario;

public class CursoDocente extends ComplementosFrameDocente {
    private JPanel contenedorCursos;

    public CursoDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        contenedorCursos = new JPanel();
        contenedorCursos.setLayout(new BoxLayout(contenedorCursos, BoxLayout.Y_AXIS));
        contenedorCursos.setBounds(200, 160, 600, 400);
        contenedorCursos.setBackground(Color.WHITE);
        contenedorCursos.setOpaque(false);

        try {
            ConexionBD conexion = new ConexionBD();
            Connection conn = conexion.obtenerConexion();

            String sql = "SELECT c.idCurso, c.nombre AS nombreCurso, a.grado, a.seccion " +
                         "FROM Curso c " +
                         "JOIN Aula a ON c.idAula = a.idAula " +
                         "WHERE c.idDocente = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuario.getIdUsuario());

            ResultSet rs = ps.executeQuery();
            int y = 0;

            while (rs.next()) {
                int idCurso = rs.getInt("idCurso");
                String nombreCurso = rs.getString("nombreCurso");
                int grado = rs.getInt("grado");
                String seccion = rs.getString("seccion");

                String textoBoton = nombreCurso + " - " + grado + "°" + seccion;

                JButton btnCurso = new JButton(textoBoton);
                btnCurso.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnCurso.setMaximumSize(new Dimension(400, 80));
                btnCurso.setBackground(new Color(255, 220, 80));
                btnCurso.setFont(new Font("Serif", Font.PLAIN, 20));

                btnCurso.addActionListener(e -> {
                    new ContenidoDocente(usuario).setVisible(true); // Si luego deseas filtrar por curso, se puede pasar el idCurso aquí
                    dispose();
                });

                contenedorCursos.add(Box.createRigidArea(new Dimension(0, 20)));
                contenedorCursos.add(btnCurso);
                y += 100;
            }

            if (y == 0) {
                JLabel lbl = new JLabel("No tienes cursos asignados.");
                lbl.setFont(new Font("SansSerif", Font.BOLD, 18));
                lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
                contenedorCursos.add(lbl);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar cursos del docente: " + e.getMessage());
        }

        panelDerecho.add(contenedorCursos);
        setVisible(true);
    }
}