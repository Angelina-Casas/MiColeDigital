/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import Complementos.BaseFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class AdmUsuarios extends BaseFrame {
    
    @Override
    protected void initContenido() {
    
    int xForm = 40;

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(xForm, 10, 100, 20);
        panelContenido.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(xForm, 30, 220, 30);
        panelContenido.add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(xForm, 70, 100, 20);
        panelContenido.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(xForm, 90, 220, 30);
        panelContenido.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(xForm, 130, 100, 20);
        panelContenido.add(lblContrasena);

        JPasswordField txtContrasena = new JPasswordField();
        txtContrasena.setBounds(xForm, 150, 220, 30);
        panelContenido.add(txtContrasena);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setBounds(xForm, 190, 100, 20);
        panelContenido.add(lblRol);

        JComboBox<String> cbRol = new JComboBox<>(new String[]{"Admin", "Docente", "Estudiante"});
        cbRol.setBounds(xForm, 210, 220, 30);
        panelContenido.add(cbRol);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(40, 260, 140, 35);
        btnAgregar.setBackground(new Color(144, 238, 144));
        panelContenido.add(btnAgregar);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setBounds(40, 305, 140, 35);
        btnEditar.setBackground(new Color(255, 102, 102));
        panelContenido.add(btnEditar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(40, 350, 140, 35);
        btnEliminar.setBackground(new Color(173, 216, 230));
        panelContenido.add(btnEliminar);
        
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(970, 510, 150, 40);
        btnRegresar.setBackground(new Color(255, 249, 200));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 13));
        
        btnRegresar.addActionListener(e -> {
            new MenuAdm().setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaUsuarios = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaUsuarios);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        
    
    
    }
    
    
}
