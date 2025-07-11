package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.PracticaBD;
import Modelos.Evaluador;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.PreguntaFormulario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Practica extends ComplementosFrameEstudiante {
    private Usuario usuario;
    private Curso curso;
    private int numeroPractica;
    private boolean enviada = false;

    private JRadioButton[] respuestasCorrectas;
    private ButtonGroup[] grupos;
    private JPanel panelScroll;
    private JScrollPane scrollPane;

    public Practica(Usuario usuario, Curso curso, int numeroPractica) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;
        this.numeroPractica = numeroPractica;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("PRACTICAS - " + curso.getNombre().toUpperCase()));

        JButton btnContenido = new JButton("Contenido");
        btnContenido.setBounds(100, 140, 425, 40);
        btnContenido.setBackground(Color.WHITE);
        btnContenido.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnContenido.addActionListener(e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnContenido);

        JButton btnCalificaciones = new JButton("Calificaciones");
        btnCalificaciones.setBounds(525, 140, 425, 40);
        btnCalificaciones.setBackground(Color.WHITE);
        btnCalificaciones.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCalificaciones.addActionListener(e -> {
            new CalifiEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCalificaciones);

        crearPanelPreguntas();
    }

    private void crearPanelPreguntas() {
        panelScroll = new JPanel(null);
        panelScroll.setPreferredSize(new Dimension(800, 1000));
        panelScroll.setBackground(Color.WHITE);

        PracticaBD bd = new PracticaBD();

        if (bd.practicaYaEnviada(usuario.getIdUsuario(), numeroPractica)) {
            int notaAnterior = bd.obtenerNotaAnterior(usuario.getIdUsuario(), numeroPractica);
            JLabel mensaje = new JLabel("Ya enviaste esta práctica. Nota anterior: " + notaAnterior);
            mensaje.setFont(new Font("Arial", Font.BOLD, 16));
            mensaje.setForeground(Color.RED);
            mensaje.setBounds(260, 200, 600, 30);
            panelScroll.add(mensaje);
            enviada = true;
        } else {
            List<PreguntaFormulario> preguntas = bd.obtenerPreguntasFormulario(numeroPractica);
            List<ButtonGroup> grupoList = new java.util.ArrayList<>();
            List<JRadioButton> correctasList = new java.util.ArrayList<>();
            int yBase = 30;

            for (PreguntaFormulario p : preguntas) {
                int nroPregunta = p.getNroPregunta();
                String pregunta = p.getPregunta();
                String[] opciones = {p.getOpcion1(), p.getOpcion2(), p.getOpcion3(), p.getOpcion4()};
                String correcta = p.getRespuestaCorrecta(); // ejemplo: "1", "2", etc.

                JLabel lbl = new JLabel(nroPregunta + ". " + pregunta);
                lbl.setBounds(30, yBase, 700, 20);
                panelScroll.add(lbl);

                ButtonGroup grupo = new ButtonGroup();
                grupoList.add(grupo);

                for (int i = 0; i < opciones.length; i++) {
                    if (opciones[i] == null) continue;

                    JRadioButton radio = new JRadioButton(opciones[i]);
                    radio.setBounds(50, yBase + 30 + i * 25, 700, 20);
                    panelScroll.add(radio);
                    grupo.add(radio);

                    if (correcta.equals(String.valueOf(i + 1))) {
                        correctasList.add(radio);
                    }
                }
                yBase += 150;
            }


            respuestasCorrectas = correctasList.toArray(new JRadioButton[0]);
            grupos = grupoList.toArray(new ButtonGroup[0]);

            JButton btnEnviar = new JButton("ENVIAR");
            btnEnviar.setBounds(300, yBase, 200, 30);
            btnEnviar.setBackground(new Color(178, 0, 38));
            btnEnviar.setForeground(Color.WHITE);
            panelScroll.add(btnEnviar);

            btnEnviar.addActionListener(e -> {
                if (!Evaluador.todosLosGruposRespondidos(grupos)) {
                    JOptionPane.showMessageDialog(this, "Responde todas las preguntas antes de enviar.");
                    return;
                }
                int nota = Evaluador.calcularNota(grupos, respuestasCorrectas);
                if (bd.insertarResultadoPractica(usuario.getIdUsuario(), numeroPractica, nota)) {
                    JOptionPane.showMessageDialog(this,
                        "¡Tu nota fue subida con éxito!\nObtuviste " + nota + " puntos.",
                        "Resultado enviado", JOptionPane.INFORMATION_MESSAGE);
                    btnEnviar.setEnabled(false);
                    enviada = true;
                    ContenidoEstudiante contenido = new ContenidoEstudiante(usuario, curso);
                    contenido.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar tu resultado.");
                }
            });
        }

        scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBounds(100, 200, 850, 460);
        panelDerecho.add(scrollPane);
    }
}
