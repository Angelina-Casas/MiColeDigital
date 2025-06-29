package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Formulario;
import Modelos.FormularioBD;
import Modelos.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class CalifiDocente extends ComplementosFrameDocente {
    private JTable tabla;
    private JScrollPane scroll;
    private DefaultTableModel modelo;

    public CalifiDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("Calificaciones por Práctica"));

        // Obtener formularios del docente
        try {
            FormularioBD formularioBD = new FormularioBD(new ConexionBD().obtenerConexion());
            List<Formulario> formularios = formularioBD.obtenerFormulariosPorDocente(usuario.getIdUsuario());

            int x = 100;
            for (Formulario f : formularios) {
                int idFormulario = f.getIdFor();
                String nombre = f.getNombreFor();

                JButton btn = new JButton(nombre);
                btn.setBounds(x, 140, 200, 40);
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
                btn.addActionListener(e -> cargarNotasPorPractica(idFormulario, usuario.getIdUsuario()));
                panelDerecho.add(btn);
                x += 210;

                // Si los botones se salen del panel, puedes ajustar esto con scroll o cambio de diseño
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener formularios del docente:\n" + e.getMessage());
        }

        // Crear tabla
        String[] columnas = {"Nombre del Estudiante", "Nota"};
        modelo = new DefaultTableModel(null, columnas);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn colNombre = tabla.getColumnModel().getColumn(0);
        colNombre.setPreferredWidth(600);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);

        scroll = new JScrollPane(tabla);
        scroll.setBounds(100, 200, 850, 420);
        panelDerecho.add(scroll);
    }

    private void cargarNotasPorPractica(int idFormulario, int idDocente) {
        modelo.setRowCount(0); // Limpiar tabla

        String sql = """
            SELECT DISTINCT u.nombre AS nombreEstudiante, r.nota
            FROM ResultadoPractica r
            JOIN Usuario u ON u.idUsuario = r.idUsuario
            JOIN Formulario f ON f.idFor = r.idFormulario
            JOIN Curso c ON f.idCurso = c.idCurso
            WHERE f.idFor = ? AND c.idDocente = ?
            ORDER BY u.nombre;
        """;

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFormulario);
            ps.setInt(2, idDocente);
            ResultSet rs = ps.executeQuery();

            boolean tieneDatos = false;

            while (rs.next()) {
                Vector<Object> fila = new Vector<>();
                fila.add(rs.getString("nombreEstudiante"));
                fila.add(rs.getDouble("nota"));
                modelo.addRow(fila);
                tieneDatos = true;
            }

            if (!tieneDatos) {
                JOptionPane.showMessageDialog(this, "No se encontraron calificaciones para esta práctica.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notas:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}
