
package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class CalifiEstudiante extends ComplementosFrameEstudiante {
    private JButton btnContenido;
    private JButton btnCalificaciones;
    private JTable tablaNotas;
    private JScrollPane scrollPane;
    
    public CalifiEstudiante(Usuario usuario){
        super(usuario);
        this.usuario = usuario;
        // Panel izquierdo reutilizable
        add(crearPanelIzquierdo());
        // Panel derecho reutilizable 
        add(crearPanelDerecho(" CALIFICACIONES  -  MICOLEDIGITAL   "));
        
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40); 
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera1.addActionListener(e -> {
        new ContenidoEstudiante(usuario).setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
        new CalifiEstudiante(usuario).setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera2);
    
        String[] columnas = {"Practicas", "Nota"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablaNotas = new JTable(modelo);
        tablaNotas.setRowHeight(25);
        tablaNotas.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));
        scrollPane = new JScrollPane(tablaNotas);
        scrollPane.setBounds(100, 200, 850, 400);
        panelDerecho.add(scrollPane);

    }       
}