package Docente;

import Complementos.ComplementosFrameDocente;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.CursoBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PerfilDocente extends ComplementosFrameDocente {
    private Usuario usuario;

    private JLabel lblFoto, lblInfoBasica, lblInfoAdicional, lblNombre;
    private JLabel lblGrado, lblSeccion;
    private JLabel lblNombreValor, lblGradoValor, lblSeccionValor;
    private JTextArea areaCursos;
    private JScrollPane scrollCursos;

    public PerfilDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        // Foto
        lblFoto = new JLabel();
        lblFoto.setBounds(460, 170, 180, 180);
        lblFoto.setIcon(new ImageIcon(getClass().getResource("/Img/profesorfoto.png")));
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

        // Cursos
        JLabel lblCursos = new JLabel("CURSOS:");
        lblCursos.setFont(new Font("Serif", Font.BOLD, 14));
        lblCursos.setBounds(280, 450, 100, 30);
        panelDerecho.add(lblCursos);

        areaCursos = new JTextArea();
        areaCursos.setFont(new Font("Serif", Font.PLAIN, 14));
        areaCursos.setEditable(false);
        areaCursos.setBackground(panelDerecho.getBackground());
        areaCursos.setLineWrap(true);
        areaCursos.setWrapStyleWord(true);

        scrollCursos = new JScrollPane(areaCursos);
        scrollCursos.setBounds(380, 450, 300, 80);
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

        // Cargar cursos y aula
        cargarDatosDelDocente(usuario.getIdUsuario());

        setVisible(true);
    }

    private void cargarDatosDelDocente(int idDocente) {
        CursoBD cursoBD = new CursoBD();
        List<Curso> todosLosCursos = cursoBD.listarCurso(); // reutilizamos tu método
        StringBuilder cursosTexto = new StringBuilder();
        String grado = null;
        String seccion = null;

        for (Curso curso : todosLosCursos) {
            if (curso.getDocente() != null && curso.getDocente().getIdUsuario() == idDocente) {
                cursosTexto.append("- ").append(curso.getNombre()).append("\n");

                if (grado == null && curso.getAula() != null) {
                    grado = String.valueOf(curso.getAula().getGrado());
                    seccion = curso.getAula().getSeccion();
                }
            }
        }

        areaCursos.setText(cursosTexto.length() == 0 ? "Sin cursos asignados." : cursosTexto.toString());
        lblGradoValor.setText(grado != null ? grado : "N/A");
        lblSeccionValor.setText(seccion != null ? seccion : "N/A");
    }
}
