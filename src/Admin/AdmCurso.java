package Admin;

import Complementos.BaseFrame;
import Modelos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdmCurso extends BaseFrame {
    private JTextField txtNombre;
    private JComboBox<Usuario> cbDocente;
    private JComboBox<Aula> cbAula;
    private JTable tablaCursos;

    private final CursoBD cursoBD = new CursoBD();
    private final UsuarioBD usuarioBD = new UsuarioBD();
    private final AulaBD aulaBD = new AulaBD();
    private final Usuario usuario;

    public AdmCurso(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        agregarLabelBienvenida();
        agregarCampos();
        agregarCombos();
        agregarBotones();
        agregarTabla();
        cargarDocentes();
        cargarAulas();
        cargarCursos();
    }

    // ==== COMPONENTES UI ====

    private void agregarLabelBienvenida() {
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombre());
        lblBienvenida.setFont(new Font("Serif", Font.BOLD, 16));
        lblBienvenida.setBounds(30, 20, 400, 25);
        panelContenido.add(lblBienvenida);
    }

    private void agregarCampos() {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(30, 70, 100, 25);
        panelContenido.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(30, 95, 300, 30);
        panelContenido.add(txtNombre);
    }

    private void agregarCombos() {
        JLabel lblDocente = new JLabel("Docente:");
        lblDocente.setFont(new Font("Arial", Font.BOLD, 14));
        lblDocente.setBounds(30, 140, 100, 25);
        panelContenido.add(lblDocente);

        cbDocente = new JComboBox<>();
        cbDocente.setBounds(30, 165, 300, 30);
        panelContenido.add(cbDocente);

        JLabel lblAula = new JLabel("Aula:");
        lblAula.setFont(new Font("Arial", Font.BOLD, 14));
        lblAula.setBounds(30, 210, 100, 25);
        panelContenido.add(lblAula);

        cbAula = new JComboBox<>();
        cbAula.setBounds(30, 235, 300, 30);
        panelContenido.add(cbAula);
    }

    private void agregarBotones() {
        int x = 110, y = 320, w = 140, h = 35, gap = 50;

        agregarBoton("AGREGAR", x, y, w, h, new Color(144, 238, 144), e -> agregarCurso());
        agregarBoton("EDITAR", x, y + gap, w, h, new Color(173, 216, 230), e -> editarCurso());
        agregarBoton("ELIMINAR", x, y + 2 * gap, w, h, new Color(255, 102, 102), e -> eliminarCurso());

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(970, 510, 150, 40);
        btnRegresar.setBackground(new Color(39, 87, 117));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegresar.addActionListener(e -> {
            new MenuAdm(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);
    }

    private void agregarBoton(String texto, int x, int y, int w, int h, Color color, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, w, h);
        boton.setBackground(color);
        boton.addActionListener(accion);
        panelContenido.add(boton);
    }

    private void agregarTabla() {
        tablaCursos = new JTable(new DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "Nombre", "Docente", "Aula"}
        ));
        tablaCursos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaCursos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tablaCursos.getSelectedRow();
                if (fila != -1) {
                    txtNombre.setText(tablaCursos.getValueAt(fila, 1).toString());
                    cbDocente.setSelectedItem(tablaCursos.getValueAt(fila, 2));
                    cbAula.setSelectedItem(tablaCursos.getValueAt(fila, 3));
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaCursos);
        scroll.setBounds(380, 60, 740, 400);
        panelContenido.add(scroll);
    }

    private void cargarCursos() {
        DefaultTableModel model = (DefaultTableModel) tablaCursos.getModel();
        model.setRowCount(0);
        for (Curso curso : cursoBD.listarCurso()) {
            model.addRow(new Object[]{
                    curso.getIdCurso(),
                    curso.getNombre(),
                    curso.getDocente(),
                    curso.getAula()
            });
        }
    }

    private void cargarDocentes() {
        cbDocente.removeAllItems();
        for (Usuario u : usuarioBD.listarDocentes()) {
            cbDocente.addItem(u);
        }
        cbDocente.setSelectedIndex(-1);
    }

    private void cargarAulas() {
        cbAula.removeAllItems();
        for (Aula a : aulaBD.listarAulas()) {
            cbAula.addItem(a);
        }
        cbAula.setSelectedIndex(-1);
    }

    private void agregarCurso() {
        String nombre = txtNombre.getText();
        Usuario docente = (Usuario) cbDocente.getSelectedItem();
        Aula aula = (Aula) cbAula.getSelectedItem();

        if (!verificarCampos(nombre, docente, aula)) return;

        Curso curso = new Curso(-1, nombre, docente, aula);
        if (cursoBD.agregarCurso(curso)) {
            mostrarMensaje("Curso agregado correctamente.");
            cargarCursos();
            limpiarCampos();
        } else {
            mostrarMensaje("Error al agregar curso.");
        }
    }
    
    private void editarCurso() {
        int fila = tablaCursos.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona un curso.");
            return;
        }

        int idCurso = (int) tablaCursos.getValueAt(fila, 0);
        String nombre = txtNombre.getText();
        Usuario docente = (Usuario) cbDocente.getSelectedItem();
        Aula aula = (Aula) cbAula.getSelectedItem();

        if (!verificarCampos(nombre, docente, aula)) return;

        Curso curso = new Curso(idCurso, nombre, docente, aula);
        if (cursoBD.actualizarCurso(curso)) {
            mostrarMensaje("Curso actualizado.");
            cargarCursos();
            limpiarCampos();
        } else {
            mostrarMensaje("Error al actualizar curso.");
        }
    }

    private void eliminarCurso() {
        int fila = tablaCursos.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona un curso.");
            return;
        }

        int idCurso = (int) tablaCursos.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el curso?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (cursoBD.eliminarCurso(idCurso)) {
                mostrarMensaje("Curso eliminado.");
                cargarCursos();
                limpiarCampos();
            } else {
                mostrarMensaje("Error al eliminar curso.");
            }
        }
    }

    private boolean verificarCampos(String nombre, Usuario docente, Aula aula) {
        if (nombre.isEmpty() || docente == null || aula == null) {
            mostrarMensaje("Completa todos los campos.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        cbDocente.setSelectedIndex(-1);
        cbAula.setSelectedIndex(-1);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
