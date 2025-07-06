package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;

public class ContenidoDocente extends ComplementosFrameDocente {
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;
    private final Curso curso;

    public ContenidoDocente(Usuario usuario, Curso curso) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO DEL CURSO: " + curso.getNombre()));

        crearBotonesCabecera();
        crearBotonAgregarPractica();

        inicializarPanelScroll();
        cargarFormularios();

        setVisible(true);
    }

    private void crearBotonesCabecera() {
        agregarBotonCabecera("Contenido", 100, null); // ya estamos en Contenido

        agregarBotonCabecera("Calificaciones", 525, () -> {
            new CalifiDocente(usuario, curso).setVisible(true);
            dispose();
        });
    }

    private void agregarBotonCabecera(String texto, int x, Runnable accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        if (accion != null) boton.addActionListener(e -> accion.run());
        panelDerecho.add(boton);
    }

    private void crearBotonAgregarPractica() {
        JButton btnAgregar = new JButton("AGREGAR PRACTICA");
        btnAgregar.setBounds(735, 580, 220, 40);
        btnAgregar.setBackground(new Color(39, 87, 117));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> {
            new AgregarContenido(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnAgregar);
    }

    private void inicializarPanelScroll() {
        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);
    }

    private void cargarFormularios() {
        try {
            FormularioBD formularioBD = new FormularioBD(new ConexionBD().obtenerConexion());
            List<Formulario> formularios = formularioBD.obtenerFormulariosPorCurso(curso.getIdCurso());

            if (formularios.isEmpty()) {
                mostrarInfo("No hay prácticas para este curso. Agrega una nueva.");
                new AgregarContenido(usuario, curso).setVisible(true);
                dispose();
                return;
            }

            formularios.sort(Comparator.comparingInt(Formulario::getIdFor));

            for (Formulario f : formularios) {
                contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorScroll.add(crearPanelPractica(f));
            }

        } catch (Exception e) {
            mostrarError("Error cargando prácticas: " + e.getMessage());
        }

        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 380);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scrollPane);

        contenedorScroll.setPreferredSize(new Dimension(850, 600));
    }

    private JPanel crearPanelPractica(Formulario formulario) {
        JPanel panel = new JPanel(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panel.setPreferredSize(new Dimension(850, 110));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblNombre = new JLabel(formulario.getNombreFor());
        lblNombre.setBounds(50, 35, 400, 20);
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(lblNombre);

        JButton btnVer = new JButton("Ver práctica");
        btnVer.setBounds(650, 35, 120, 30);
        btnVer.setBackground(new Color(254, 234, 157));
        btnVer.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnVer.addActionListener(e -> {
            new AgregarContenido(usuario, curso, formulario.getIdFor()).setVisible(true);
            dispose();
        });
        panel.add(btnVer);

        return panel;
    }

    // ---------- UTILITARIOS ----------

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarInfo(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }
}