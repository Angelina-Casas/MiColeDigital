/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estudiante;
import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;

public class CursoEstudiante extends ComplementosFrameEstudiante{
   private JButton btnCursoMatematica;

    public CursoEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;
        // Panel izquierdo reutilizable
        add(crearPanelIzquierdo());
        // Panel derecho reutilizable 
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        btnCursoMatematica = new JButton("Matemáticas 6°");
        btnCursoMatematica.setBounds(300, 200, 400, 100);
        btnCursoMatematica.setBackground(new Color(255, 220, 80));
        btnCursoMatematica.setFont(new Font("Serif", Font.PLAIN, 20));
        btnCursoMatematica.addActionListener(e -> {
        new ContenidoEstudiante(usuario).setVisible(true);
        dispose();
    });
        panelDerecho.add(btnCursoMatematica);

        setVisible(true);
    }
}
    

