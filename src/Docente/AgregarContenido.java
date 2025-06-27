/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Docente;

import javax.swing.*;
import java.awt.*;
import Complementos.ComplementosFrameDocente;
import Modelos.Usuario;
import Modelos.Formulario;
import Modelos.FormularioBD;
import Conexion.ConexionBD;

public class AgregarContenido extends ComplementosFrameDocente {
    private JScrollPane scrollPaneFormulario;
    private JPanel panelFormulario;
    private JPanel panelVistaPrevia;
    private JTextField txtNombre, txtTema, txtVideo, txtPregunta;
    private JTextField[] txtOpciones = new JTextField[4];
    private JComboBox<String> comboNumero, comboAlternativa;

    public AgregarContenido(Usuario usuario) {
        super(usuario);
        this.usuario = usuario;

        add(crearPanelIzquierdo());
        add(crearPanelDerecho("CONTENIDO - MICOLEDIGITAL"));

        JButton btnCabecera1 = new JButton("Contenido");
        btnCabecera1.setBounds(100, 140, 425, 40);
        btnCabecera1.setBackground(Color.WHITE);
        btnCabecera1.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        panelDerecho.add(btnCabecera1);

        JButton btnCabecera2 = new JButton("Calificaciones");
        btnCabecera2.setBounds(525, 140, 425, 40);
        btnCabecera2.setBackground(Color.WHITE);
        btnCabecera2.setBorder(BorderFactory.createLineBorder(new Color(178, 0, 38), 2));
        btnCabecera2.addActionListener(e -> {
            new CalifiDocente(usuario).setVisible(true);
            dispose();
        });
        panelDerecho.add(btnCabecera2);

        panelFormulario = new JPanel();
        panelFormulario.setLayout(null);
        panelFormulario.setPreferredSize(new Dimension(500, 600));
        panelFormulario.setBackground(Color.WHITE);
        int y = 20;

        panelFormulario.add(crearLabel("Ingrese nombre de la Práctica:", 30, y));
        txtNombre = crearTextField(270, y, 200);
        panelFormulario.add(txtNombre);
        y += 40;

        panelFormulario.add(crearLabel("Ingrese tema de la práctica:", 30, y));
        txtTema = crearTextField(270, y, 200);
        panelFormulario.add(txtTema);
        y += 40;

        panelFormulario.add(crearLabel("Video:", 30, y));
        txtVideo = crearTextField(270, y, 200);
        panelFormulario.add(txtVideo);
        y += 40;

        panelFormulario.add(crearLabel("Número de preguntas: 4", 30, y));
        comboNumero = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboNumero.setBounds(270, y, 100, 25);
        panelFormulario.add(comboNumero);
        y += 40;

        panelFormulario.add(crearLabel("Pregunta:", 30, y));
        txtPregunta = crearTextField(270, y, 200);
        panelFormulario.add(txtPregunta);
        y += 40;

        for (int i = 0; i < 4; i++) {
            panelFormulario.add(crearLabel("Opción " + (i + 1) + ":", 30, y));
            txtOpciones[i] = crearTextField(270, y, 200);
            panelFormulario.add(txtOpciones[i]);
            y += 40;
        }

        panelFormulario.add(crearLabel("Alternativa correcta:", 30, y));
        comboAlternativa = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        comboAlternativa.setBounds(270, y, 100, 25);
        panelFormulario.add(comboAlternativa);
        y += 50;
        
        JButton btnVer= new JButton("Visualizar");
        btnVer.setBounds(270, y, 150, 35);
        btnVer.setBackground(new Color(178, 0, 38));
        btnVer.setForeground(Color.WHITE);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(270, y, 150, 35);
        btnGuardar.setBackground(new Color(178, 0, 38));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.addActionListener(e -> {
            Formulario formulario = new Formulario(
                txtNombre.getText(),
                txtTema.getText(),
                txtVideo.getText(),
                Integer.parseInt((String) comboNumero.getSelectedItem()),
                txtPregunta.getText(),
                txtOpciones[0].getText(),
                txtOpciones[1].getText(),
                txtOpciones[2].getText(),
                txtOpciones[3].getText(),
                (String) comboAlternativa.getSelectedItem()
            );

            try {
                ConexionBD conexionBD = new ConexionBD();
                FormularioBD formularioBD = new FormularioBD(conexionBD.obtenerConexion());
                boolean exito = formularioBD.insertarFormulario(formulario);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Formulario guardado correctamente");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar el formulario");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage());
            }
        });
        panelFormulario.add(btnGuardar);

        scrollPaneFormulario = new JScrollPane(panelFormulario);
        scrollPaneFormulario.setBounds(100, 200, 520, 430);
        scrollPaneFormulario.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneFormulario.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        panelDerecho.add(scrollPaneFormulario);

        panelVistaPrevia = new JPanel();
        panelVistaPrevia.setBounds(630, 200, 320, 430);
        panelVistaPrevia.setBackground(Color.LIGHT_GRAY);
        panelVistaPrevia.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelDerecho.add(panelVistaPrevia);

        setVisible(true);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 250, 25);
        return label;
    }

    private JTextField crearTextField(int x, int y, int ancho) {
        JTextField field = new JTextField();
        field.setBounds(x, y, ancho, 25);
        return field;
    }
}
