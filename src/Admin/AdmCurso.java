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

    private CursoBD cursoBD = new CursoBD();
    private UsuarioBD usuarioBD = new UsuarioBD();
    private AulaBD aulaBD = new AulaBD();
    private Usuario usuario;
    
    public AdmCurso(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setBounds(40, 40, 100, 25);
        panelContenido.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(40, 65, 300, 30);
        panelContenido.add(txtNombre);

        JLabel lblDocente = new JLabel("Docente:");
        lblDocente.setFont(new Font("Arial", Font.BOLD, 14));
        lblDocente.setBounds(40, 110, 100, 25);
        panelContenido.add(lblDocente);

        cbDocente = new JComboBox<>();
        cbDocente.setBounds(40, 135, 300, 30);
        panelContenido.add(cbDocente);

        JLabel lblAula = new JLabel("Aula:");
        lblAula.setFont(new Font("Arial", Font.BOLD, 14));
        lblAula.setBounds(40, 180, 100, 25);
        panelContenido.add(lblAula);

        cbAula = new JComboBox<>();
        cbAula.setBounds(40, 205, 300, 30);
        panelContenido.add(cbAula);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(40, 260, 140, 35);
        btnAgregar.setBackground(new Color(144, 238, 144));
        btnAgregar.addActionListener(e -> agregarCurso());
        panelContenido.add(btnAgregar);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setBounds(40, 305, 140, 35);
        btnEditar.setBackground(new Color(255, 102, 102));
        btnEditar.addActionListener(e -> editarCurso());
        panelContenido.add(btnEditar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(40, 350, 140, 35);
        btnEliminar.setBackground(new Color(173, 216, 230));
        btnEliminar.addActionListener(e -> eliminarCurso());
        panelContenido.add(btnEliminar);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(970, 510, 150, 40);
        btnRegresar.setBackground(new Color(255, 249, 200));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegresar.addActionListener(e -> {
            new MenuAdm(usuario).setVisible(true);
            dispose();}
        );
        panelContenido.add(btnRegresar);

        tablaCursos = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Docente", "Aula"}
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

        JScrollPane scrollLista = new JScrollPane(tablaCursos);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        cargarDocente();
        cargarAula();
        cargarCursos();
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

    private void cargarDocente() {
        cbDocente.removeAllItems();
        for (Usuario usuario : usuarioBD.listarDocentes()) {
            cbDocente.addItem(usuario);
        }
    }

    private void cargarAula() {
        cbAula.removeAllItems();
        for (Aula aula : aulaBD.listarAulas()) {
            cbAula.addItem(aula);}
        cbAula.setSelectedIndex(-1);
    }

    private void agregarCurso() {
        String nombre = txtNombre.getText();
        Usuario docente = (Usuario) cbDocente.getSelectedItem();
        Aula aula = (Aula) cbAula.getSelectedItem();
        if (nombre.isEmpty() || docente == null || aula == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }
        Curso curso = new Curso(-1, nombre, docente, aula);
        if (cursoBD.agregarCurso(curso)) {
            JOptionPane.showMessageDialog(this, "Curso agregado correctamente.");
            cargarCursos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar curso.");
        }
    }

    private void editarCurso() {
        int fila = tablaCursos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso.");
            return;
        }
        int idCurso = (int) tablaCursos.getValueAt(fila, 0);
        String nombre = txtNombre.getText();
        Usuario docente = (Usuario) cbDocente.getSelectedItem();
        Aula aula = (Aula) cbAula.getSelectedItem();
        if (nombre.isEmpty() || docente == null || aula == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }
        Curso curso = new Curso(idCurso, nombre, docente, aula);
        if (cursoBD.actualizarCurso(curso)) {
            JOptionPane.showMessageDialog(this, "Curso actualizado.");
            cargarCursos();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar curso.");
        }
    }

    private void eliminarCurso() {
        int fila = tablaCursos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un curso.");
            return;
        }
        int idCurso = (int) tablaCursos.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar el curso?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (cursoBD.eliminarCurso(idCurso)) {
                JOptionPane.showMessageDialog(this, "Curso eliminado.");
                cargarCursos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar curso.");
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        cbDocente.setSelectedIndex(-1);
        cbAula.setSelectedIndex(-1);
    }
}
