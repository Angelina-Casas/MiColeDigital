package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class CalifiDocente extends ComplementosFrameDocente {
    private JButton btnContenido, btnCalificaciones;
    private JTable tabla;
    private JScrollPane scroll;
    private DefaultTableModel modelo;

    public CalifiDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("Calificaciones"));

        // Botón Contenido
        btnContenido = new JButton("Contenido");
        btnContenido.setBounds(100, 140, 425, 40);
        btnContenido.setBackground(Color.WHITE);
        btnContenido.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnContenido.addActionListener(e -> {
            new AgregarContenido(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnContenido);

        // Botón Calificaciones
        btnCalificaciones = new JButton("Calificaciones");
        btnCalificaciones.setBounds(525, 140, 425, 40);
        btnCalificaciones.setBackground(Color.WHITE);
        btnCalificaciones.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCalificaciones.addActionListener(e -> {
            new CalifiDocente(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCalificaciones);

        // Crear tabla
        String[] columnas = {"Nombre y Apellidos", "S1", "S2", "S3", "S4", "S5", "S6"};
        modelo = new DefaultTableModel(null, columnas);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn colNombre = tabla.getColumnModel().getColumn(0);
        colNombre.setPreferredWidth(500);
        for (int i = 1; i <= 6; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(60);
        }

        scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 200, 850, 420);
        panelDerecho.add(scroll);

        // Cargar calificaciones desde vista
        cargarCalificacionesDesdeVista(usuario.getIdUsuario());
    }

    private void cargarCalificacionesDesdeVista(int idDocente) {
        modelo.setRowCount(0); // Limpiar tabla

        String sql = """
            SELECT nombreEstudiante, S1, S2, S3, S4, S5, S6
            FROM VistaNotasPorDocente
            WHERE idDocente = ?
            ORDER BY nombreEstudiante;
        """;
        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idDocente);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getString("nombreEstudiante"));
                for (int i = 1; i <= 6; i++) {
                    double nota = rs.getDouble("S" + i);
                    fila.add(rs.wasNull() ? "" : nota);
                }
                modelo.addRow(fila);
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar calificaciones:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}