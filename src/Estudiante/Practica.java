package Estudiante;

import Complementos.PruebasSemanales;
import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class Practica extends ComplementosFrameEstudiante{
    private JButton btnEnviar;
    private JPanel panelScroll;
    private JScrollPane scrollPane;
    private int numeroPractica;
    
    
    public Practica(Usuario usuario, int numeroPractica){
        super(usuario);
        this.usuario = usuario;
        this.numeroPractica = numeroPractica;
        
        add(crearPanelIzquierdo());
        add(crearPanelDerecho("     PRACTICAS"));
        
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40); // mismo ancho que la mitad del scroll (850 / 2)
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera1.addActionListener(e -> {
        new ContenidoEstudiante(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        // Botón 2 - Prácticas completadas
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); // empieza donde terminó el anterior
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiEstudiante(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);
       crearPanelPreguntas();
       
    }
    
    private void crearPanelPreguntas() {      

        panelScroll = new JPanel();
        panelScroll.setLayout(null);
        panelScroll.setPreferredSize(new Dimension(800, 800)); // altura suficiente para scroll
        panelScroll.setBackground(Color.WHITE);

        // Agregamos 4 preguntas por escrito  y radio buttons MODIFIQUEN LAS PREGUNTAS Y SUS RESPUESTAS(para saber cual es la correcta lo hacen desde su codigo, no en diseno)
        switch(numeroPractica){
            
            case 1: 
                PruebasSemanales.agregarPregunta(panelScroll, 1, "¿Cuánto es: 50 + 3 x 4 - 8 ÷ 2?", 30, new String[]{"58", "60", "62", "56"});
                PruebasSemanales.agregarPregunta(panelScroll, 2, "¿Resultado de: (6 + 4) × 5 - 12 ÷ 4? ", 180, new String[]{"48", "50", "45", "44"});
                PruebasSemanales.agregarPregunta(panelScroll, 3, "Calcula: 72 ÷ (6 x 2) + 3²", 330, new String[]{"9", "15", "12", "18"});
                PruebasSemanales.agregarPregunta(panelScroll, 4, "¿Resultado de: 100 - (8 + 2 x 5)", 480, new String[]{"90", "80", "70", "85"});
                break;
                
            case 2: 
                PruebasSemanales.agregarPregunta(panelScroll, 1, "Resuelve:(15 - 3) x 2 + 6² ÷ 3", 30, new String[]{"42", "44", "40", "38"});
                PruebasSemanales.agregarPregunta(panelScroll, 2, "¿Cuando da: 48 ÷ 4 + (2 x 5) - 3²?", 180, new String[]{"9", "12", "15", "18"});
                PruebasSemanales.agregarPregunta(panelScroll, 3, "Resuelve: [(5 + 5) x 2] - 4 + 8", 330, new String[]{"24", "20", "22", "26"});
                PruebasSemanales.agregarPregunta(panelScroll, 4, "Resultado de: 10² - 6 x 3 + (12 ÷ 4)", 480, new String[]{"76", "70", "60", "64"});
                break;
                
            case 3:
                PruebasSemanales.agregarPregunta(panelScroll, 1, "¿Cuál de estos numero NO es multiplo de 8?", 30, new String[]{"32", "56", "96", "45"});
                PruebasSemanales.agregarPregunta(panelScroll, 2, "¿Cuál de estos es divisor de 72?", 180, new String[]{"8", "7", "9", "98"});
                PruebasSemanales.agregarPregunta(panelScroll, 3, "Si un número es multiplo de 5, termina en:", 330, new String[]{"5 o 10", "5 o 0", "2 o 5", "0 o 2"});
                PruebasSemanales.agregarPregunta(panelScroll, 4, "¿Cual es el menor múltiplo común de 6 y 4?", 480, new String[]{"24", "12", "8", "6"});
                break;
                
            case 4:
                PruebasSemanales.agregarPregunta(panelScroll, 1, "¿Cuál es el MCM de 4 y 6?", 30, new String[]{"24", "12", "8", "10"});
                PruebasSemanales.agregarPregunta(panelScroll, 2, "El MCM de 8 y 10 es: ", 180, new String[]{"40", "20", "10", "80"});
                PruebasSemanales.agregarPregunta(panelScroll, 3, "Si el MCM de 3 y 7 es: ", 330, new String[]{"21", "10", "14", "35"});
                PruebasSemanales.agregarPregunta(panelScroll, 4, "¿Cuál es el MCM de 5, 10 y 15?", 480, new String[]{"15", "30", "60", "45"});
                break;
                
            default:
                JOptionPane.showMessageDialog(this, "Práctica no disponible.");
                break;
        }
                
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
    

