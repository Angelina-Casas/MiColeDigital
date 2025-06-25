/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import javax.swing.*;
import java.awt.*;

public class CrearUsuarios extends JFrame{
    public CrearUsuarios() {
        setTitle("Gestión de Usuarios");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Panel de fondo amarillo claro
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 249, 200)); // amarillo claro
        panel.setBounds(50, 50, 800, 400);
        panel.setLayout(null);
        add(panel);

        // Etiquetas y campos
        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(20, 10, 80, 20);
        panel.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(20, 30, 180, 25);
        panel.add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(20, 60, 80, 20);
        panel.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(20, 80, 180, 25);
        panel.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contrasena");
        lblContrasena.setBounds(20, 110, 100, 20);
        panel.add(lblContrasena);

        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setBounds(20, 130, 180, 25);
        panel.add(txtContrasena);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setBounds(20, 160, 80, 20);
        panel.add(lblRol);

        JComboBox<String> cbRol = new JComboBox<>(new String[]{"Admin", "Docente", "Estudiante"});
        cbRol.setBounds(20, 180, 180, 25);
        panel.add(cbRol);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 230, 100, 30);
        panel.add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(20, 270, 100, 30);
        panel.add(btnEliminar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(20, 310, 100, 30);
        panel.add(btnEditar);

        // JList para mostrar usuarios
        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaUsuarios = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaUsuarios);
        scrollLista.setBounds(220, 10, 550, 330);
        panel.add(scrollLista);

        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setBounds(600, 350, 150, 35);
        panel.add(btnCerrarSesion);

        setVisible(true);
    }
    
}
