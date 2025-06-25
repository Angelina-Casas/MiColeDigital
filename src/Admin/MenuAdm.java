package Admin;


import Complementos.BaseFrame;
import Main.LoginGeneral;
import javax.swing.*;
import java.awt.*;

public class MenuAdm extends BaseFrame {

    @Override
    protected void initContenido() {
        
            JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/logoMiColePequeno.png")));
        lblLogo.setBounds(1080, 10, 80, 80);
        panelContenido.add(lblLogo);

        
        JLabel imgUsuarios = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/imgUsuarios.png")));
        imgUsuarios.setBounds(200, 180, 220, 140);
        panelContenido.add(imgUsuarios);

        

        JLabel imgCursos = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/imgCursos.png")));
        imgCursos.setBounds(670, 180, 220, 140);
        panelContenido.add(imgCursos);

        
        JButton btnUsuarios = new JButton("USUARIOS");
        btnUsuarios.setBounds(225, 340, 170, 50);
        btnUsuarios.setBackground(new Color(102, 204, 0));
        btnUsuarios.setForeground(Color.WHITE);
        btnUsuarios.setFont(new Font("Arial", Font.BOLD, 14));
        btnUsuarios.addActionListener(e -> {
        new AdmUsuarios().setVisible(true);
            dispose();
        });
        panelContenido.add(btnUsuarios);


        JButton btnCursos = new JButton("CURSOS");
        btnCursos.setBounds(700,340 , 170, 50);
        btnCursos.setBackground(new Color(0, 153, 204));  
        btnCursos.setForeground(Color.WHITE);
        btnCursos.setFont(new Font("Arial", Font.BOLD, 14));
        btnCursos.addActionListener(e -> {
        new AdmCurso().setVisible(true);
            dispose();
        });
        panelContenido.add(btnCursos);

        
        JButton btnCerrarSesion = new JButton("CERRAR SESIÓN");
        btnCerrarSesion.setBounds(1000, 500, 150, 40);
        btnCerrarSesion.setBackground(new Color(255, 249, 200));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 13));
        btnCerrarSesion.addActionListener(e -> {
        new LoginGeneral().setVisible(true);
            dispose();
        });
        
        panelContenido.add(btnCerrarSesion);
    }

}