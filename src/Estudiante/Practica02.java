/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estudiante;
import Complementos.PruebasSemanales;
import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class Practica02 extends ComplementosFrameEstudiante{
    private JButton btnEnviar;
    private JPanel panelScroll;
    private JScrollPane scrollPane;
    
    
    public Practica02(Usuario usuario){
        super(usuario);
        this.usuario = usuario;
        
        add(crearPanelIzquierdo());
        add(crearPanelDerecho("      PRACTICAS"));
        
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40); // mismo ancho que la mitad del scroll (850 / 2)
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCabecera1.addActionListener(e -> {
        new ContenidoEstudiante(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        // Botón 2 - Prácticas completadas
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); // empieza donde terminó el anterior
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCabecera2.addActionListener(e -> {
        new CalifiEstudiante(usuario).setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera2);
        
        // Panel con preguntas
        panelScroll = new JPanel();
        panelScroll.setLayout(null);
        panelScroll.setPreferredSize(new Dimension(800, 800)); // altura suficiente para scroll
        panelScroll.setBackground(Color.WHITE);

        // Agregamos 4 preguntas por escrito  y radio buttons MODIFIQUEN LAS PREGUNTAS Y SUS RESPUESTAS(para saber cual es la correcta lo hacen desde su codigo, no en diseno)
        PruebasSemanales.agregarPregunta(panelScroll, 1, "¿Cuánto es 2 + 3?", 30, new String[]{"5", "8", "12", "15"});
        PruebasSemanales.agregarPregunta(panelScroll, 2, "Resuelve: (4 + 5) × 2", 180, new String[]{"10", "20", "30", "40"});
        PruebasSemanales.agregarPregunta(panelScroll, 3, "Selecciona la letra correcta", 330, new String[]{"A", "B", "C", "D"});
        PruebasSemanales.agregarPregunta(panelScroll, 4, "¿Te gusta programar?", 480, new String[]{"Sí", "No", "Tal vez", "Nunca"});

        // Botón Enviar
        btnEnviar = new JButton("ENVIAR");
        btnEnviar.setBounds(300, 650, 200, 30);
        btnEnviar.setBackground(new Color(178, 0, 38)); 
        btnEnviar.setForeground(Color.WHITE);
        panelScroll.add(btnEnviar);

        // Scroll
        scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBounds(100, 200, 850, 460);
        panelDerecho.add(scrollPane);
    }

    
}
    
