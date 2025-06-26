/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Complementos.BaseFrame;
import javax.swing.*;
import java.awt.*;

public class AdmAula extends BaseFrame {

    @Override
    protected void initContenido() {
        
        JLabel lblGrado = new JLabel("Grado:");
        lblGrado.setBounds(80, 60, 100, 30);
        lblGrado.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblGrado);

        JTextField txtGrado = new JTextField();
        txtGrado.setBounds(150, 60, 200, 30);
        panelContenido.add(txtGrado);

        JLabel lblSeccion = new JLabel("Sección:");
        lblSeccion.setBounds(80, 110, 100, 30);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblSeccion);

        JTextField txtSeccion = new JTextField();
        txtSeccion.setBounds(150, 110, 200, 30);
        panelContenido.add(txtSeccion);

        
        String[] nombresBotones = { "VER ESTUDIANTES", "AGREGAR", "EDITAR", "ELIMINAR" };
        int y = 180;
        for (String nombre : nombresBotones) {
            JButton boton = new JButton(nombre);
            boton.setBounds(80, y, 280, 35);
            boton.setBackground(new Color(173, 216, 230));
            panelContenido.add(boton);
            y += 70;
        }

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaCursos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaCursos);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 500, 120, 35);
        btnRegresar.setBackground(new Color(173, 216, 230));
        panelContenido.add(btnRegresar);
    }

}
