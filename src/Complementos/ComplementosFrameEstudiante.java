package Complementos;

import Estudiante.CursoEstudiante;
import Estudiante.PerfilEstudiante;
import Main.LoginGeneral;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class ComplementosFrameEstudiante extends JFrame{
    
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
    protected JButton btnEstudiante;
    protected JButton btnCerrarSesion;

    public ComplementosFrameEstudiante(Usuario usuario) {
        this.usuario = usuario;
        setSize(ANCHO, ALTO);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected JPanel crearPanelIzquierdo() {
    panelIzquierdo = new JPanel();
    panelIzquierdo.setBackground(new Color(212, 223, 237));
    panelIzquierdo.setBounds(0, 0, 203, ALTO);
    panelIzquierdo.setLayout(null);

    lblLogoIzquierda = new JLabel();
    lblLogoIzquierda.setBounds(30, 20, 140,140);
    lblLogoIzquierda.setIcon(new ImageIcon(getClass().getResource("/Img/montefioripequeno.png")));
    panelIzquierdo.add(lblLogoIzquierda);

    btnCursos = new JButton("CURSOS");
    btnCursos.setBounds(10, 220, 180, 40);
    btnCursos.setBackground(new Color(254, 234, 157));
    btnCursos.addActionListener(e -> {
        new CursoEstudiante(usuario).setVisible(true);
        dispose();
    });
    panelIzquierdo.add(btnCursos);

    btnEstudiante = new JButton("ESTUDIANTE");
    btnEstudiante.setBounds(10, 270, 180, 40);
    btnEstudiante.setBackground(new Color(254, 234, 157));
    btnEstudiante.addActionListener(e -> {
        
        new PerfilEstudiante(usuario).setVisible(true);
        dispose();
    });
    panelIzquierdo.add(btnEstudiante);

    btnCerrarSesion = new JButton("CERRAR SESIÓN");
    btnCerrarSesion.setBounds(10, 320, 180, 40);
    btnCerrarSesion.setBackground(new Color(254, 234, 157));
    btnCerrarSesion.addActionListener(e -> {
        new LoginGeneral().setVisible(true);
        dispose();
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
        lineaRoja.setBackground(new Color(39,87,117));
        lineaRoja.setBounds(50, 115, 950, 5);
        panelDerecho.add(lineaRoja);

        return panelDerecho;
    }
}

