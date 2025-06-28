package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import Modelos.Curso;
import Conexion.ConexionBD;

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

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera1.addActionListener(e -> {
            new ContenidoEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera2.setEnabled(false); // ya estamos en esta vista
        panelDerecho.add(btnCabecera2);

        String[] columnas = {"Práctica", "Nota", "Fecha de Envío"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        tablaNotas = new JTable(modelo);
        tablaNotas.setRowHeight(25);
        tablaNotas.getTableHeader().setFont(new Font("Serif", Font.BOLD, 14));
        scrollPane = new JScrollPane(tablaNotas);
        scrollPane.setBounds(100, 200, 850, 400);
        panelDerecho.add(scrollPane);

        cargarNotasDesdeBD(modelo);
    }

    private void cargarNotasDesdeBD(DefaultTableModel modelo) {
    ConexionBD conexionBD = new ConexionBD();
    try (Connection con = conexionBD.obtenerConexion()) {
        String sql = "SELECT f.nombreFor, r.nota, r.fechaEnvio " +
                     "FROM ResultadoPractica r " +
                     "JOIN Formulario f ON f.idFor = r.idFormulario " +
                     "WHERE r.idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, usuario.getIdUsuario());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String practica = rs.getString("nombreFor");
            double nota = rs.getDouble("nota");
            Date fecha = rs.getDate("fechaEnvio"); // 🔥 solo la fecha
            modelo.addRow(new Object[]{practica, nota, fecha});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar notas: " + e.getMessage());
    }
    }
}