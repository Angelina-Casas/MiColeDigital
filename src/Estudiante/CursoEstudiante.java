package Estudiante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.CursoBD;

public class CursoEstudiante extends ComplementosFrameEstudiante {
    private JPanel contenedorCursos;

    public CursoEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        contenedorCursos = new JPanel();
        contenedorCursos.setLayout(new BoxLayout(contenedorCursos, BoxLayout.Y_AXIS));
        contenedorCursos.setBackground(Color.WHITE);
        contenedorCursos.setBounds(340, 180, 400, 400);
        panelDerecho.setLayout(null);
        panelDerecho.add(contenedorCursos);

        CursoBD cursoBD = new CursoBD();
        List<Curso> cursos = cursoBD.listarCursosPorEstudiante(usuario.getIdUsuario());

        if (cursos.isEmpty()) {
            JLabel lblSinCursos = new JLabel("No tienes cursos asignados.");
            lblSinCursos.setFont(new Font("SansSerif", Font.BOLD, 16));
            lblSinCursos.setAlignmentX(Component.CENTER_ALIGNMENT);
            contenedorCursos.add(lblSinCursos);
        } else {
            for (Curso curso : cursos) {
                JButton btnCurso = new JButton(curso.getNombre() + " - " + curso.getAula().getGrado() + "° " + curso.getAula().getSeccion());
                btnCurso.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnCurso.setMaximumSize(new Dimension(400, 80));
                btnCurso.setFont(new Font("SansSerif", Font.BOLD, 16));
                btnCurso.setBackground(new Color(254, 234, 157));

                btnCurso.addActionListener(e -> {
                    new ContenidoEstudiante(usuario, curso);
                    dispose();
                });
                contenedorCursos.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorCursos.add(btnCurso);
            }
        }

        setVisible(true);
    }
}

