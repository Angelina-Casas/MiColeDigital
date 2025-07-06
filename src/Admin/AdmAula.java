package Admin;

import Complementos.BaseFrame;
import Modelos.Usuario;
import Modelos.Aula;
import Modelos.AulaBD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdmAula extends BaseFrame {
    private JTextField txtGrado, txtSeccion;
    private JTable tablaAulas;
    private final AulaBD aulaBD = new AulaBD();
    private final Usuario usuario;

    public AdmAula(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        agregarLabelBienvenida();
        agregarCamposEntrada();
        agregarBotones();
        agregarTablaAulas();
        agregarBotonRegresar();
        cargarAulas();
    }

    // Sección: UI Components
    private void agregarLabelBienvenida() {
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre());
        lblBienvenida.setFont(new Font("Serif", Font.BOLD, 16));
        lblBienvenida.setBounds(20, 20, 400, 25);
        panelContenido.add(lblBienvenida);
    }

    private void agregarCamposEntrada() {
        JLabel lblGrado = new JLabel("Grado:");
        lblGrado.setBounds(90, 80, 100, 30);
        lblGrado.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblGrado);

        txtGrado = new JTextField();
        txtGrado.setBounds(90, 110, 180, 28);
        panelContenido.add(txtGrado);

        JLabel lblSeccion = new JLabel("Sección:");
        lblSeccion.setBounds(90, 145, 100, 30);
        lblSeccion.setFont(new Font("Arial", Font.BOLD, 14));
        panelContenido.add(lblSeccion);

        txtSeccion = new JTextField();
        txtSeccion.setBounds(90, 175, 180, 28);
        panelContenido.add(txtSeccion);
    }

    private void agregarBotones() {
        int x = 100, y = 275, w = 160, h = 30, gap = 50;

        agregarBoton("VER ESTUDIANTES", x, y, w, h, new Color(255, 249, 200), e -> verEstudiantes());
        agregarBoton("AGREGAR", x, y + gap, w, h, new Color(144, 238, 144), e -> agregarAula());
        agregarBoton("EDITAR", x, y + 2 * gap, w, h, new Color(173, 216, 230), e -> editarAula());
        agregarBoton("ELIMINAR", x, y + 3 * gap, w, h, new Color(255, 102, 102), e -> eliminarAula());
    }

    private void agregarBoton(String texto, int x, int y, int w, int h, Color color, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, w, h);
        boton.setBackground(color);
        boton.addActionListener(accion);
        panelContenido.add(boton);
    }

    private void agregarTablaAulas() {
        tablaAulas = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Grado", "Sección"}));
        tablaAulas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaAulas);
        scroll.setBounds(380, 80, 740, 380);
        panelContenido.add(scroll);

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
    }

    private void agregarBotonRegresar() {
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 500, 120, 35);
        btnRegresar.setBackground(new Color(39, 87, 117));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.addActionListener(e -> {
            new MenuAdm(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);
    }

    // Sección: Lógica de negocio
    private void cargarAulas() {
        DefaultTableModel model = (DefaultTableModel) tablaAulas.getModel();
        model.setRowCount(0);
        for (Aula a : aulaBD.listarAulas()) {
            model.addRow(new Object[]{a.getIdAula(), a.getGrado(), a.getSeccion()});
        }
    }

    private void verEstudiantes() {
        int fila = tablaAulas.getSelectedRow();
        if (fila != -1) {
            int idAula = (int) tablaAulas.getValueAt(fila, 0);
            Aula aula = aulaBD.obtenerAula(idAula);
            if (aula != null) {
                new VerEstudiantes(aula, usuario).setVisible(true);
                dispose();
            } else {
                mostrarMensaje("No se pudo encontrar el aula.");
            }
        } else {
            mostrarMensaje("Selecciona un aula primero.");
        }
    }

    private void agregarAula() {
        String gradoStr = txtGrado.getText();
        String seccion = txtSeccion.getText();
        if (verificarCampos(gradoStr, seccion)) {
            int grado = Integer.parseInt(gradoStr);
            Aula aula = new Aula(-1, grado, seccion);
            if (aulaBD.insertarAula(aula)) {
                mostrarMensaje("Aula agregada exitosamente.");
                limpiarCampos();
                cargarAulas();
            } else {
                mostrarMensaje("Error al agregar aula.");
            }
        }
    }

    private void editarAula() {
        int fila = tablaAulas.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona una fila.");
            return;
        }
        String gradoStr = txtGrado.getText();
        String seccion = txtSeccion.getText();
        if (verificarCampos(gradoStr, seccion)) {
            int grado = Integer.parseInt(gradoStr);
            int id = (int) tablaAulas.getValueAt(fila, 0);
            Aula aula = new Aula(id, grado, seccion);
            if (aulaBD.actualizarAula(aula)) {
                mostrarMensaje("Aula actualizada.");
                limpiarCampos();
                cargarAulas();
            } else {
                mostrarMensaje("Error al actualizar aula.");
            }
        }
    }

    private void eliminarAula() {
        int fila = tablaAulas.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona una fila.");
            return;
        }
        int id = (int) tablaAulas.getValueAt(fila, 0);
        if (aulaBD.eliminarAula(id)) {
            mostrarMensaje("Aula eliminada.");
            limpiarCampos();
            cargarAulas();
        } else {
            mostrarMensaje("Error al eliminar aula.");
        }
    }

    // Sección: Helpers
    private void limpiarCampos() {
        txtGrado.setText("");
        txtSeccion.setText("");
    }

    private boolean verificarCampos(String gradoStr, String seccion) {
        if (gradoStr.isEmpty() || seccion.isEmpty()) {
            mostrarMensaje("Por favor, complete todos los campos.");
            return false;
        }
        try {
            int grado = Integer.parseInt(gradoStr);
            if (grado <= 0 || grado > 6) {
                mostrarMensaje("El grado debe estar entre 1 y 6.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("El grado debe ser un número entero.");
            return false;
        }
        if (seccion.length() >= 3) {
            mostrarMensaje("La sección debe tener máximo 2 caracteres.");
            return false;
        }
        return true;
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
