package Docente;

import Modelos.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import Complementos.ComplementosFrameDocente;
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
    private int idFormularioActual = 0;

    private Curso curso;

    public AgregarContenido(Usuario usuario, Curso curso, int idFormulario) {
        super(usuario);

        this.usuario = usuario;
        this.curso = curso;
        this.idFormularioActual = idFormulario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO - MICOLEDIGITAL"));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera1.addActionListener(e -> {
            new ContenidoDocente(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiDocente(usuario, curso).setVisible(true); // ← LÍNEA CORREGIDA
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

        try {
            ConexionBD conexionBD = new ConexionBD();
            FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());

            if (idFormularioActual != 0) {
                Formulario formulario = formularioBD.obtenerFormularioPorId(idFormularioActual);
                if (formulario != null) {
                    txtNombre.setText(formulario.getNombreFor());
                    txtTema.setText(formulario.getTema());
                    txtVideo.setText(formulario.getVideoUrl());
                }

                preguntas = formularioBD.obtenerPreguntas(idFormularioActual);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del formulario: " + ex.getMessage());
        }

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
        btnEliminar.setBounds(340, y + 138, 100, 35);
        btnEliminar.setBackground(new Color(234, 98, 85));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> {
            if (idFormularioActual == 0) {
                JOptionPane.showMessageDialog(this, "No se puede eliminar una práctica que aún no ha sido guardada.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que desea borrar la práctica?", "Confirmación",
                    JOptionPane.OK_CANCEL_OPTION);

            if (confirmacion == JOptionPane.OK_OPTION) {
                try {
                    ConexionBD conexionBD = new ConexionBD();
                    FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());

                    boolean exito = formularioBD.eliminarFormularioYPreguntas(idFormularioActual);
                    if (exito) {
                        JOptionPane.showMessageDialog(this, "Práctica eliminada correctamente.");
                        new ContenidoDocente(usuario, curso).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al eliminar la práctica.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
                }
            }
        });
        panelDerecho.add(btnEliminar);

        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.setBounds(100, y + 138, 100, 35);
        btnVisualizar.setBackground(new Color(134, 199, 231));
        btnVisualizar.setForeground(Color.WHITE);
        btnVisualizar.addActionListener(e -> mostrarPrevisualizacion());
        panelDerecho.add(btnVisualizar);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(220, y + 138, 100, 35);
        btnGuardar.setBackground(new Color(89, 196, 107));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> {
            String msg = (idFormularioActual == 0)
                    ? "¿Estás seguro de subir esta nueva práctica?" : "¿Deseas actualizar esta práctica existente?";
            int confirmacion = JOptionPane.showConfirmDialog(this, msg, "Confirmación", JOptionPane.OK_CANCEL_OPTION);
            if (confirmacion == JOptionPane.OK_OPTION) {
                actualizarPreguntaDesdeCampos();
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

        setVisible(true);
    }

    public AgregarContenido(Usuario usuario, Curso curso) {
        this(usuario, curso, 0);
    }

    private void mostrarPrevisualizacion() {
        int numero = Integer.parseInt((String) comboNumero.getSelectedItem());

        String preguntaTexto = txtPregunta.getText().trim();
        String[] opciones = {
                txtOpciones[0].getText().trim(),
                txtOpciones[1].getText().trim(),
                txtOpciones[2].getText().trim(),
                txtOpciones[3].getText().trim()
        };
        String alternativa = (String) comboAlternativa.getSelectedItem();

        if (!preguntaTexto.isEmpty() || !opciones[0].isEmpty() || !opciones[1].isEmpty()
                || !opciones[2].isEmpty() || !opciones[3].isEmpty()) {
            PreguntaFormulario nueva = new PreguntaFormulario(numero, preguntaTexto,
                    opciones[0], opciones[1], opciones[2], opciones[3], alternativa);

            boolean reemplazada = false;
            for (int i = 0; i < preguntas.size(); i++) {
                if (preguntas.get(i).getNroPregunta() == numero) {
                    preguntas.set(i, nueva);
                    reemplazada = true;
                    break;
                }
            }
            if (!reemplazada) {
                preguntas.add(nueva);
            }
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

    private void actualizarPreguntaDesdeCampos() {
        int numero = Integer.parseInt((String) comboNumero.getSelectedItem());

        PreguntaFormulario nueva = new PreguntaFormulario(
                numero,
                txtPregunta.getText(),
                txtOpciones[0].getText(),
                txtOpciones[1].getText(),
                txtOpciones[2].getText(),
                txtOpciones[3].getText(),
                (String) comboAlternativa.getSelectedItem()
        );

        boolean reemplazada = false;
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getNroPregunta() == numero) {
                preguntas.set(i, nueva);
                reemplazada = true;
                break;
            }
        }

        if (!reemplazada) {
            preguntas.add(nueva);
        }
    }

    private void guardarEnBaseDeDatos() {
        Formulario formulario = new Formulario(
                txtNombre.getText(),
                txtTema.getText(),
                txtVideo.getText()
        );
        formulario.setIdCurso(curso.getIdCurso());

        try {
            ConexionBD conexionBD = new ConexionBD();
            FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());

            boolean exito;
            if (idFormularioActual == 0) {
                exito = formularioBD.insertarFormularioYPreguntas(formulario, preguntas);
            } else {
                formulario.setIdFor(idFormularioActual);
                exito = formularioBD.actualizarFormularioYPreguntas(formulario, preguntas);
            }

            if (exito) {
                String msg = (idFormularioActual == 0) ? "Formulario y preguntas guardados correctamente" : "Formulario y preguntas actualizados correctamente";
                JOptionPane.showMessageDialog(this, msg);
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar los datos");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
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
