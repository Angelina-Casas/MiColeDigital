/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Complementos.BaseFrame;
import javax.swing.*;
import java.awt.*;

public class VerEstudiantes extends BaseFrame {

    @Override
    protected void initContenido() {
        // Label "AULA"
        JLabel lblAula = new JLabel("AULA");
        lblAula.setFont(new Font("Arial", Font.BOLD, 13));
        lblAula.setBounds(80, 40, 100, 30);
        panelContenido.add(lblAula);

        JLabel lblAulaValor = new JLabel(); // para mostrar el nombre del aula
        lblAulaValor.setBounds(150, 40, 250, 30);
        lblAulaValor.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelContenido.add(lblAulaValor);

        // Label "ESTUDIANTE"
        JLabel lblEstudiante = new JLabel("ESTUDIANTE");
        lblEstudiante.setFont(new Font("Arial", Font.BOLD, 13));
        lblEstudiante.setBounds(80, 90, 120, 30);
        panelContenido.add(lblEstudiante);

        JTextField txtEstudiante = new JTextField();
        txtEstudiante.setBounds(150, 120, 250, 30);
        panelContenido.add(txtEstudiante);

        // Label "DOCENTE"
        JLabel lblDocente = new JLabel("DOCENTE");
        lblDocente.setFont(new Font("Arial", Font.BOLD, 13));
        lblDocente.setBounds(80, 170, 120, 30);
        panelContenido.add(lblDocente);

        JComboBox<String> cbDocente = new JComboBox<>();
        cbDocente.setBounds(150, 200, 250, 30);
        panelContenido.add(cbDocente);

        // Label "DOCENTE ASIGNADO"
        JLabel lblDocenteAsignado = new JLabel("DOCENTE ASIGNADO:");
        lblDocenteAsignado.setFont(new Font("Arial", Font.BOLD, 13));
        lblDocenteAsignado.setBounds(80, 250, 180, 30);
        panelContenido.add(lblDocenteAsignado);

        JLabel lblAsignadoValor = new JLabel(); // valor del docente asignado
        lblAsignadoValor.setBounds(80, 280, 320, 30);
        lblAsignadoValor.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelContenido.add(lblAsignadoValor);

        // Botones: ELIMINAR, EDITAR, AGREGAR
        String[] botones = { "ELIMINAR", "EDITAR", "AGREGAR" };
        int y = 340;
        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.setBounds(100, y, 200, 35);
            panelContenido.add(boton);
            y += 50;
        }

        // Panel blanco de la derecha
        JPanel panelTabla = new JPanel();
        panelTabla.setBounds(450, 30, 700, 400);
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelContenido.add(panelTabla);

        // Botón REGRESAR
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 480, 120, 35);
        panelContenido.add(btnRegresar);
    }

}