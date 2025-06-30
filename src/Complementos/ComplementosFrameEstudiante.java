package Complementos;

import Estudiante.CursoEstudiante;
import Estudiante.PerfilEstudiante;
import Main.LoginGeneral;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ComplementosFrameEstudiante extends JFrame {

    public static final int ANCHO = 1280;
    public static final int ALTO = 720;

    protected Usuario usuario;
    protected JPanel panelIzquierdo;
    protected JPanel panelDerecho;
    protected JPanel lineaRoja;

    protected JLabel lblLogoIzquierda;
    protected JLabel lblLogoDerecha;
    protected JLabel lblTitulo;

    protected JButton btnCursos;
    protected JButton btnEstudiantes;
    protected JButton btnCerrarSesion;

    private JButton botonSeleccionado = null;

    public ComplementosFrameEstudiante(Usuario usuario) {
        this.usuario = usuario;
        setSize(ANCHO, ALTO);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected void resaltarBoton(JButton boton) {
        if (botonSeleccionado != null && botonSeleccionado != boton) {
            botonSeleccionado.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        }
        botonSeleccionado = boton;
        botonSeleccionado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 3),
            BorderFactory.createEmptyBorder(3, 3, 3, 3)
        ));
    }

    protected JPanel crearPanelIzquierdo() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setBackground(new Color(212, 223, 237));
        panelIzquierdo.setBounds(0, 0, 203, ALTO);
        panelIzquierdo.setLayout(null);

        lblLogoIzquierda = new JLabel();
        lblLogoIzquierda.setBounds(30, 20, 140, 140);
        lblLogoIzquierda.setIcon(new ImageIcon(getClass().getResource("/Img/montefioripequeno.png")));
        panelIzquierdo.add(lblLogoIzquierda);

        URL urlCursos = getClass().getResource("/Img/btnCursos.png");
        URL urlCursosHover = getClass().getResource("/Img/btnCursos_hover.png");
        ImageIcon iconCursos = urlCursos != null ? new ImageIcon(urlCursos) : null;
        ImageIcon iconCursosHover = urlCursosHover != null ? new ImageIcon(urlCursosHover) : null;

        btnCursos = new JButton(iconCursos != null ? iconCursos : new ImageIcon());
        btnCursos.setBounds(10, 220, 180, 40);
        btnCursos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btnCursos.setOpaque(true);
        btnCursos.setBackground(new Color(212, 223, 237));
        btnCursos.setFocusPainted(false);
        btnCursos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCursos.addActionListener(e -> {
            resaltarBoton(btnCursos);
            new CursoEstudiante(usuario).setVisible(true);
            dispose();
        });
        btnCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (iconCursosHover != null) btnCursos.setIcon(iconCursosHover);
                btnCursos.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (iconCursos != null) btnCursos.setIcon(iconCursos);
                if (botonSeleccionado != btnCursos) {
                    btnCursos.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                } else {
                    btnCursos.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 3),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                    ));
                }
            }
        });
        panelIzquierdo.add(btnCursos);

        URL urlEstudiante = getClass().getResource("/Img/btnEstudiantes.png");
        URL urlEstudianteHover = getClass().getResource("/Img/btnEstudiantes_hover.png");
        ImageIcon iconEstudiante = urlEstudiante != null ? new ImageIcon(urlEstudiante) : null;
        ImageIcon iconEstudianteHover = urlEstudianteHover != null ? new ImageIcon(urlEstudianteHover) : null;

        btnEstudiantes = new JButton(iconEstudiante != null ? iconEstudiante : new ImageIcon());
        btnEstudiantes.setBounds(10, 270, 180, 40);
        btnEstudiantes.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btnEstudiantes.setOpaque(true);
        btnEstudiantes.setBackground(new Color(212, 223, 237));
        btnEstudiantes.setFocusPainted(false);
        btnEstudiantes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEstudiantes.addActionListener(e -> {
            resaltarBoton(btnEstudiantes);
            new PerfilEstudiante(usuario).setVisible(true);
            dispose();
        });
        btnEstudiantes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (iconEstudianteHover != null) btnEstudiantes.setIcon(iconEstudianteHover);
                btnEstudiantes.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (iconEstudiante != null) btnEstudiantes.setIcon(iconEstudiante);
                if (botonSeleccionado != btnEstudiantes) {
                    btnEstudiantes.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                } else {
                    btnEstudiantes.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 3),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                    ));
                }
            }
        });
        panelIzquierdo.add(btnEstudiantes);

        URL urlCerrar = getClass().getResource("/Img/btnCerrarSesion.png");
        URL urlCerrarHover = getClass().getResource("/Img/btnCerrarSesion_hover.png");
        ImageIcon iconCerrar = urlCerrar != null ? new ImageIcon(urlCerrar) : null;
        ImageIcon iconCerrarHover = urlCerrarHover != null ? new ImageIcon(urlCerrarHover) : null;

        btnCerrarSesion = new JButton(iconCerrar != null ? iconCerrar : new ImageIcon());
        btnCerrarSesion.setBounds(10, 320, 180, 40);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setBackground(new Color(212, 223, 237));
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(e -> {
            resaltarBoton(btnCerrarSesion);
            new LoginGeneral().setVisible(true);
            dispose();
        });
        btnCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (iconCerrarHover != null) btnCerrarSesion.setIcon(iconCerrarHover);
                btnCerrarSesion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (iconCerrar != null) btnCerrarSesion.setIcon(iconCerrar);
                if (botonSeleccionado != btnCerrarSesion) {
                    btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                } else {
                    btnCerrarSesion.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 3),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                    ));
                }
            }
        });
        panelIzquierdo.add(btnCerrarSesion);

        return panelIzquierdo;
    }

    protected JPanel crearPanelDerecho(String titulo) {
        panelDerecho = new JPanel();
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBounds(203, 0, ANCHO - 203, ALTO);
        panelDerecho.setLayout(null);

        lblLogoDerecha = new JLabel();
        lblLogoDerecha.setBounds(900, 10, 128, 101);
        lblLogoDerecha.setIcon(new ImageIcon(getClass().getResource("/Img/logoMiColePequeno.png")));
        panelDerecho.add(lblLogoDerecha);

        lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setBounds(320,25, 500, 30);
        panelDerecho.add(lblTitulo);

        lineaRoja = new JPanel();
        lineaRoja.setBackground(new Color(39, 87, 117));
        lineaRoja.setBounds(50, 115, 950, 5);
        panelDerecho.add(lineaRoja);

        return panelDerecho;
    }

    protected void marcarBotonActivo(String nombreBoton) {
        if (nombreBoton.equalsIgnoreCase("cursos")) {
            resaltarBoton(btnCursos);
        } else if (nombreBoton.equalsIgnoreCase("estudiantes")) {
            resaltarBoton(btnEstudiantes);
        }
    }
}
