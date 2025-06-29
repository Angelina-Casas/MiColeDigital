package Estudiante;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

import Complementos.ComplementosFrameEstudiante;
import Conexion.ConexionBD;
import Modelos.Usuario;
import Modelos.Curso;
import Modelos.Formulario;
import Modelos.FormularioBD;

public class ContenidoEstudiante extends ComplementosFrameEstudiante {
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;

    public ContenidoEstudiante(Usuario usuario, Curso curso) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO DEL CURSO: " + curso.getNombre()));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39, 87, 117), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiEstudiante(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        try {
            ConexionBD conexionBD = new ConexionBD();
            Connection conexion = conexionBD.obtenerConexion();
            FormularioBD formularioBD = new FormularioBD(conexion);

            List<Formulario> formularios = formularioBD.obtenerTodosFormularios();

            for (int i = 0; i < formularios.size(); i++) {
                Formulario f = formularios.get(i);

                JPanel panelPractica = new JPanel(null);
                panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
                panelPractica.setPreferredSize(new Dimension(850, 110));
                panelPractica.setBackground(new Color(240, 240, 240));
                panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                JLabel lblSemana = new JLabel("Semana " + (i + 1));
                lblSemana.setBounds(20, 10, 200, 20);
                lblSemana.setFont(new Font("SansSerif", Font.BOLD, 14));
                panelPractica.add(lblSemana);

                JLabel lblTema = new JLabel("Tema: " + f.getTema());
                lblTema.setBounds(20, 35, 400, 20);
                panelPractica.add(lblTema);

                JLabel lblURL = new JLabel(f.getVideoUrl());
                lblURL.setBounds(20, 60, 500, 20);
                lblURL.setForeground(Color.BLUE);
                lblURL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                final String url = f.getVideoUrl();
                lblURL.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        abrirEnlace(url);
                    }
                });
                panelPractica.add(lblURL);

                JButton btnVer = new JButton("Ver práctica");
                btnVer.setBounds(650, 35, 120, 30);
                btnVer.setBackground(new Color(39, 87, 117));
                btnVer.setForeground(Color.WHITE);
                int nroPractica = f.getIdFor(); // usa el ID de la base
                btnVer.addActionListener(e -> {
                    new Practica(usuario, curso, nroPractica).setVisible(true);
                    dispose();
                });

                panelPractica.add(btnVer);
                contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorScroll.add(panelPractica);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar contenido: " + e.getMessage());
        }

        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 420);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scrollPane);

        contenedorScroll.setPreferredSize(new Dimension(850, 600));

        setVisible(true);
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
