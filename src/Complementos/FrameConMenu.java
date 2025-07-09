package Complementos;

import Main.LoginGeneral;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public abstract class FrameConMenu extends JFrame {

    public static final int ANCHO = 1280;
    public static final int ALTO = 720;
    protected static final Color COLOR_FONDO = new Color(212, 223, 237);
    protected static final Color COLOR_LINEA = new Color(39, 87, 117);

    protected Usuario usuario;
    protected JPanel panelIzquierdo, panelDerecho, lineaRoja;
    protected JLabel lblLogoIzquierda, lblLogoDerecha, lblTitulo;
    protected JButton btnCerrarSesion;
    private JButton botonSeleccionado;

    public FrameConMenu(Usuario usuario) {
        this.usuario = usuario;
        configurarVentana();
    }

    private void configurarVentana() {
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

    protected JPanel crearPanelIzquierdoBase() {
        panelIzquierdo = new JPanel(null);
        panelIzquierdo.setBackground(COLOR_FONDO);
        panelIzquierdo.setBounds(0, 0, 203, ALTO);

        lblLogoIzquierda = crearLogo("/Img/montefioripequeno.png", 30, 20, 140, 140);
        panelIzquierdo.add(lblLogoIzquierda);

        btnCerrarSesion = crearBotonMenu("/Img/btnCerrarSesion.png", "/Img/btnCerrarSesion_hover.png", 320, () -> {
            resaltarBoton(btnCerrarSesion);
            new LoginGeneral().setVisible(true);
            dispose();
        });
        panelIzquierdo.add(btnCerrarSesion);

        return panelIzquierdo;
    }

    protected JPanel crearPanelDerecho(String titulo) {
        panelDerecho = new JPanel(null);
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBounds(203, 0, ANCHO - 203, ALTO);

        lblLogoDerecha = crearLogo("/Img/logoMiColePequeno.png", 900, 10, 128, 101);
        lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setBounds(315, 40, 500, 30);

        lineaRoja = new JPanel();
        lineaRoja.setBackground(COLOR_LINEA);
        lineaRoja.setBounds(50, 115, 950, 5);

        panelDerecho.add(lblLogoDerecha);
        panelDerecho.add(lblTitulo);
        panelDerecho.add(lineaRoja);

        return panelDerecho;
    }

    protected JButton crearBotonMenu(String iconPath, String hoverPath, int y, Runnable accion) {
        ImageIcon icon = cargarIcono(iconPath);
        ImageIcon iconHover = cargarIcono(hoverPath, icon);

        JButton btn = new JButton(icon);
        btn.setBounds(10, y, 180, 40);
        btn.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        btn.setOpaque(true);
        btn.setBackground(COLOR_FONDO);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> accion.run());

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setIcon(iconHover);
                btn.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.WHITE, 2),
                        BorderFactory.createEmptyBorder(3, 3, 3, 3)
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setIcon(icon);
                if (botonSeleccionado != btn) {
                    btn.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                } else {
                    btn.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.WHITE, 3),
                            BorderFactory.createEmptyBorder(3, 3, 3, 3)
                    ));
                }
            }
        });

        return btn;
    }

    private ImageIcon cargarIcono(String path) {
        URL url = getClass().getResource(path);
        return url != null ? new ImageIcon(url) : new ImageIcon();
    }

    private ImageIcon cargarIcono(String path, ImageIcon fallback) {
        URL url = getClass().getResource(path);
        return url != null ? new ImageIcon(url) : fallback;
    }

    private JLabel crearLogo(String path, int x, int y, int width, int height) {
        JLabel lbl = new JLabel(new ImageIcon(getClass().getResource(path)));
        lbl.setBounds(x, y, width, height);
        return lbl;
    }
}
