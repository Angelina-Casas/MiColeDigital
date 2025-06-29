package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.CursoBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PerfilEstudiante extends ComplementosFrameEstudiante {
    private Usuario usuario;

    private JLabel lblFoto, lblInfoBasica, lblInfoAdicional, lblNombre, lblCorreo;
    private JLabel lblGrado, lblSeccion;
    private JLabel lblNombreValor, lblCorreoValor, lblGradoValor, lblSeccionValor;
    private JTextArea areaCursos;
    private JScrollPane scrollCursos;

    public PerfilEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        // Imagen
        lblFoto = new JLabel();
        lblFoto.setBounds(460, 170, 180, 180);
        lblFoto.setIcon(new ImageIcon(getClass().getResource("/Img/estudiantefoto.png")));
        panelDerecho.add(lblFoto);

        // Títulos
        lblInfoBasica = new JLabel("Información básica");
        lblInfoBasica.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoBasica.setBounds(280, 380, 200, 30);
        panelDerecho.add(lblInfoBasica);
        lblInfoAdicional = new JLabel("Información adicional");
        lblInfoAdicional.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoAdicional.setBounds(700, 380, 220, 30);
        panelDerecho.add(lblInfoAdicional);

        // Nombre
        lblNombre = new JLabel("NOMBRE:");
        lblNombre.setFont(new Font("Serif", Font.BOLD, 14));
        lblNombre.setBounds(280, 415, 100, 30);
        panelDerecho.add(lblNombre);

        lblNombreValor = new JLabel(usuario.getNombre());
        lblNombreValor.setFont(new Font("Serif", Font.PLAIN, 14));
        lblNombreValor.setBounds(380, 415, 300, 30);
        panelDerecho.add(lblNombreValor);

        // Correo
        lblCorreo = new JLabel("CORREO:");
        lblCorreo.setFont(new Font("Serif", Font.BOLD, 14));
        lblCorreo.setBounds(280, 450, 100, 30);
        panelDerecho.add(lblCorreo);

        lblCorreoValor = new JLabel(usuario.getCorreo());
        lblCorreoValor.setFont(new Font("Serif", Font.PLAIN, 14));
        lblCorreoValor.setBounds(380, 450, 300, 30);
        panelDerecho.add(lblCorreoValor);

        // Cursos
        JLabel lblCursos = new JLabel("CURSOS:");
        lblCursos.setFont(new Font("Serif", Font.BOLD, 14));
        lblCursos.setBounds(280, 485, 100, 30);
        panelDerecho.add(lblCursos);

        areaCursos = new JTextArea();
        areaCursos.setFont(new Font("Serif", Font.PLAIN, 14));
        areaCursos.setEditable(false);
        areaCursos.setBackground(panelDerecho.getBackground());
        areaCursos.setLineWrap(true);
        areaCursos.setWrapStyleWord(true);

        scrollCursos = new JScrollPane(areaCursos);
        scrollCursos.setBounds(380, 485, 300, 80);
        scrollCursos.setBorder(null);
        panelDerecho.add(scrollCursos);

        // Grado
        lblGrado = new JLabel("GRADO:");
        lblGrado.setFont(new Font("Serif", Font.BOLD, 14));
        lblGrado.setBounds(700, 415, 100, 30);
        panelDerecho.add(lblGrado);

        lblGradoValor = new JLabel("N/A");
        lblGradoValor.setFont(new Font("Serif", Font.PLAIN, 14));
        lblGradoValor.setBounds(800, 415, 100, 30);
        panelDerecho.add(lblGradoValor);

        // Sección
        lblSeccion = new JLabel("SECCIÓN:");
        lblSeccion.setFont(new Font("Serif", Font.BOLD, 14));
        lblSeccion.setBounds(700, 450, 100, 30);
        panelDerecho.add(lblSeccion);

        lblSeccionValor = new JLabel("N/A");
        lblSeccionValor.setFont(new Font("Serif", Font.PLAIN, 14));
        lblSeccionValor.setBounds(800, 450, 100, 30);
        panelDerecho.add(lblSeccionValor);

        // Cargar datos de BD
        cargarCursosGradoSeccion(usuario.getIdUsuario());

        setVisible(true);
    }

    private void cargarCursosGradoSeccion(int idUsuario) {
        CursoBD cursoBD = new CursoBD();
        List<Curso> cursos = cursoBD.listarCursosPorEstudiante(idUsuario);

        StringBuilder textoCursos = new StringBuilder();
        String grado = null;
        String seccion = null;

        for (Curso curso : cursos) {
            if (grado == null && curso.getAula() != null) {
                grado = String.valueOf(curso.getAula().getGrado());
                seccion = curso.getAula().getSeccion();
            }
            textoCursos.append("- ").append(curso.getNombre()).append("\n");
        }

        areaCursos.setText(textoCursos.length() == 0 ? "Sin cursos asignados." : textoCursos.toString());
        lblGradoValor.setText(grado == null ? "N/A" : grado);
        lblSeccionValor.setText(seccion == null ? "N/A" : seccion);
    }
}