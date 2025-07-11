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

    private JLabel lblNombreValor, lblCorreoValor, lblGradoValor, lblSeccionValor;
    private JTextArea areaCursos;

    public PerfilEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        inicializarComponentes();
        cargarCursosGradoSeccion(usuario.getIdUsuario());

        setVisible(true);
    }

    private void inicializarComponentes() {
        agregarFotoPerfil();
        agregarSeccionTitulos();
        agregarInformacionBasica();
        agregarInformacionAdicional();
    }

    private void agregarFotoPerfil() {
        JLabel lblFoto = new JLabel(new ImageIcon(getClass().getResource("/Img/estudiantefoto.png")));
        lblFoto.setBounds(460, 170, 180, 180);
        panelDerecho.add(lblFoto);
    }

    private void agregarSeccionTitulos() {
        JLabel lblInfoBasica = new JLabel("Información básica");
        lblInfoBasica.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoBasica.setBounds(280, 380, 200, 30);
        panelDerecho.add(lblInfoBasica);

        JLabel lblInfoAdicional = new JLabel("Información adicional");
        lblInfoAdicional.setFont(new Font("Serif", Font.BOLD, 18));
        lblInfoAdicional.setBounds(700, 380, 220, 30);
        panelDerecho.add(lblInfoAdicional);
    }

    private void agregarInformacionBasica() {
        crearEtiqueta("NOMBRE:", 280, 415);
        lblNombreValor = crearValor(usuario.getNombre(), 380, 415);

        crearEtiqueta("CORREO:", 280, 450);
        lblCorreoValor = crearValor(usuario.getCorreo(), 380, 450);

        crearEtiqueta("CURSOS:", 280, 485);

        areaCursos = new JTextArea();
        areaCursos.setFont(new Font("Serif", Font.PLAIN, 14));
        areaCursos.setEditable(false);
        areaCursos.setBackground(panelDerecho.getBackground());
        areaCursos.setLineWrap(true);
        areaCursos.setWrapStyleWord(true);

        JScrollPane scrollCursos = new JScrollPane(areaCursos);
        scrollCursos.setBounds(380, 490, 300, 80);
        scrollCursos.setBorder(null);
        panelDerecho.add(scrollCursos);
    }

    private void agregarInformacionAdicional() {
        crearEtiqueta("GRADO:", 700, 415);
        lblGradoValor = createPlaceholderValue(800, 415);

        crearEtiqueta("SECCIÓN:", 700, 450);
        lblSeccionValor = createPlaceholderValue(800, 450);
    }

    private void cargarCursosGradoSeccion(int idUsuario) {
        CursoBD cursoBD = new CursoBD();
        List<Curso> cursos = cursoBD.listarCursosPorEstudiante(idUsuario);

        StringBuilder cursosTexto = new StringBuilder();
        String grado = null;
        String seccion = null;

        for (Curso curso : cursos) {
            if (grado == null && curso.getAula() != null) {
                grado = String.valueOf(curso.getAula().getGrado());
                seccion = curso.getAula().getSeccion();
            }
            cursosTexto.append("- ").append(curso.getNombre()).append("\n");
        }

        areaCursos.setText(cursosTexto.length() == 0 ? "Sin cursos asignados." : cursosTexto.toString());
        lblGradoValor.setText(grado != null ? grado : "N/A");
        lblSeccionValor.setText(seccion != null ? seccion : "N/A");
    }

    private void crearEtiqueta(String texto, int x, int y) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(new Font("Serif", Font.BOLD, 14));
        etiqueta.setBounds(x, y, 100, 30);
        panelDerecho.add(etiqueta);
    }

    private JLabel crearValor(String texto, int x, int y) {
        JLabel valor = new JLabel(texto);
        valor.setFont(new Font("Serif", Font.PLAIN, 14));
        valor.setBounds(x, y, 300, 30);
        panelDerecho.add(valor);
        return valor;
    }

    private JLabel createPlaceholderValue(int x, int y) {
        return crearValor("N/A", x, y);
    }
}
