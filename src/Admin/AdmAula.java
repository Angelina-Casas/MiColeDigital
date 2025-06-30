package Admin;

import Complementos.BaseFrame;
import Modelos.Usuario;
import Modelos.Aula;
import Modelos.AulaBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdmAula extends BaseFrame {
    private JTextField txtGrado, txtSeccion;
    private JTable tablaAulas;
    private AulaBD aulaBD = new AulaBD();
    private Usuario usuario;

    public AdmAula(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre());
        lblBienvenida.setFont(new Font("Serif", Font.BOLD, 16));
        lblBienvenida.setBounds(20, 20, 400, 25);
        panelContenido.add(lblBienvenida);

        JLabel lblGrado = new JLabel("Grado:");
        lblGrado.setBounds(30, 110, 100, 30);
        lblGrado.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblGrado);

        txtGrado = new JTextField();
        txtGrado.setBounds(110, 110, 180, 28);
        panelContenido.add(txtGrado);

        JLabel lblSeccion = new JLabel("Sección:");
        lblSeccion.setBounds(30, 160, 100, 30);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblSeccion);

        txtSeccion = new JTextField();
        txtSeccion.setBounds(110, 160, 180, 28);
        panelContenido.add(txtSeccion);

        int btnWidth = 160;
        int btnHeight = 30;
        int xBtn = 80;
        int startY = 250;
        int gap = 50;

        JButton btnVerEstudiantes = new JButton("VER ESTUDIANTES");
        btnVerEstudiantes.setBounds(xBtn, startY, btnWidth, btnHeight);
        btnVerEstudiantes.setBackground(new Color(255, 249, 200));
        panelContenido.add(btnVerEstudiantes);
        btnVerEstudiantes.addActionListener(e -> {
            int fila = tablaAulas.getSelectedRow();
            if (fila != -1) {
                int idAula = (int) tablaAulas.getValueAt(fila, 0);
                Aula aulaSeleccionada = aulaBD.obtenerAula(idAula);
                if (aulaSeleccionada != null) {
                    new VerEstudiantes(aulaSeleccionada, usuario).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo encontrar el aula.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un aula primero.");
            }
        });

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(xBtn, startY + gap, btnWidth, btnHeight);
        btnAgregar.setBackground(new Color(144, 238, 144));
        btnAgregar.addActionListener(e -> agregarAula());
        panelContenido.add(btnAgregar);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setBounds(xBtn, startY + 2 * gap, btnWidth, btnHeight);
        btnEditar.setBackground(new Color(173, 216, 230));
        btnEditar.addActionListener(e -> editarAula());
        panelContenido.add(btnEditar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(xBtn, startY + 3 * gap, btnWidth, btnHeight);
        btnEliminar.setBackground(new Color(255, 102, 102));
        btnEliminar.addActionListener(e -> eliminarAula());
        panelContenido.add(btnEliminar);

        tablaAulas = new JTable(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Grado", "Sección"}
        ));
        tablaAulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollLista = new JScrollPane(tablaAulas);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 500, 120, 35);
        btnRegresar.setBackground(new Color(39,87,117)); 
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.addActionListener(e -> {
            new MenuAdm(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);

        tablaAulas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaAulas.getSelectedRow();
                if (fila != -1) {
                    txtGrado.setText(tablaAulas.getValueAt(fila, 1).toString());
                    txtSeccion.setText(tablaAulas.getValueAt(fila, 2).toString());
                }
            }
        });

        cargarAulas();
    }

    private void cargarAulas() {
        DefaultTableModel model = (DefaultTableModel) tablaAulas.getModel();
        model.setRowCount(0);
        List<Aula> lista = aulaBD.listarAulas();
        for (Aula a : lista) {
            model.addRow(new Object[]{
                a.getIdAula(),
                a.getGrado(),
                a.getSeccion()
            });
        }
    }

    private void agregarAula() {
        String gradoStr = txtGrado.getText();
        String seccion = txtSeccion.getText();
        if (verificarCampos(gradoStr, seccion)) {
            int grado = Integer.parseInt(gradoStr);
            Aula aula = new Aula(-1, grado, seccion);
            if (aulaBD.insertarAula(aula)) {
                JOptionPane.showMessageDialog(this, "Aula agregada exitosamente.");
                limpiarCampos();
                cargarAulas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar aula.");
            }
        }
    }

    private void editarAula() {
        int fila = tablaAulas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }
        String gradoStr = txtGrado.getText();
        String seccion = txtSeccion.getText();
        if (verificarCampos(gradoStr, seccion)) {
            int grado = Integer.parseInt(gradoStr);
            int id = (int) tablaAulas.getValueAt(fila, 0);
            Aula aula = new Aula(id, grado, seccion);
            if (aulaBD.actualizarAula(aula)) {
                JOptionPane.showMessageDialog(this, "Aula actualizada.");
                limpiarCampos();
                cargarAulas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar aula.");
            }
        }
    }

    private void eliminarAula() {
        int fila = tablaAulas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }
        int id = (int) tablaAulas.getValueAt(fila, 0);
        if (aulaBD.eliminarAula(id)) {
            JOptionPane.showMessageDialog(this, "Aula eliminada.");
            limpiarCampos();
            cargarAulas();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar aula.");
        }
    }

    private void limpiarCampos() {
        txtGrado.setText("");
        txtSeccion.setText("");
    }

    private boolean verificarCampos(String gradoStr, String seccion) {
        if (gradoStr.isEmpty() || seccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
            return false;
        }
        try {
            int grado = Integer.parseInt(gradoStr);
            if (grado > 6 || grado <= 0) {
                JOptionPane.showMessageDialog(this, "El grado debe estar entre 1 y 6.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El grado debe ser un número entero.");
            return false;
        }
        if (seccion.length() >= 3) {
            JOptionPane.showMessageDialog(this, "La sección debe tener máximo 2 caracteres.");
            return false;
        }
        return true;
    }
}


