/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;
import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameDocente;
import Modelos.Usuario;

public class CursoDocente extends ComplementosFrameDocente{
    //aqui vamos agregando otros botones conforme se creen otros cursos para el profesor
    private JButton btnCursoMatematica;
    
    public CursoDocente(Usuario usuario){
        super(usuario);
        this.usuario = usuario;
        
        add(crearPanelIzquierdo());
        // Panel derecho reutilizable 
        add(crearPanelDerecho("BIENVENIDO A MICOLEDIGITAL"));

        btnCursoMatematica = new JButton("Matemáticas 6°");
        btnCursoMatematica.setBounds(300, 200, 400, 100);
        btnCursoMatematica.setBackground(new Color(255, 248, 180));
        btnCursoMatematica.setFont(new Font("Serif", Font.PLAIN, 20));
        btnCursoMatematica.addActionListener(e -> {
            new AgregarContenido(usuario).setVisible(true);
            dispose();
            });
        panelDerecho.add(btnCursoMatematica);

        setVisible(true);
    }
}
