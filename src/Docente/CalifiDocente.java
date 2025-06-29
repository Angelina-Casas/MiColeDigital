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
    private JTable tabla;
    private JScrollPane scroll;
    private DefaultTableModel modelo;

    public CalifiDocente(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("Calificaciones por Práctica"));

        // Botones por práctica
        int x = 100;
        for (int i = 1; i <= 4; i++) {
            int idFormulario = i;
            JButton btn = new JButton("Práctica " + i);
            btn.setBounds(x, 140, 200, 40);
            btn.setBackground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
            btn.addActionListener(e -> cargarNotasPorPractica(idFormulario, usuario.getIdUsuario()));
            panelDerecho.add(btn);
            x += 210;
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

        System.out.println("📌 Consultando:");
        System.out.println("🔹 idFormulario = " + idFormulario);
        System.out.println("🔹 idDocente = " + idDocente);

        String sql = """
            SELECT DISTINCT u.nombre AS nombreEstudiante, r.nota
            FROM ResultadoPractica r
            JOIN Usuario u ON u.idUsuario = r.idUsuario
            JOIN AulaUsuario au ON au.idUsuario = u.idUsuario
            JOIN Aula a ON a.idAula = au.idAula
            JOIN Curso c ON c.idAula = a.idAula AND c.idDocente = ?
            JOIN Formulario f ON f.idFor = r.idFormulario
            WHERE r.idFormulario = ?
            ORDER BY u.nombre;
        """;

        try {
            ConexionBD conexion = new ConexionBD();
            Connection con = conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idDocente);
            ps.setInt(2, idFormulario);
            System.out.println("🔎 Ejecutando SQL...");
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
                System.out.println("❗ Consulta no devolvió resultados.");
                JOptionPane.showMessageDialog(this, "No se encontraron calificaciones para esta práctica.");
            } else {
                System.out.println("✅ Datos cargados correctamente.");
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "❌ Error al cargar notas:\n" + e.getMessage());
            e.printStackTrace();
        }
    }
}