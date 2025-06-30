package Complementos;

import Docente.CursoDocente;
import Docente.PerfilDocente;
import Main.LoginGeneral;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ComplementosFrameDocente extends JFrame {

    public static final int ANCHO = 1280;
    public static final int ALTO = 720;
    private static final Color COLOR_FONDO = new Color(212, 223, 237);

    protected Usuario usuario;
    protected JPanel panelIzquierdo;
    protected JPanel panelDerecho;
    protected JPanel lineaRoja;

    protected JLabel lblLogoIzquierda;
    protected JLabel lblLogoDerecha;
    protected JLabel lblTitulo;

    protected JButton btnCursos;
    protected JButton btnDocentes;
    protected JButton btnCerrarSesion;

    private JButton botonSeleccionado = null;

    public ComplementosFrameDocente(Usuario usuario) {
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
        panelIzquierdo.setBackground(COLOR_FONDO);
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
        btnCursos.setBackground(COLOR_FONDO);
        btnCursos.setFocusPainted(false);
        btnCursos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCursos.addActionListener(e -> {
            resaltarBoton(btnCursos);
            new CursoDocente(usuario).setVisible(true);
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

        URL urlDocentes = getClass().getResource("/Img/btnDocentes.png");
        URL urlDocentesHover = getClass().getResource("/Img/btnDocentes_hover.png");
        ImageIcon iconDocentes = urlDocentes != null ? new ImageIcon(urlDocentes) : null;
        ImageIcon iconDocentesHover = urlDocentesHover != null ? new ImageIcon(urlDocentesHover) : null;

        btnDocentes = new JButton(iconDocentes != null ? iconDocentes : new ImageIcon());
        btnDocentes.setBounds(10, 270, 180, 40);
        btnDocentes.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btnDocentes.setOpaque(true);
        btnDocentes.setBackground(COLOR_FONDO);
        btnDocentes.setFocusPainted(false);
        btnDocentes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDocentes.addActionListener(e -> {
            resaltarBoton(btnDocentes);
            new PerfilDocente(usuario).setVisible(true);
            dispose();
        });
        btnDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (iconDocentesHover != null) btnDocentes.setIcon(iconDocentesHover);
                btnDocentes.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
                    BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (iconDocentes != null) btnDocentes.setIcon(iconDocentes);
                if (botonSeleccionado != btnDocentes) {
                    btnDocentes.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                } else {
                    btnDocentes.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 3),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                    ));
                }
            }
        });
        panelIzquierdo.add(btnDocentes);

        // Botón CERRAR SESIÓN
        URL urlCerrar = getClass().getResource("/Img/btnCerrarSesion.png");
        URL urlCerrarHover = getClass().getResource("/Img/btnCerrarSesion_hover.png");
        ImageIcon iconCerrar = urlCerrar != null ? new ImageIcon(urlCerrar) : null;
        ImageIcon iconCerrarHover = urlCerrarHover != null ? new ImageIcon(urlCerrarHover) : null;

        btnCerrarSesion = new JButton(iconCerrar != null ? iconCerrar : new ImageIcon());
        btnCerrarSesion.setBounds(10, 320, 180, 40);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setBackground(COLOR_FONDO);
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
        lblTitulo.setBounds(350, 30, 500, 30);
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
        } else if (nombreBoton.equalsIgnoreCase("docentes")) {
            resaltarBoton(btnDocentes);
        }
    }
}