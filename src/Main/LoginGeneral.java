package Main;

import Estudiante.PerfilEstudiante;
import Complementos.ComplementosFrameEstudiante;
import javax.swing.*;
import java.awt.*;

public class LoginGeneral extends ComplementosFrameEstudiante{
    private JLabel lblLogoIzquierda;
    private JLabel lblIconoUsuario;
    private JLabel lblLogoDerecha;
    private JLabel lblTitulo;
    private JLabel lblUsuario;
    private JLabel lblContrasena;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    
    public LoginGeneral() { 
    
        
        JPanel panelIzquierdologin = new JPanel();
        panelIzquierdologin .setBackground(new Color(255, 248, 220)); 
        panelIzquierdologin .setBounds(0, 0, 640, 720);
        panelIzquierdologin .setLayout(null);

        lblLogoIzquierda = new JLabel();
        lblLogoIzquierda.setBounds(155,154, 330, 413); 
        lblLogoIzquierda.setIcon(new ImageIcon(getClass().getResource("/Imagenes/montefioriLogoGrande1.png")));
        panelIzquierdologin.add(lblLogoIzquierda);

        
        JPanel panelDerechoLogin = new JPanel();
        panelDerechoLogin.setBackground(Color.WHITE);
        panelDerechoLogin.setBounds(640, 0, 640, 720);
        panelDerechoLogin.setLayout(null);

        
        lblLogoDerecha = new JLabel();
        lblLogoDerecha.setBounds(494,15,98,78);
        lblLogoDerecha.setIcon(new ImageIcon(getClass().getResource("/Imagenes/logoMiColePequeno.png")));
        panelDerechoLogin.add(lblLogoDerecha); 

        // Título
        lblTitulo = new JLabel("INICIAR SESION");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setBounds(215, 160, 200, 30);
        panelDerechoLogin.add(lblTitulo);

        
        lblIconoUsuario = new JLabel();
        lblIconoUsuario.setBounds(250, 230, 95, 95);
        lblIconoUsuario.setIcon(new ImageIcon(getClass().getResource("/Imagenes/personaIniciarSesion.png")));
        panelDerechoLogin.add(lblIconoUsuario);

        
        lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUsuario.setBounds(215, 350, 100, 20);
        panelDerechoLogin.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(215, 375, 200, 30);
        txtUsuario.setBackground(new Color(210, 235, 255)); // azul clarito
        panelDerechoLogin.add(txtUsuario);

        
        lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblContrasena.setBounds(215, 420, 100, 20);
        panelDerechoLogin.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(215, 445, 200, 30);
        txtContrasena.setBackground(new Color(210, 235, 255));
        panelDerechoLogin.add(txtContrasena);

        
        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(215, 500, 200, 40);
        btnIngresar.setBackground(new Color(178, 0, 38)); 
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(e -> {
        PerfilEstudiante menu = new PerfilEstudiante();
        menu.setVisible(true);
        dispose(); 
        });
        panelDerechoLogin.add(btnIngresar);

        
        add(panelIzquierdologin);
        add(panelDerechoLogin);

        setVisible(true);
    }
   
    

}
