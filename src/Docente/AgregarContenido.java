/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Complementos.ComplementosFrameDocente;
import Modelos.Usuario;
import Modelos.Formulario;
import Modelos.PreguntaFormulario;
import Modelos.FormularioBD;
import Conexion.ConexionBD; 

public class AgregarContenido extends ComplementosFrameDocente {
    private JScrollPane scrollPaneFormulario;
    private JPanel panelFormulario;
    private JPanel panelVistaPrevia;
    private JTextField txtNombre, txtTema, txtVideo, txtPregunta;
    private JTextField[] txtOpciones = new JTextField[4];
    private JComboBox<String> comboNumero, comboAlternativa;
    private JButton btnGuardar;
    private List<PreguntaFormulario> preguntas = new ArrayList<>();

    
    public AgregarContenido(Usuario usuario) {
    this(usuario, 0);
    }
    
    public AgregarContenido(Usuario usuario, int idFormulario) {
        super(usuario);
        this.usuario = usuario;
        
        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO - MICOLEDIGITAL"));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiDocente(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setPreferredSize(new Dimension(500, 600));
        panelFormulario.setBackground(Color.WHITE);
        int y = 20;

        panelFormulario.add(crearLabel("Ingrese nombre de la Práctica:", 30, y));
        txtNombre = crearTextField(270, y, 200);
        panelFormulario.add(txtNombre);
        y += 40;

        panelFormulario.add(crearLabel("Ingrese tema de la práctica:", 30, y));
        txtTema = crearTextField(270, y, 200);
        panelFormulario.add(txtTema);
        y += 40;

        panelFormulario.add(crearLabel("Video:", 30, y));
        txtVideo = crearTextField(270, y, 200);
        panelFormulario.add(txtVideo);
        y += 40;

        panelFormulario.add(crearLabel("Número de preguntas: 4", 30, y));
        comboNumero = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboNumero.setBounds(270, y, 100, 25);
        panelFormulario.add(comboNumero);
        y += 40;

        panelFormulario.add(crearLabel("Pregunta:", 30, y));
        txtPregunta = crearTextField(270, y, 200);
        panelFormulario.add(txtPregunta);
        y += 40;

        for (int i = 0; i < 4; i++) {
            panelFormulario.add(crearLabel("Opción " + (i + 1) + ":", 30, y));
            txtOpciones[i] = crearTextField(270, y, 200);
            panelFormulario.add(txtOpciones[i]);
            y += 40;
        }

        panelFormulario.add(crearLabel("Alternativa correcta:", 30, y));
        comboAlternativa = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboAlternativa.setBounds(270, y, 100, 25);
        panelFormulario.add(comboAlternativa);
        y += 50;

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(460, y+138, 100, 35);
        btnEliminar.setBackground(new Color(234, 98, 85));
        btnEliminar.setForeground(Color.WHITE);
        
        panelDerecho.add(btnEliminar);
        
        
        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.setBounds(300, y+138, 100, 35);
        btnVisualizar.setBackground(new Color(134, 199, 231));
        btnVisualizar.setForeground(Color.WHITE);
        btnVisualizar.addActionListener(e -> mostrarPrevisualizacion());
        panelDerecho.add(btnVisualizar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(140, y+138, 100, 35);
        btnGuardar.setBackground(new Color(89,196,107));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de subir esta práctica?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            if (confirmacion == JOptionPane.OK_OPTION) {
                guardarEnBaseDeDatos();
            }
        });
        panelDerecho.add(btnGuardar);

        scrollPaneFormulario = new JScrollPane(panelFormulario);
        scrollPaneFormulario.setBounds(100, 200, 520, 350);
        scrollPaneFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneFormulario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDerecho.add(scrollPaneFormulario);

        panelVistaPrevia = new JPanel();
        panelVistaPrevia.setBounds(630, 200, 320, 430);
        panelVistaPrevia.setBackground(Color.LIGHT_GRAY);
        panelVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelVistaPrevia.setLayout(new BorderLayout());
        panelDerecho.add(panelVistaPrevia);

        
        if (idFormulario != 0) {
            cargarFormularioDesdeBD(idFormulario);
        }
        setVisible(true);
    }

    private void mostrarPrevisualizacion() {
        int numero = Integer.parseInt((String) comboNumero.getSelectedItem());
        PreguntaFormulario pregunta = new PreguntaFormulario(
                numero,
                txtPregunta.getText(),
                txtOpciones[0].getText(),
                txtOpciones[1].getText(),
                txtOpciones[2].getText(),
                txtOpciones[3].getText(),
                (String) comboAlternativa.getSelectedItem()
        );

        // Agregar o reemplazar la pregunta
        if (preguntas.size() >= numero) {
            preguntas.set(numero - 1, pregunta);
        } else {
            preguntas.add(pregunta);
        }

        JTextArea area = new JTextArea();
        area.setText("Nombre práctica: " + txtNombre.getText() +
                "\nTema: " + txtTema.getText() +
                "\nVideo: " + txtVideo.getText());
        for (PreguntaFormulario pf : preguntas) {
            area.append("\n\nNro pregunta: " + pf.getNroPregunta());
            area.append("\nPregunta: " + pf.getPregunta());
            area.append("\nOpción 1: " + pf.getOpcion1());
            area.append("\nOpción 2: " + pf.getOpcion2());
            area.append("\nOpción 3: " + pf.getOpcion3());
            area.append("\nOpción 4: " + pf.getOpcion4());
            area.append("\nAlternativa correcta: " + pf.getRespuestaCorrecta());
        }
        area.setEditable(false);
        panelVistaPrevia.removeAll();
        panelVistaPrevia.add(new JScrollPane(area), BorderLayout.CENTER);
        panelVistaPrevia.revalidate();
        panelVistaPrevia.repaint();
    }

    private void guardarEnBaseDeDatos() {
        Formulario formulario = new Formulario(
                txtNombre.getText(),
                txtTema.getText(),
                txtVideo.getText()
        );
        try {
            ConexionBD conexionBD = new ConexionBD();
            FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());
            boolean exito = formularioBD.insertarFormularioYPreguntas(formulario, preguntas);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Formulario y preguntas guardados correctamente");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
        }
    }

    private void cargarFormularioDesdeBD(int idFormulario) {
        try {
        ConexionBD conexion = new ConexionBD();
        FormularioBD formularioBD = new FormularioBD(conexion.obtenerConexion());

        
        Formulario form = formularioBD.obtenerFormulario(idFormulario);
        List<PreguntaFormulario> preguntasCargadas = formularioBD.obtenerPreguntas(idFormulario);

        
        txtNombre.setText(form.getNombreFor());
        txtTema.setText(form.getTema());
        txtVideo.setText(form.getVideoUrl());

        
        if (!preguntasCargadas.isEmpty()) {
            PreguntaFormulario pf = preguntasCargadas.get(0);
            comboNumero.setSelectedItem(String.valueOf(pf.getNroPregunta()));
            txtPregunta.setText(pf.getPregunta());
            txtOpciones[0].setText(pf.getOpcion1());
            txtOpciones[1].setText(pf.getOpcion2());
            txtOpciones[2].setText(pf.getOpcion3());
            txtOpciones[3].setText(pf.getOpcion4());
            comboAlternativa.setSelectedItem(pf.getRespuestaCorrecta());
        }

        
        preguntas = preguntasCargadas;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando práctica: " + ex.getMessage());
        }
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