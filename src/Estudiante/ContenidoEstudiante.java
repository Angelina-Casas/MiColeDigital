package Estudiante;

import Complementos.ComplementosFrameEstudiante;
import Conexion.ConexionBD;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.Formulario;
import Modelos.FormularioBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class ContenidoEstudiante extends ComplementosFrameEstudiante {
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;
    private Curso curso;

    public ContenidoEstudiante(Usuario usuario, Curso curso) {
        super(usuario);
        this.usuario = usuario;
        this.curso = curso;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO DEL CURSO: " + curso.getNombre()));

        crearBotonesCabecera();
        inicializarPanelScroll();
        cargarContenidoDelCurso();

        setVisible(true);
    }

    private void crearBotonesCabecera() {
        JButton btnContenido = crearBotonCabecera("Contenido", 100);
        panelDerecho.add(btnContenido);

        JButton btnCalificaciones = crearBotonCabecera("Calificaciones", 525);
        btnCalificaciones.addActionListener(e -> {
            new CalifiEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCalificaciones);
    }

    private JButton crearBotonCabecera(String texto, int x) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, 140, 425, 40);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        return boton;
    }

    private void inicializarPanelScroll() {
        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 420);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        contenedorScroll.setPreferredSize(new Dimension(850, 600));
        panelDerecho.add(scrollPane);
    }

    private void cargarContenidoDelCurso() {
        try (Connection conexion = new ConexionBD().obtenerConexion()) {
            FormularioBD formularioBD = new FormularioBD(conexion);
            List<Formulario> formularios = formularioBD.obtenerFormulariosPorCurso(curso.getIdCurso());

            int contador = 1;
            for (Formulario f : formularios) {
                contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorScroll.add(crearPanelPractica(f, contador++));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar contenido: " + e.getMessage());
        }
    }

    private JPanel crearPanelPractica(Formulario f, int numeroPractica) {
        JPanel panel = new JPanel(null);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panel.setPreferredSize(new Dimension(850, 110));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel lblSemana = new JLabel("Práctica " + numeroPractica);
        lblSemana.setBounds(20, 10, 200, 20);
        lblSemana.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(lblSemana);

        JLabel lblTema = new JLabel("Tema: " + f.getTema());
        lblTema.setBounds(20, 35, 400, 20);
        panel.add(lblTema);

        JLabel lblURL = new JLabel(f.getVideoUrl());
        lblURL.setBounds(20, 60, 500, 20);
        lblURL.setForeground(Color.BLUE);
        lblURL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblURL.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirEnlace(f.getVideoUrl());
            }
        });
        panel.add(lblURL);

        JButton btnVer = new JButton("Ver práctica");
        btnVer.setBounds(650, 35, 120, 30);
        btnVer.setBackground(new Color(39, 87, 117));
        btnVer.setForeground(Color.WHITE);
        btnVer.addActionListener(e -> {
            new Practica(usuario, curso, f.getIdFor()).setVisible(true);
            dispose();
        });
        panel.add(btnVer);

        return panel;
    }

    private void abrirEnlace(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new java.net.URI(url));
            } else {
                JOptionPane.showMessageDialog(this, "Tu sistema no permite abrir enlaces.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al abrir el enlace: " + e.getMessage());
        }
    }
}
