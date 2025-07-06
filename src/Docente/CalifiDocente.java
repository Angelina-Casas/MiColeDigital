package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

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

        crearBotonesCabecera();
        mostrarBotonesDePracticas();

        setVisible(true);
    }

    private void crearBotonesCabecera() {
        agregarBotonCabecera("Contenido", 100, () -> {
            new ContenidoDocente(usuario, curso).setVisible(true);
            dispose();
        });

        agregarBotonCabecera("Calificaciones", 525, this::mostrarBotonesDePracticas);
    }

    private void agregarBotonCabecera(String texto, int x, Runnable evento) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        boton.addActionListener(e -> evento.run());
        panelDerecho.add(boton);
    }

    private void mostrarBotonesDePracticas() {
        limpiarPanelContenido();

        panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);

        try {
            FormularioBD formularioBD = new FormularioBD(new ConexionBD().obtenerConexion());
            List<Formulario> formularios = formularioBD.obtenerFormulariosPorCurso(curso.getIdCurso());

            for (Formulario f : formularios) {
                panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
                panelContenido.add(crearPanelPractica(f));
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener formularios:\n" + e.getMessage());
        }

        scroll = new JScrollPane(panelContenido);
        scroll.setBounds(100, 200, 850, 370);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scroll);

        panelDerecho.repaint();
        panelDerecho.revalidate();
    }

    private JPanel crearPanelPractica(Formulario f) {
        JPanel panel = new JPanel(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panel.setPreferredSize(new Dimension(850, 110));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblNombre = new JLabel(f.getNombreFor());
        lblNombre.setBounds(50, 35, 400, 20);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(lblNombre);

        JButton btnVer = new JButton("Ver notas");
        btnVer.setBounds(650, 35, 120, 30);
        btnVer.setBackground(new Color(39, 87, 117));
        btnVer.setForeground(Color.WHITE);
        btnVer.addActionListener(e -> mostrarTablaNotas(f.getIdFor(), f.getNombreFor()));
        panel.add(btnVer);

        return panel;
    }

    private void mostrarTablaNotas(int idFormulario, String nombrePractica) {
        limpiarPanelContenido();

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBounds(100, 200, 850, 400);
        panelContenido.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("Notas de: " + nombrePractica);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelContenido.add(lblTitulo, BorderLayout.NORTH);

        modelo = new DefaultTableModel(new String[]{"Nombre del Estudiante", "Nota"}, 0);
        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(600);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(235);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollTabla.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelContenido.add(scrollTabla, BorderLayout.CENTER);

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
                mostrarInfo("No hay calificaciones para esta práctica.");
            }

        } catch (SQLException e) {
            mostrarError("Error al cargar notas:\n" + e.getMessage());
        }
    }

    private void limpiarPanelContenido() {
        if (scroll != null) panelDerecho.remove(scroll);
        if (panelContenido != null) panelDerecho.remove(panelContenido);
    }

    // --------------------- UTILITARIOS ---------------------

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}