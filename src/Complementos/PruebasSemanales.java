/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Complementos;
import javax.swing.*;
import java.awt.*;

public class PruebasSemanales {
    public static void agregarPregunta(JPanel destino, int numero, String textoPregunta, int y, String[] opciones) {
        
        JLabel lblPregunta = new JLabel("    PREGUNTA " + numero);
        lblPregunta.setBounds(20, y, 120, 25);
        lblPregunta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        destino.add(lblPregunta);

        
        JLabel lblTexto = new JLabel(textoPregunta, SwingConstants.CENTER);
        lblTexto.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTexto.setOpaque(true);
        lblTexto.setBackground(Color.WHITE);
        lblTexto.setForeground(Color.BLACK);
        lblTexto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        lblTexto.setBounds(60, y + 30, 500, 40);
        destino.add(lblTexto);

        // Opciones
        JRadioButton opcion1 = new JRadioButton(opciones[0]);
        opcion1.setBounds(580, y + 30, 100, 20);

        JRadioButton opcion2 = new JRadioButton(opciones[1]);
        opcion2.setBounds(700, y + 30, 100, 20);

        JRadioButton opcion3 = new JRadioButton(opciones[2]);
        opcion3.setBounds(580, y + 60, 100, 20);

        JRadioButton opcion4 = new JRadioButton(opciones[3]);
        opcion4.setBounds(700, y + 60, 100, 20);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(opcion1);
        grupo.add(opcion2);
        grupo.add(opcion3);
        grupo.add(opcion4);

        destino.add(opcion1);
        destino.add(opcion2);
        destino.add(opcion3);
        destino.add(opcion4);
    }
    
}
