package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AgregarContenido extends ComplementosFrameDocente {

    private JScrollPane scrollPaneFormulario;
    private JPanel panelFormulario, panelVistaPrevia;
    private JTextField txtNombre, txtTema, txtVideo, txtPregunta;
    private JTextField[] txtOpciones = new JTextField[4];
    private JComboBox<String> comboNumero, comboAlternativa;
    private JButton btnGuardar;
    private List<PreguntaFormulario> preguntas = new ArrayList<>();
    private int idFormularioActual = 0;

    private Curso curso;

    public AgregarContenido(Usuario usuario, Curso curso) {
        this(usuario, curso, 0);
    }

    public AgregarContenido(Usuario usuario, Curso curso, int idFormulario) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;
        this.idFormularioActual = idFormulario;

        configurarVistaBase();
        cargarDatosFormulario();
        configurarFormularioPregunta();
        configurarBotonesInferiores();

        setVisible(true);
    }

    private void configurarVistaBase() {
        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO - MICOLEDIGITAL"));

        agregarBotonCabecera("Contenido", 100, () -> {
            new ContenidoDocente(usuario, curso).setVisible(true);
            dispose();
        });

        agregarBotonCabecera("Calificaciones", 525, () -> {
            new CalifiDocente(usuario, curso).setVisible(true);
            dispose();
        });
    }

    private void agregarBotonCabecera(String texto, int x, Runnable evento) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        boton.addActionListener(e -> evento.run());
        panelDerecho.add(boton);
    }

    private void cargarDatosFormulario() {
        if (idFormularioActual == 0) return;

        try {
            ConexionBD conexionBD = new ConexionBD();
            FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());

            Formulario formulario = formularioBD.obtenerFormularioPorId(idFormularioActual);
            if (formulario != null) {
                txtNombre.setText(formulario.getNombreFor());
                txtTema.setText(formulario.getTema());
                txtVideo.setText(formulario.getVideoUrl());
            }

            preguntas = formularioBD.obtenerPreguntas(idFormularioActual);
        } catch (Exception ex) {
            mostrarMensaje("Error al cargar datos del formulario: " + ex.getMessage());
        }
    }

    private void configurarFormularioPregunta() {
        panelFormulario = new JPanel(null);
        panelFormulario.setPreferredSize(new Dimension(500, 600));
        panelFormulario.setBackground(Color.WHITE);
        int y = 20;

        txtNombre = agregarCampo(panelFormulario, "Ingrese nombre de la Práctica:", y); y += 40;
        txtTema = agregarCampo(panelFormulario, "Ingrese tema de la práctica:", y); y += 40;
        txtVideo = agregarCampo(panelFormulario, "Video:", y); y += 40;

        panelFormulario.add(crearLabel("Número de preguntas: 4", 30, y));
        comboNumero = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboNumero.setBounds(270, y, 100, 25);
        panelFormulario.add(comboNumero);
        y += 40;

        txtPregunta = agregarCampo(panelFormulario, "Pregunta:", y); y += 40;

        for (int i = 0; i < 4; i++) {
            txtOpciones[i] = agregarCampo(panelFormulario, "Opción " + (i + 1) + ":", y);
            y += 40;
        }

        panelFormulario.add(crearLabel("Alternativa correcta:", 30, y));
        comboAlternativa = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboAlternativa.setBounds(270, y, 100, 25);
        panelFormulario.add(comboAlternativa);

        scrollPaneFormulario = new JScrollPane(panelFormulario);
        scrollPaneFormulario.setBounds(100, 200, 520, 350);
        scrollPaneFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneFormulario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDerecho.add(scrollPaneFormulario);

        panelVistaPrevia = new JPanel(new BorderLayout());
        panelVistaPrevia.setBounds(630, 200, 320, 430);
        panelVistaPrevia.setBackground(Color.LIGHT_GRAY);
        panelVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelDerecho.add(panelVistaPrevia);
    }

    private void configurarBotonesInferiores() {
        int y = 518;

        panelDerecho.add(crearBoton("Visualizar", 100, y, new Color(134, 199, 231), this::mostrarPrevisualizacion));
        panelDerecho.add(crearBoton("Guardar", 220, y, new Color(89, 196, 107), this::confirmarGuardado));
        panelDerecho.add(crearBoton("Eliminar", 340, y, new Color(234, 98, 85), this::eliminarFormulario));
    }

    private void confirmarGuardado() {
        String msg = (idFormularioActual == 0)
                ? "¿Estás seguro de subir esta nueva práctica?"
                : "¿Deseas actualizar esta práctica existente?";

        int confirmacion = JOptionPane.showConfirmDialog(this, msg, "Confirmación", JOptionPane.OK_CANCEL_OPTION);
        if (confirmacion == JOptionPane.OK_OPTION) {
            actualizarPreguntaDesdeCampos();
            guardarEnBaseDeDatos();
        }
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

        if (!preguntaTexto.isEmpty() || contieneTexto(opciones)) {
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

            String msg = exito
                    ? (idFormularioActual == 0
                    ? "Formulario y preguntas guardados correctamente"
                    : "Formulario y preguntas actualizados correctamente")
                    : "Error al guardar los datos";
            mostrarMensaje(msg);

        } catch (Exception ex) {
            mostrarMensaje("Error de conexión: " + ex.getMessage());
        }
    }

    private void eliminarFormulario() {
        if (idFormularioActual == 0) {
            mostrarMensaje("No se puede eliminar una práctica que aún no ha sido guardada.");
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
                    mostrarMensaje("Práctica eliminada correctamente.");
                    new ContenidoDocente(usuario, curso).setVisible(true);
                    dispose();
                } else {
                    mostrarMensaje("Error al eliminar la práctica.");
                }
            } catch (Exception ex) {
                mostrarMensaje("Error de conexión: " + ex.getMessage());
            }
        }
    }

    // --------------------- UTILITARIOS ---------------------

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

    private JTextField agregarCampo(JPanel panel, String texto, int y) {
        panel.add(crearLabel(texto, 30, y));
        JTextField field = crearTextField(270, y, 200);
        panel.add(field);
        return field;
    }

    private JButton crearBoton(String texto, int x, int y, Color colorFondo, Runnable evento) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 100, 35);
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.addActionListener(e -> evento.run());
        return boton;
    }

    private boolean contieneTexto(String[] textos) {
        for (String texto : textos) {
            if (!texto.isEmpty()) return true;
        }
        return false;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
