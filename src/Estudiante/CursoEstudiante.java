package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.CursoBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CursoEstudiante extends ComplementosFrameEstudiante {
    private JPanel contenedorCursos;

    public CursoEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        inicializarContenedorCursos();
        cargarCursosDelEstudiante();

        setVisible(true);
    }

    private void inicializarContenedorCursos() {
        contenedorCursos = new JPanel();
        contenedorCursos.setLayout(new BoxLayout(contenedorCursos, BoxLayout.Y_AXIS));
        contenedorCursos.setBackground(Color.WHITE);
        contenedorCursos.setBounds(340, 180, 400, 400);

        panelDerecho.setLayout(null);
        panelDerecho.add(contenedorCursos);
    }

    private void cargarCursosDelEstudiante() {
        CursoBD cursoBD = new CursoBD();
        List<Curso> cursos = cursoBD.listarCursosPorEstudiante(usuario.getIdUsuario());

        if (cursos.isEmpty()) {
            mostrarMensajeSinCursos();
        } else {
            for (Curso curso : cursos) {
                contenedorCursos.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorCursos.add(crearBotonCurso(curso));
            }
        }
    }

    private void mostrarMensajeSinCursos() {
        JLabel lblSinCursos = new JLabel("No tienes cursos asignados.");
        lblSinCursos.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblSinCursos.setAlignmentX(Component.CENTER_ALIGNMENT);
        contenedorCursos.add(lblSinCursos);
    }

    private JButton crearBotonCurso(Curso curso) {
        String textoBoton = curso.getNombre() + " - " + curso.getAula().getGrado() + "° " + curso.getAula().getSeccion();

        JButton btnCurso = new JButton(textoBoton);
        btnCurso.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCurso.setMaximumSize(new Dimension(400, 80));
        btnCurso.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnCurso.setBackground(new Color(254, 234, 157));

        btnCurso.addActionListener(e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });

        return btnCurso;
    }
}