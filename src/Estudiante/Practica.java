package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Conexion.ConexionBD;
import Modelos.Evaluador;
import Modelos.Usuario;
import Modelos.Curso;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Practica extends ComplementosFrameEstudiante {
    private final Usuario usuario;
    private final Curso curso;
    private final int numeroPractica;
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
        add(crearPanelDerecho("PRÁCTICAS - " + curso.getNombre().toUpperCase()));

        agregarBotonesCabecera();
        crearPanelPreguntas();

        setVisible(true);
    }

    private void agregarBotonesCabecera() {
        JButton btnContenido = crearBotonCabecera("Contenido", 100, e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });

        JButton btnCalificaciones = crearBotonCabecera("Calificaciones", 525, e -> {
            new CalifiEstudiante(usuario, curso).setVisible(true);
            dispose();
        });

        panelDerecho.add(btnContenido);
        panelDerecho.add(btnCalificaciones);
    }

    private JButton crearBotonCabecera(String texto, int x, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        boton.addActionListener(accion);
        return boton;
    }

    private void crearPanelPreguntas() {
        panelScroll = new JPanel(null);
        panelScroll.setPreferredSize(new Dimension(800, 1000));
        panelScroll.setBackground(Color.WHITE);

        try (Connection conn = new ConexionBD().obtenerConexion()) {
            if (practicaYaEnviada(conn)) {
                mostrarMensajeYaEnviada();
            } else {
                cargarPreguntasDesdeBD(conn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
        }

        scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBounds(100, 200, 850, 460);
        panelDerecho.add(scrollPane);
    }

    private boolean practicaYaEnviada(Connection conn) throws SQLException {
        String sql = "SELECT nota FROM ResultadoPractica WHERE idUsuario = ? AND idFormulario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdUsuario());
            ps.setInt(2, numeroPractica);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int notaAnterior = rs.getInt("nota");
                JLabel mensaje = new JLabel("Ya enviaste esta práctica. Nota anterior: " + notaAnterior);
                mensaje.setFont(new Font("Arial", Font.BOLD, 16));
                mensaje.setForeground(Color.RED);
                mensaje.setBounds(260, 200, 600, 30);
                panelScroll.add(mensaje);
                enviada = true;
                return true;
            }
        }
        return false;
    }

    private void mostrarMensajeYaEnviada() {
        // Ya se maneja dentro de practicaYaEnviada()
    }

    private void cargarPreguntasDesdeBD(Connection conn) throws SQLException {
        String sql = "SELECT * FROM PreguntaFormulario WHERE idFormulario = ? ORDER BY nroPregunta";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numeroPractica);
            ResultSet rs = ps.executeQuery();

            List<ButtonGroup> grupoList = new ArrayList<>();
            List<JRadioButton> correctasList = new ArrayList<>();
            int yBase = 30;

            while (rs.next()) {
                yBase = agregarPregunta(rs, grupoList, correctasList, yBase);
            }

            respuestasCorrectas = correctasList.toArray(new JRadioButton[0]);
            grupos = grupoList.toArray(new ButtonGroup[0]);

            agregarBotonEnviar(yBase, conn);
        }
    }

    private int agregarPregunta(ResultSet rs, List<ButtonGroup> grupoList, List<JRadioButton> correctasList, int yBase) throws SQLException {
        int nroPregunta = rs.getInt("nroPregunta");
        String pregunta = rs.getString("pregunta");
        String[] opciones = {
            rs.getString("opcion1"),
            rs.getString("opcion2"),
            rs.getString("opcion3"),
            rs.getString("opcion4")
        };
        String respuestaCorrecta = rs.getString("respuestaCorrecta");

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

            if (respuestaCorrecta != null && respuestaCorrecta.equals(String.valueOf(i + 1))) {
                correctasList.add(radio);
            }
        }

        return yBase + 150;
    }

    private void agregarBotonEnviar(int yBase, Connection conn) {
        JButton btnEnviar = new JButton("ENVIAR");
        btnEnviar.setBounds(300, yBase, 200, 30);
        btnEnviar.setBackground(new Color(178, 0, 38));
        btnEnviar.setForeground(Color.WHITE);
        panelScroll.add(btnEnviar);

        if (grupos.length == 0 || respuestasCorrectas.length == 0) {
            JOptionPane.showMessageDialog(this,
                "Esta práctica aún no tiene preguntas registradas.",
                "Sin preguntas", JOptionPane.WARNING_MESSAGE);
            btnEnviar.setEnabled(false);
            return;
        }

        btnEnviar.addActionListener(e -> enviarRespuestas(conn, btnEnviar));
    }

    private void enviarRespuestas(Connection conn, JButton btnEnviar) {
        if (!Evaluador.todosLosGruposRespondidos(grupos)) {
            JOptionPane.showMessageDialog(this, "Responde todas las preguntas antes de enviar.");
            return;
        }

        int nota = Evaluador.calcularNota(grupos, respuestasCorrectas);

        String sql = "INSERT INTO ResultadoPractica (idUsuario, idFormulario, nota) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getIdUsuario());
            ps.setInt(2, numeroPractica);
            ps.setInt(3, nota);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "¡Tu nota fue subida con éxito!\nObtuviste " + nota + " puntos.",
                "Resultado enviado", JOptionPane.INFORMATION_MESSAGE);

            enviada = true;
            btnEnviar.setEnabled(false);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar tu resultado:\n" + ex.getMessage());
        }
    }
}