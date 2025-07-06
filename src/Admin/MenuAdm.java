package Admin;

import Complementos.BaseFrame;
import Main.LoginGeneral;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;

public class MenuAdm extends BaseFrame {
    private final Usuario usuario;

    public MenuAdm(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        agregarLogo();
        agregarImagenes();
        agregarBotones();
    }

    // === Secciones UI ===

    private void agregarLogo() {
        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/Img/logoMiColePequeno.png")));
        lblLogo.setBounds(1040, 5, 128, 101);
        panelContenido.add(lblLogo);
    }

    private void agregarImagenes() {
        agregarImagen("/Img/imgUsuarios.png", 180, 180, 220, 140);
        agregarImagen("/Img/imgAulas1.png", 475, 180, 220, 140);
        agregarImagen("/Img/imgCursos.png", 770, 180, 220, 150);
    }

    private void agregarImagen(String ruta, int x, int y, int w, int h) {
        JLabel imagen = new JLabel(new ImageIcon(getClass().getResource(ruta)));
        imagen.setBounds(x, y, w, h);
        panelContenido.add(imagen);
    }

    private void agregarBotones() {
        agregarBoton("USUARIOS", 210, 340, new Color(102, 204, 0), () -> abrirVentana(new AdmUsuario(usuario)));
        agregarBoton("AULAS", 500, 340, new Color(255, 165, 0), () -> abrirVentana(new AdmAula(usuario)));
        agregarBoton("CURSOS", 790, 340, new Color(0, 153, 204), () -> abrirVentana(new AdmCurso(usuario)));
        agregarBotonCerrarSesion();
    }

    private void agregarBoton(String texto, int x, int y, Color color, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 170, 50);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.addActionListener(e -> accion.run());
        panelContenido.add(boton);
    }

    private void agregarBotonCerrarSesion() {
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setBounds(950, 500, 150, 40);
        btnCerrarSesion.setBackground(new Color(39, 87, 117));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 13));
        btnCerrarSesion.addActionListener(e -> abrirVentana(new LoginGeneral()));
        panelContenido.add(btnCerrarSesion);
    }

    private void abrirVentana(JFrame frame) {
        frame.setVisible(true);
        dispose();
    }
}