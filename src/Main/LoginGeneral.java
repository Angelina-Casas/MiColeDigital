package Main;

import Admin.MenuAdm;
import Docente.CursoDocente;
import Estudiante.CursoEstudiante;
import Modelos.Usuario;
import Modelos.UsuarioBD;

import javax.swing.*;
import java.awt.*;

public class LoginGeneral extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginGeneral() {
        setTitle("Login - MiColeDigital");
        setSize(1280, 720);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(crearPanelIzquierdo());
        add(crearPanelDerecho());

        setVisible(true);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panelIzquierdo = new JPanel(null);
        panelIzquierdo.setBackground(new Color(212, 223, 237));
        panelIzquierdo.setBounds(0, 0, 640, 720);

        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(155, 154, 330, 413);
        lblLogo.setIcon(new ImageIcon(getClass().getResource("/Img/montefioriLogoGrande1.png")));
        panelIzquierdo.add(lblLogo);

        return panelIzquierdo;
    }

    private JPanel crearPanelDerecho() {
        JPanel panelDerecho = new JPanel(null);
        panelDerecho.setBackground(Color.WHITE);
        panelDerecho.setBounds(640, 0, 640, 720);

        agregarComponentesLogin(panelDerecho);

        return panelDerecho;
    }

    private void agregarComponentesLogin(JPanel panel) {
        JLabel lblLogoPeque = new JLabel();
        lblLogoPeque.setBounds(494, 15, 125, 101);
        lblLogoPeque.setIcon(new ImageIcon(getClass().getResource("/Img/logoMiColePequeno.png")));
        panel.add(lblLogoPeque);

        JLabel lblTitulo = new JLabel("INICIAR SESIÓN");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setBounds(217, 160, 200, 30);
        panel.add(lblTitulo);

        JLabel lblIconoUsuario = new JLabel();
        lblIconoUsuario.setBounds(250, 230, 95, 95);
        lblIconoUsuario.setIcon(new ImageIcon(getClass().getResource("/Img/personaIniciarSesion.png")));
        panel.add(lblIconoUsuario);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblUsuario.setBounds(215, 350, 100, 20);
        panel.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(215, 375, 200, 30);
        txtUsuario.setBackground(new Color(210, 235, 255));
        panel.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblContrasena.setBounds(215, 420, 100, 20);
        panel.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(215, 445, 200, 30);
        txtContrasena.setBackground(new Color(210, 235, 255));
        panel.add(txtContrasena);

        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(215, 500, 200, 40);
        btnIngresar.setBackground(new Color(39, 87, 117));
        btnIngresar.setForeground(Color.WHITE);
        btnIngresar.setFocusPainted(false);
        btnIngresar.addActionListener(e -> autenticarUsuario());
        panel.add(btnIngresar);

        getRootPane().setDefaultButton(btnIngresar);
    }

    private void autenticarUsuario() {
        String correo = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword()).trim();

        UsuarioBD usuarioBD = new UsuarioBD();
        Usuario usuario = usuarioBD.validarUsuario(correo, contrasena);

        if (usuario != null) {
            String rol = usuario.getRol().getNombreRol().toLowerCase();
            JOptionPane.showMessageDialog(this, "¡Bienvenido " + usuario.getNombre() + " (" + rol + ")!");

            switch (rol) {
                case "estudiante" -> new CursoEstudiante(usuario).setVisible(true);
                case "docente" -> new CursoDocente(usuario).setVisible(true);
                case "administrador" -> new MenuAdm(usuario).setVisible(true);
                default -> JOptionPane.showMessageDialog(this, "Rol no reconocido.");
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.");
        }
    }
}