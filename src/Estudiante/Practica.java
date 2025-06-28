package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Conexion.ConexionBD;
import Modelos.Evaluador;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Practica extends ComplementosFrameEstudiante {
    private boolean enviada = false;
    private JRadioButton[] respuestasCorrectas;
    private ButtonGroup[] grupos;
    private JPanel panelScroll;
    private JScrollPane scrollPane;
    private int numeroPractica;

    public Practica(Usuario usuario, int numeroPractica) {
        super(usuario);
        this.usuario = usuario;
        this.numeroPractica = numeroPractica;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("     PRACTICAS"));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera1.addActionListener(e -> {
            new ContenidoEstudiante(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiEstudiante(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        crearPanelPreguntas();
    }

    private void crearPanelPreguntas() {
        panelScroll = new JPanel();
        panelScroll.setLayout(null);
        panelScroll.setPreferredSize(new Dimension(800, 1000));
        panelScroll.setBackground(Color.WHITE);

        try (Connection conn = new ConexionBD().obtenerConexion()) {

            // Verificar si ya envió esta práctica
            PreparedStatement psCheck = conn.prepareStatement(
                "SELECT nota FROM ResultadoPractica WHERE idUsuario = ? AND idFormulario = ?"
            );
            psCheck.setInt(1, usuario.getIdUsuario());
            psCheck.setInt(2, numeroPractica);
            ResultSet rsCheck = psCheck.executeQuery();

            if (rsCheck.next()) {
                int notaAnterior = rsCheck.getInt("nota");
                enviada = true;

                JLabel mensaje = new JLabel("Ya enviaste esta práctica. Nota anterior: " + notaAnterior);
                mensaje.setFont(new Font("Arial", Font.BOLD, 16));
                mensaje.setBounds(50, 50, 600, 30);
                panelScroll.add(mensaje);

            } else {
                // Cargar preguntas
                PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM PreguntaFormulario WHERE idFormulario = ? ORDER BY nroPregunta"
                );
                ps.setInt(1, numeroPractica);
                ResultSet rs = ps.executeQuery();

                List<ButtonGroup> grupoList = new ArrayList<>();
                List<JRadioButton> correctasList = new ArrayList<>();
                int yBase = 30;

                while (rs.next()) {
                    int nroPregunta = rs.getInt("nroPregunta");
                    String pregunta = rs.getString("pregunta");
                    String[] opciones = {
                        rs.getString("opcion1"),
                        rs.getString("opcion2"),
                        rs.getString("opcion3"),
                        rs.getString("opcion4")
                    };
                    String respuestaCorrecta = rs.getString("respuestaCorrecta"); // debe ser "1", "2", etc.

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

                        if (respuestaCorrecta != null && respuestaCorrecta.equals("" + (i + 1))) {
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

                // Validación si no hay preguntas
                if (grupos.length == 0 || respuestasCorrectas.length == 0) {
                    JOptionPane.showMessageDialog(this,
                            "Esta práctica aún no tiene preguntas registradas.",
                            "Sin preguntas", JOptionPane.WARNING_MESSAGE);
                    btnEnviar.setEnabled(false);
                    return;
                }

                // Acción al hacer clic en ENVIAR
                btnEnviar.addActionListener(e -> {
                    if (!Evaluador.todosLosGruposRespondidos(grupos)) {
                        JOptionPane.showMessageDialog(this, "Responde todas las preguntas antes de enviar.");
                        return;
                    }

                    int nota = Evaluador.calcularNota(grupos, respuestasCorrectas);

                    // NUEVA CONEXIÓN para guardar resultado
                    try (Connection nuevaConn = new ConexionBD().obtenerConexion()) {
                        PreparedStatement psInsert = nuevaConn.prepareStatement(
                            "INSERT INTO ResultadoPractica (idUsuario, idFormulario, nota) VALUES (?, ?, ?)"
                        );
                        psInsert.setInt(1, usuario.getIdUsuario());
                        psInsert.setInt(2, numeroPractica);
                        psInsert.setInt(3, nota);
                        psInsert.executeUpdate();

                        JOptionPane.showMessageDialog(this,
                            "\u00a1Tu nota fue subida con \u00e9xito!\nObtuviste " + nota + " puntos.",
                            "Resultado enviado", JOptionPane.INFORMATION_MESSAGE);
                        enviada = true;
                        btnEnviar.setEnabled(false);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error al guardar tu resultado:\n" + ex.getMessage());
                    }
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.");
        }

        scrollPane = new JScrollPane(panelScroll);
        scrollPane.setBounds(100, 200, 850, 460);
        panelDerecho.add(scrollPane);
    }
}
