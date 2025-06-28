package Estudiante;

import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameEstudiante;
import Modelos.Usuario;
import Modelos.Curso;

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
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40); 
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(39,87,117), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiEstudiante(usuario,curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        // 👇 Aquí puedes cambiar según el curso recibido
        String[] semanas;
        String[] temas;
        String[] urls;

        switch (curso.getNombre().toLowerCase()) {
            case "matemática" -> {
                semanas = new String[]{"Semana 1", "Semana 2", "Semana 3"};
                temas = new String[]{"Suma y resta", "Multiplicación", "División"};
                urls = new String[]{
                        "https://www.youtube.com/watch?v=RZzyWljhMEw",
                        "https://www.youtube.com/watch?v=-acFUpFSgo4",
                        "https://www.youtube.com/watch?v=YW_04Esg4QQ"
                };
            }
            case "comunicación" -> {
                semanas = new String[]{"Semana 1", "Semana 2"};
                temas = new String[]{"Comprensión lectora", "Tipos de textos"};
                urls = new String[]{
                        "https://www.youtube.com/watch?v=-SCa4B7VoAs",
                        "https://www.youtube.com/watch?v=Xv9aPqFdVPg"
                };
            }
            default -> {
                semanas = new String[]{"Semana 1"};
                temas = new String[]{"Contenido no disponible"};
                urls = new String[]{"https://www.youtube.com"};
            }
        }

        for (int i = 0; i < semanas.length; i++) {
            JPanel panelPractica = new JPanel(null);
            panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
            panelPractica.setPreferredSize(new Dimension(850, 110));
            panelPractica.setBackground(new Color(240, 240, 240));
            panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel lblSemana = new JLabel(semanas[i]);
            lblSemana.setBounds(20, 10, 200, 20);
            lblSemana.setFont(new Font("SansSerif", Font.BOLD, 14));
            panelPractica.add(lblSemana);

            JLabel lblTema = new JLabel("Tema: " + temas[i]);
            lblTema.setBounds(20, 35, 400, 20);
            panelPractica.add(lblTema);

            int index = i;
            JLabel lblURL = new JLabel(urls[i]);
            lblURL.setBounds(20, 60, 500, 20);
            lblURL.setForeground(Color.BLUE);
            lblURL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            lblURL.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    abrirEnlace(urls[index]);
                }
            });
            panelPractica.add(lblURL);

            JButton btnVer = new JButton("Ver práctica");
            btnVer.setBounds(650, 35, 120, 30);
            btnVer.setBackground(new Color(39, 87, 117));
            btnVer.setForeground(Color.WHITE);
            btnVer.addActionListener(e -> {
                new Practica(usuario, curso, index + 1).setVisible(true);
                dispose();
            });

            panelPractica.add(btnVer);
            contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
            contenedorScroll.add(panelPractica);
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