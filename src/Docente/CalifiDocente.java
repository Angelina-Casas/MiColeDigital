package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Formulario;
import Modelos.FormularioBD;
import Modelos.Usuario;
import Modelos.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;
import javax.swing.table.TableColumn;

public class CalifiDocente extends ComplementosFrameDocente {
    private JTable tabla;
    private JScrollPane scroll;
    private DefaultTableModel modelo;
    private Curso curso;
    private JPanel panelContenido;

    public CalifiDocente(Usuario usuario, Curso curso) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("Calificaciones del curso: " + curso.getNombre()));

        // Botón Contenido
        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera1.addActionListener(e -> {
            new ContenidoDocente(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera1);

        // Botón Calificaciones (refresca vista)
        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera2.addActionListener(e -> mostrarBotonesDePracticas());
        panelDerecho.add(btnCabecera2);

        mostrarBotonesDePracticas();
        setVisible(true);
    }

    private void mostrarBotonesDePracticas() {
        if (scroll != null) panelDerecho.remove(scroll);
        if (panelContenido != null) panelDerecho.remove(panelContenido);

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);

        try {
            FormularioBD formularioBD = new FormularioBD(new ConexionBD().obtenerConexion());
            List<Formulario> formularios = formularioBD.obtenerFormulariosPorCurso(curso.getIdCurso());

            for (Formulario f : formularios) {
                int idFormulario = f.getIdFor();
                String nombre = f.getNombreFor();

                JPanel panelPractica = new JPanel(null);
                panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
                panelPractica.setPreferredSize(new Dimension(850, 110));
                panelPractica.setBackground(new Color(240, 240, 240));
                panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                JLabel lblNombre = new JLabel(nombre);
                lblNombre.setBounds(50, 35, 400, 20);
                lblNombre.setFont(new Font("SansSerif", Font.BOLD, 20));
                panelPractica.add(lblNombre);

                JButton btnVer = new JButton("Ver notas");
                btnVer.setBounds(650, 35, 120, 30);
                btnVer.setBackground(new Color(39, 87, 117));
                btnVer.setForeground(Color.WHITE);
                btnVer.addActionListener(e -> mostrarTablaNotas(idFormulario, nombre));
                panelPractica.add(btnVer);

                panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
                panelContenido.add(panelPractica);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener formularios:\n" + e.getMessage());
        }

        scroll = new JScrollPane(panelContenido);
        scroll.setBounds(100, 200, 850, 370);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDerecho.add(scroll);
        panelDerecho.repaint();
        panelDerecho.revalidate();
    }

    private void mostrarTablaNotas(int idFormulario, String nombrePractica) {
        if (scroll != null) panelDerecho.remove(scroll);
        if (panelContenido != null) panelDerecho.remove(panelContenido);

        panelContenido = new JPanel(null);
        panelContenido.setBounds(100, 200, 850, 400);
        panelContenido.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Notas de: " + nombrePractica);
        lblTitulo.setBounds(0, 0, 500, 30);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        panelContenido.add(lblTitulo);
        
        String[] columnas = {"Nombre del Estudiante", "Nota"};
        modelo = new DefaultTableModel(null, columnas);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumn colNombre = tabla.getColumnModel().getColumn(0);
        colNombre.setPreferredWidth(600);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBounds(0, 40, 800, 300);
        panelContenido.add(scrollTabla);

        panelDerecho.add(panelContenido);
        panelDerecho.repaint();
        panelDerecho.revalidate();

        cargarNotasPorPractica(idFormulario);
    }

    private void cargarNotasPorPractica(int idFormulario) {
        modelo.setRowCount(0);

        String sql = """
            SELECT u.nombre AS nombreEstudiante, r.nota
            FROM ResultadoPractica r
            JOIN Usuario u ON u.idUsuario = r.idUsuario
            WHERE r.idFormulario = ?
            ORDER BY u.nombre
        """;

        try (Connection con = new ConexionBD().obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFormulario);
            ResultSet rs = ps.executeQuery();

            boolean tieneDatos = false;
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("nombreEstudiante"),
                    rs.getDouble("nota")
                });
                tieneDatos = true;
            }

            if (!tieneDatos) {
                JOptionPane.showMessageDialog(this, "No hay calificaciones para esta práctica.");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar notas:\n" + e.getMessage());
        }
    }
}
