/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;
import Complementos.BaseFrame;
import javax.swing.*;
import java.awt.*;

public class AdmCurso extends BaseFrame {

    @Override
    protected void initContenido() {
        
        

        // Etiqueta Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(40, 40, 100, 25);
        panelContenido.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(40, 65, 300, 30);
        panelContenido.add(txtNombre);

        // Docente
        JLabel lblDocente = new JLabel("Docente:");
        lblDocente.setFont(new Font("Arial", Font.BOLD, 14));
        lblDocente.setBounds(40, 110, 100, 25);
        panelContenido.add(lblDocente);

        JComboBox<String> cbDocente = new JComboBox<>();
        cbDocente.setBounds(40, 135, 300, 30);
        panelContenido.add(cbDocente);

        // Aula
        JLabel lblAula = new JLabel("Aula:");
        lblAula.setFont(new Font("Arial", Font.BOLD, 14));
        lblAula.setBounds(40, 180, 100, 25);
        panelContenido.add(lblAula);

        JComboBox<String> cbAula = new JComboBox<>();
        cbAula.setBounds(40, 205, 300, 30);
        panelContenido.add(cbAula);

        // Botones: Agregar, Editar, Eliminar
        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(40, 260, 140, 35);
        btnAgregar.setBackground(new Color(144, 238, 144));
        panelContenido.add(btnAgregar);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setBounds(40, 305, 140, 35);
        btnEditar.setBackground(new Color(255, 102, 102));
        panelContenido.add(btnEditar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(40, 350, 140, 35);
        btnEliminar.setBackground(new Color(173, 216, 230));
        panelContenido.add(btnEliminar);
        // Botón Regresar
        
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(970, 510, 150, 40);
        btnRegresar.setBackground(new Color(255, 249, 200));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 13));
        
        btnRegresar.addActionListener(e -> {
            new MenuAdm().setVisible(true);
            dispose();
        });
        
        panelContenido.add(btnRegresar);

        DefaultListModel<String> modeloLista = new DefaultListModel<>();
        JList<String> listaCursos = new JList<>(modeloLista);
        JScrollPane scrollLista = new JScrollPane(listaCursos);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        
        
    }

    
}
