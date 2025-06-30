package Docente;

import Complementos.ComplementosFrameDocente;
import Conexion.ConexionBD;
import Modelos.Formulario;
import Modelos.FormularioBD;
import Modelos.Usuario;
import Modelos.Curso;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Comparator;

public class ContenidoDocente extends ComplementosFrameDocente {
    private JScrollPane scrollPane;
    private JPanel contenedorScroll;

    public ContenidoDocente(Usuario usuario, Curso curso) {
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
            new CalifiDocente(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        JButton btnAgregarPractica = new JButton("AGREGAR PRACTICA");
        btnAgregarPractica.setBounds(735, 580, 220, 40);
        btnAgregarPractica.setBackground(new Color(39, 87, 117));
        btnAgregarPractica.setForeground(Color.WHITE);
        btnAgregarPractica.addActionListener(e -> {
            new AgregarContenido(usuario, curso).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnAgregarPractica);

        contenedorScroll = new JPanel();
        contenedorScroll.setLayout(new BoxLayout(contenedorScroll, BoxLayout.Y_AXIS));
        contenedorScroll.setBackground(Color.WHITE);

        try {
            ConexionBD conexionBD = new ConexionBD();
            FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());

            List<Formulario> listaFormularios = formularioBD.obtenerFormulariosPorCurso(curso.getIdCurso());

            if (listaFormularios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay prácticas para este curso. Agrega una nueva.");
                new AgregarContenido(usuario, curso).setVisible(true);
                dispose();
                return;
            }

            listaFormularios.sort(Comparator.comparingInt(Formulario::getIdFor));

            for (Formulario form : listaFormularios) {
                int idFormulario = form.getIdFor();
                String nombre = form.getNombreFor();

                JPanel panelPractica = new JPanel(null);
                panelPractica.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
                panelPractica.setPreferredSize(new Dimension(850, 110));
                panelPractica.setBackground(new Color(240, 240, 240));
                panelPractica.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                JLabel lblSemana = new JLabel(nombre);
                lblSemana.setBounds(50, 35, 400, 20);
                lblSemana.setFont(new Font("SansSerif", Font.BOLD, 20));
                panelPractica.add(lblSemana);

                JButton btnVer = new JButton("Ver práctica");
                btnVer.setBounds(650, 35, 120, 30);
                btnVer.setBackground(new Color(254, 234, 157));
                btnVer.setFont(new Font("SansSerif", Font.BOLD, 12));
                btnVer.addActionListener(e -> {
                    new AgregarContenido(usuario, curso, idFormulario).setVisible(true);
                    dispose();
                });

                panelPractica.add(btnVer);

                contenedorScroll.add(Box.createRigidArea(new Dimension(0, 10)));
                contenedorScroll.add(panelPractica);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando prácticas: " + e.getMessage());
        }

        scrollPane = new JScrollPane(contenedorScroll);
        scrollPane.setBounds(100, 190, 850, 380);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        panelDerecho.add(scrollPane);

        contenedorScroll.setPreferredSize(new Dimension(850, 600));
        setVisible(true);
    }
}
