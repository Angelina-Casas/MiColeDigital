package Admin;

import Complementos.BaseFrame;
import Main.LoginGeneral;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class MenuAdm extends BaseFrame {
    
    private Usuario usuario;

    public MenuAdm(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }
    
    @Override
    protected void initContenido() {
        
        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/Img/logoMiColePequeno.png")));
        lblLogo.setBounds(1040, 05, 128, 101);
        panelContenido.add(lblLogo);

        JLabel imgUsuarios = new JLabel(new ImageIcon(getClass().getResource("/Img/imgUsuarios.png")));
        imgUsuarios.setBounds(180, 180, 220, 140);
        panelContenido.add(imgUsuarios);
        
        JLabel imgAulas = new JLabel(new ImageIcon(getClass().getResource("/Img/imgAulas1.png")));
        imgAulas.setBounds(475, 180, 220, 140);
        panelContenido.add(imgAulas);

        JLabel imgCursos = new JLabel(new ImageIcon(getClass().getResource("/Img/imgCursos.png")));
        imgCursos.setBounds(770, 180, 220, 150);
        panelContenido.add(imgCursos);

        JButton btnUsuarios = new JButton("USUARIOS");
        btnUsuarios.setBounds(210, 340, 170, 50);
        btnUsuarios.setBackground(new Color(102, 204, 0));
        btnUsuarios.setForeground(Color.WHITE);
        btnUsuarios.setFont(new Font("Arial", Font.BOLD, 14));
        btnUsuarios.addActionListener((var e) -> {
            new AdmUsuario(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnUsuarios);

        JButton btnAulas = new JButton("AULAS");
        btnAulas.setBounds(500,340 , 170, 50);
        btnAulas.setBackground(new Color(255, 165, 0));  
        btnAulas.setForeground(Color.WHITE);
        btnAulas.setFont(new Font("Arial", Font.BOLD, 14));
        btnAulas.addActionListener(e -> {
            new AdmAula(usuario).setVisible(true);
                dispose();
        });
        panelContenido.add(btnAulas);
        
        JButton btnCursos = new JButton("CURSOS");
        btnCursos.setBounds(790,340 , 170, 50);
        btnCursos.setBackground(new Color(0, 153, 204));  
        btnCursos.setForeground(Color.WHITE);
        btnCursos.setFont(new Font("Arial", Font.BOLD, 14));
        btnCursos.addActionListener(e -> {
            new AdmCurso(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnCursos);

        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setBounds(950, 500, 150, 40);
        btnCerrarSesion.setBackground(new Color(39,87,117));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 13));
        btnCerrarSesion.addActionListener(e -> {
        new LoginGeneral().setVisible(true);
            dispose();
        });
        panelContenido.add(btnCerrarSesion);
    }

}