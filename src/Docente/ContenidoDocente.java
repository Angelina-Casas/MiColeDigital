/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;
import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameDocente;
import Estudiante.CalifiEstudiante;

public class ContenidoDocente extends ComplementosFrameDocente {
    private JScrollPane scrollPaneFormulario;
    private JPanel panelFormulario;
    private JPanel panelVistaPrevia;
    
   public ContenidoDocente() {
        // Panel izquierdo
        add(crearPanelIzquierdo());

        // Panel derecho con título
        add(crearPanelDerecho("CONTENIDO - MICOLEDIGITAL"));

        // Botones cabecera
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        panelDerecho.add(btnCabecera1);

        // Botón 2 - Prácticas completadas
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCabecera2.addActionListener(e -> {
        new CalifiEstudiante().setVisible(true);
        dispose();
        });
        panelDerecho.add(btnCabecera2);

        // === PANEL FORMULARIO ===
        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setPreferredSize(new Dimension(500, 600)); // activa scroll si crece
        panelFormulario.setBackground(Color.WHITE);

        int y = 20;

        panelFormulario.add(crearLabel("Ingrese nombre de la Práctica:", 30, y));
        panelFormulario.add(crearTextField(270, y, 200));
        y += 40;

        panelFormulario.add(crearLabel("Ingrese tema de la práctica:", 30, y));
        panelFormulario.add(crearTextField(270, y, 200));
        y += 40;

        panelFormulario.add(crearLabel("Video:", 30, y));
        panelFormulario.add(crearTextField(270, y, 200));
        y += 40;

        panelFormulario.add(crearLabel("Ingrese número de pregunta:", 30, y));
        JComboBox<String> comboNumero = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        comboNumero.setBounds(270, y, 100, 25);
        panelFormulario.add(comboNumero);
        y += 40;

        panelFormulario.add(crearLabel("Pregunta:", 30, y));
        panelFormulario.add(crearTextField(270, y, 300));
        y += 40;

        for (int i = 1; i <= 4; i++) {
            panelFormulario.add(crearLabel("Opción " + i + ":", 30, y));
            panelFormulario.add(crearTextField(270, y, 300));
            y += 40;
        }

        panelFormulario.add(crearLabel("Alternativa correcta:", 30, y));
        JComboBox<String> comboAlternativa = new JComboBox<>(new String[]{"A", "B", "C", "D"});
        comboAlternativa.setBounds(270, y, 100, 25);
        panelFormulario.add(comboAlternativa);
        y += 50;

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(270, y, 150, 35);
        btnGuardar.setBackground(new Color(178, 0, 38));
        btnGuardar.setForeground(Color.WHITE);
        panelFormulario.add(btnGuardar);

        // Scroll que contiene el formulario
        scrollPaneFormulario = new JScrollPane(panelFormulario);
        scrollPaneFormulario.setBounds(100, 180, 500, 450);  // más abajo y alineado a la izquierda
        scrollPaneFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneFormulario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDerecho.add(scrollPaneFormulario);

        // === PANEL DERECHO DE PREVISUALIZACIÓN ===
        panelVistaPrevia = new JPanel();
        panelVistaPrevia.setBounds(630, 180, 320, 450); 
        panelVistaPrevia.setBackground(Color.LIGHT_GRAY);
        panelVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelDerecho.add(panelVistaPrevia);

        setVisible(true);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 250, 25);
        return label;
    }

    private JTextField crearTextField(int x, int y, int ancho) {
        JTextField field = new JTextField();
        field.setBounds(x, y, ancho, 25);
        return field;
    }
}
 

