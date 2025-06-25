/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import Complementos.ComplementosFrameDocente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class CalifiDocente extends ComplementosFrameDocente{
    private JButton btnContenido, btnCalificaciones;
    private JTable tabla;
    private JScrollPane scroll;
    
    public CalifiDocente() {
        add(crearPanelIzquierdo());
        add(crearPanelDerecho("MATEMATICA"));

        // Botones superiores
        btnContenido = new JButton("CONTENIDO");
        btnContenido.setBounds(100, 140, 425, 40);
        btnContenido.setBackground(Color.WHITE);
        btnContenido.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnContenido.addActionListener(e -> {
        new ContenidoDocente().setVisible(true);
        dispose();
        });
        panelDerecho.add(btnContenido);
        panelDerecho.add(btnContenido);

        btnCalificaciones = new JButton("CALIFICACIONES");
        btnCalificaciones.setBounds(525, 140, 425, 40);
        btnCalificaciones.setBackground(Color.WHITE);
        btnCalificaciones.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCalificaciones.addActionListener(e -> {
        new CalifiDocente().setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCalificaciones);
        panelDerecho.add(btnCalificaciones);

        // Tabla
        String[] columnas = {"Nombre y Apellidos", "S1", "S2", "S3", "S4", "S5", "S6"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);

        // Establecer tamaños de columna
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desactiva autoajuste

        TableColumn colNombre = tabla.getColumnModel().getColumn(0);
        colNombre.setPreferredWidth(500); // columna "Nombre y Apellidos" más ancha

        for (int i = 1; i <= 6; i++) {
            TableColumn col = tabla.getColumnModel().getColumn(i);
            col.setPreferredWidth(60); 
        }

        // Scroll
        scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 200, 850, 460);
        panelDerecho.add(scroll);
    }
}
