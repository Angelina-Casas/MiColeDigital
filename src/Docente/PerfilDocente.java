package Docente;

import Complementos.ComplementosFrameDocente;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.CursoBD;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PerfilDocente extends ComplementosFrameDocente {
    private JLabel lblFoto, lblInfoBasica, lblInfoAdicional;
    private JLabel lblNombre, lblGrado, lblSeccion;
    private JLabel lblNombreValor, lblGradoValor, lblSeccionValor;
    private JTextArea areaCursos;
    private JScrollPane scrollCursos;

    public PerfilDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        inicializarComponentes();
        cargarDatosDelDocente(usuario.getIdUsuario());

        setVisible(true);
    }

    private void inicializarComponentes() {
        crearImagenPerfil();
        crearEtiquetasTitulos();
        crearEtiquetasBasicas();
        crearAreaCursos();
        crearEtiquetasAdicionales();
    }

    private void crearImagenPerfil() {
        lblFoto = new JLabel();
        lblFoto.setBounds(460, 170, 180, 180);
        lblFoto.setIcon(new ImageIcon(getClass().getResource("/Img/profesorfoto.png")));
        panelDerecho.add(lblFoto);
    }

    private void crearEtiquetasTitulos() {
        lblInfoBasica = crearEtiqueta("Información básica", 280, 380, 200, 30, Font.BOLD, 18);
        lblInfoAdicional = crearEtiqueta("Información adicional", 700, 380, 220, 30, Font.BOLD, 18);
    }

    private void crearEtiquetasBasicas() {
        lblNombre = crearEtiqueta("NOMBRE:", 280, 415, 100, 30, Font.BOLD, 14);
        lblNombreValor = crearEtiqueta(usuario.getNombre(), 380, 415, 300, 30, Font.PLAIN, 14);

        JLabel lblCursos = crearEtiqueta("CURSOS:", 280, 450, 100, 30, Font.BOLD, 14);
    }

    private void crearAreaCursos() {
        areaCursos = new JTextArea();
        areaCursos.setFont(new Font("Serif", Font.PLAIN, 14));
        areaCursos.setEditable(false);
        areaCursos.setBackground(panelDerecho.getBackground());
        areaCursos.setLineWrap(true);
        areaCursos.setWrapStyleWord(true);

        scrollCursos = new JScrollPane(areaCursos);
        scrollCursos.setBounds(380, 455, 300, 80);
        scrollCursos.setBorder(null);
        panelDerecho.add(scrollCursos);
    }

    private void crearEtiquetasAdicionales() {
        lblGrado = crearEtiqueta("GRADO:", 700, 415, 100, 30, Font.BOLD, 14);
        lblGradoValor = crearEtiqueta("N/A", 800, 415, 100, 30, Font.PLAIN, 14);

        lblSeccion = crearEtiqueta("SECCIÓN:", 700, 450, 100, 30, Font.BOLD, 14);
        lblSeccionValor = crearEtiqueta("N/A", 800, 450, 100, 30, Font.PLAIN, 14);
    }

    private JLabel crearEtiqueta(String texto, int x, int y, int w, int h, int estilo, int tamaño) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Serif", estilo, tamaño));
        lbl.setBounds(x, y, w, h);
        panelDerecho.add(lbl);
        return lbl;
    }

    private void cargarDatosDelDocente(int idDocente) {
        CursoBD cursoBD = new CursoBD();
        List<Curso> cursos = cursoBD.listarCurso();
        StringBuilder cursosTexto = new StringBuilder();
        String grado = null;
        String seccion = null;

        for (Curso curso : cursos) {
            if (curso.getDocente() != null && curso.getDocente().getIdUsuario() == idDocente) {
                cursosTexto.append("- ").append(curso.getNombre()).append("\n");

                if (grado == null && curso.getAula() != null) {
                    grado = String.valueOf(curso.getAula().getGrado());
                    seccion = curso.getAula().getSeccion();
                }
            }
        }

        areaCursos.setText(cursosTexto.length() > 0 ? cursosTexto.toString() : "Sin cursos asignados.");
        lblGradoValor.setText(grado != null ? grado : "N/A");
        lblSeccionValor.setText(seccion != null ? seccion : "N/A");
    }
}
