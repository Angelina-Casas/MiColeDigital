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

    public CalifiEstudiante(Usuario usuario, Curso curso){
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CALIFICACIONES - " + curso.getNombre().toUpperCase()));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40); 
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera1.addActionListener(e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.setEnabled(false); // ya estás en esta vista
        panelDerecho.add(btnCabecera2);

        // Crear tabla
        String[] columnas = {"Práctica", "Nota", "Fecha de Envío"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablaNotas = new JTable(modelo);
        tablaNotas.setRowHeight(25);
        tablaNotas.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        scrollPane = new JScrollPane(tablaNotas);
        scrollPane.setBounds(100, 200, 850, 400);
        panelDerecho.add(scrollPane);

        // Cargar notas sin filtrar por curso (como tú pediste)
        cargarNotas(modelo);
    }

    private void cargarNotas(DefaultTableModel modelo) {
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
            ps.setInt(2, curso.getIdCurso()); // Filtramos por curso
            ResultSet rs = ps.executeQuery();

            boolean hayDatos = false;
            while (rs.next()) {
                String practica = rs.getString("practica");
                double nota = rs.getDouble("nota");
                Date fecha = rs.getDate("fechaEnvio");
                modelo.addRow(new Object[]{practica, nota, fecha});
                hayDatos = true;
            }

            if (!hayDatos) {
                JOptionPane.showMessageDialog(this, "No tienes calificaciones registradas para este curso todavía.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notas:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}