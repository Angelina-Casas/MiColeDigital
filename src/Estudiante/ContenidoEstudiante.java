/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estudiante;
import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;

public class ContenidoEstudiante extends ComplementosFrameEstudiante {
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;
    
   public ContenidoEstudiante(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;
        // Panel izquierdo reutilizable
        add(crearPanelIzquierdo());
        // Panel derecho reutilizable 
        add(crearPanelDerecho(" CONTENIDO  -  MICOLEDIGITAL   "));
        //
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        panelDerecho.add(btnCabecera1);

        // Botón 2 - Prácticas completadas
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
        new CalifiEstudiante(usuario).setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera2);
        
        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        
        // Datos de prácticas
        String[] semanas = { "Semana 1", "Semana 2", "Semana 3", "Semana 4" };
        String[] temas = {
            "Operaciones Combinadas",
            "Algoritmos Básicos",
            "Geometría de Figuras Planas",
            "Ecuaciones Simples"
        };
       
        //AQUI VAN LOS LIKNS DE LAS CLASES POR SEMANA
        String[] urls = {
            "https://www.youtube.com/watch?v=RZzyWljhMEw",
            "https://www.youtube.com/watch?v=-acFUpFSgo4",
            "https://www.youtube.com/watch?v=YW_04Esg4QQ",
            "https://www.youtube.com/watch?v=-SCa4B7VoAs"
        };

        // Crear los 4 paneles
        for (int i = 0; i < semanas.length; i++) {
            JPanel panelPractica = new JPanel(null); 
            panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110)); 
            panelPractica.setPreferredSize(new Dimension(850, 110)); 
            panelPractica.setBackground(new Color(240, 240, 240));
            panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            
            JLabel lblSemana = new JLabel(semanas[i]);
            lblSemana.setBounds(20, 10, 200, 20);
            lblSemana.setFont(new Font("SansSerif", Font.BOLD, 14));
            panelPractica.add(lblSemana);

            JLabel lblTema = new JLabel("Tema: " + temas[i]);
            lblTema.setBounds(20, 35, 400, 20);
            panelPractica.add(lblTema);

            int index = i;
            JLabel lblURL = new JLabel(urls[i]);
            lblURL.setBounds(20, 60, 500, 20);
            lblURL.setForeground(Color.BLUE);
            panelPractica.add(lblURL);
            lblURL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); 
            lblURL.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            abrirEnlace(urls[index]);
            }
            });

            JButton btnVer = new JButton("Ver práctica");
            btnVer.setBounds(650, 35, 120, 30);
            btnVer.setBackground(new Color(39,87,117));
            btnVer.setForeground(Color.WHITE);
            btnVer.addActionListener(e -> {
            dispose();
            });

             // para usar dentro del lambda

            btnVer.addActionListener(e -> {
                switch (index) {
                    case 0 -> new Practica(usuario,1).setVisible(true);
                    case 1 -> new Practica(usuario,2).setVisible(true);
                    case 2 -> new Practica(usuario,3).setVisible(true);
                    case 3 -> new Practica(usuario,4).setVisible(true);
                }
                dispose();
            });

            panelPractica.add(btnVer);
            contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
            contenedorScroll.add(panelPractica);
        }

        // Scroll con borde visible
        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 420);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scrollPane);
        
        contenedorScroll.setPreferredSize(new Dimension(850, 600)); // ancho igual al scroll

        setVisible(true);
   }
   
   private void abrirEnlace(String url) {
    try {
        Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new java.net.URI(url));
            } else {
            JOptionPane.showMessageDialog(this, "Tu sistema no permite abrir enlaces.");
            }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al abrir el enlace: " + e.getMessage());}
    }
}
