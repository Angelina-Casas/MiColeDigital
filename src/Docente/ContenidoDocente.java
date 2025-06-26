/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import Complementos.ComplementosFrameDocente;
import Estudiante.CalifiEstudiante;
import Modelos.Usuario;
import javax.swing.*;
import java.awt.*;

public class ContenidoDocente extends ComplementosFrameDocente{
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;
    
    public ContenidoDocente (Usuario usuario) {
        super(usuario);
        this.usuario = usuario;
        
        
        add(crearPanelIzquierdo());
         
        add(crearPanelDerecho(" CONTENIDO  -  MICOLEDIGITAL   "));
        
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        panelDerecho.add(btnCabecera1);

        
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
        new CalifiDocente(usuario).setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera2);
        
        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        
        
        String[] semanas = { "Semana 1", "Semana 2", "Semana 3", "Semana 4" };
        
        
        

        
        for (int i = 0; i < semanas.length; i++) {
            JPanel panelPractica = new JPanel(null); 
            panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110)); 
            panelPractica.setPreferredSize(new Dimension(850, 110)); 
            panelPractica.setBackground(new Color(240, 240, 240));
            panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            JLabel lblSemana = new JLabel(semanas[i]);
            lblSemana.setBounds(50, 35, 200, 20);
            lblSemana.setFont(new Font("SansSerif", Font.BOLD, 20));
            panelPractica.add(lblSemana);

            JButton btnVer = new JButton("Ver práctica");
            btnVer.setBounds(650, 35, 120, 30);
            btnVer.setBackground(new Color(39,87,117));
            btnVer.setForeground(Color.WHITE);
            btnVer.addActionListener(e -> {
                new AgregarContenido(usuario).setVisible(true);
            dispose();
            });

            panelPractica.add(btnVer);
            contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
            contenedorScroll.add(panelPractica);
        }

        
        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 420);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scrollPane);
        
        contenedorScroll.setPreferredSize(new Dimension(850, 600)); 

        setVisible(true);
   }

    
}
