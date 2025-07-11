package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Conexion.ConexionBD;
import Modelos.Usuario;
import Modelos.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class CalifiEstudiante extends ComplementosFrameEstudiante {
    private JTable tablaNotas;
    private JScrollPane scrollPane;
    private Curso curso;

    public CalifiEstudiante(Usuario usuario, Curso curso) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CALIFICACIONES - " + curso.getNombre().toUpperCase()));

        crearBotonesCabecera();
        inicializarTabla();
    }

    private void crearBotonesCabecera() {
        JButton btnContenido = crearBotonCabecera("Contenido", 100);
        btnContenido.addActionListener(e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnContenido);

        JButton btnCalificaciones = crearBotonCabecera("Calificaciones", 525);
        btnCalificaciones.setEnabled(false);  // Ya estamos en esta pantalla
        panelDerecho.add(btnCalificaciones);
    }

    private JButton crearBotonCabecera(String texto, int x) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        return boton;
    }

    private void inicializarTabla() {
        String[] columnas = {"Práctica", "Nota", "Fecha de Envío"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablaNotas = new JTable(modelo);
        tablaNotas.setRowHeight(25);
        tablaNotas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        scrollPane = new JScrollPane(tablaNotas);
        scrollPane.setBounds(100, 200, 850, 400);
        panelDerecho.add(scrollPane);

        cargarNotasDesdeBD(modelo);
    }

    private void cargarNotasDesdeBD(DefaultTableModel modelo) {
        String sql = """
            SELECT f.nombreFor AS practica, r.nota, r.fechaEnvio
            FROM ResultadoPractica r
            INNER JOIN Formulario f ON r.idFormulario = f.idFor
            WHERE r.idUsuario = ? AND f.idCurso = ?
            ORDER BY f.idFor
        """;

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, usuario.getIdUsuario());
            ps.setInt(2, curso.getIdCurso());
            ResultSet rs = ps.executeQuery();

            boolean hayDatos = false;
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("practica"),
                    rs.getDouble("nota"),
                    rs.getDate("fechaEnvio")
                };
                modelo.addRow(fila);
                hayDatos = true;
            }

            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No tienes calificaciones registradas para este curso todavía.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notas:\n" + e.getMessage());
        }
    }
}
