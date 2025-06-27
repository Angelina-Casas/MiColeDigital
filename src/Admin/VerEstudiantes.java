package Admin;

import Complementos.BaseFrame;
import Modelos.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VerEstudiantes extends BaseFrame {
    private JLabel lblAulaValor;
    private Usuario usuario;
    private JComboBox<Usuario> comboTutor;
    private JTable tablaEstudiantes;
    private JComboBox<Usuario> comboEstudiantes;
    private int idAulaActual;

    public VerEstudiantes(Aula aula,Usuario usuari) {
        super();
        this.usuario = usuario;
        initContenido();
        cargarDatos(aula);
    }

    @Override
    protected void initContenido() {
        JLabel lblAula = new JLabel("AULA");
        lblAula.setFont(new Font("Arial", Font.BOLD, 13));
        lblAula.setBounds(60, 40, 100, 30);
        panelContenido.add(lblAula);

        lblAulaValor = new JLabel();
        lblAulaValor.setBounds(150, 40, 250, 30);
        lblAulaValor.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelContenido.add(lblAulaValor);

        JLabel lblTutor = new JLabel("TUTOR:");
        lblTutor.setFont(new Font("Arial", Font.BOLD, 13));
        lblTutor.setBounds(60, 90, 180, 30);
        panelContenido.add(lblTutor);

        comboTutor = new JComboBox<>();
        comboTutor.setBounds(80, 120, 320, 30);
        panelContenido.add(comboTutor);

        comboEstudiantes = new JComboBox<>();
        comboEstudiantes.setBounds(80, 180, 320, 30);
        panelContenido.add(comboEstudiantes);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(80, 230, 100, 30);
        panelContenido.add(btnAgregar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(300, 230, 100, 30);
        panelContenido.add(btnEliminar);

        tablaEstudiantes = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Correo"}
        ));
        JScrollPane scroll = new JScrollPane(tablaEstudiantes);
        scroll.setBounds(450, 30, 700, 400);
        panelContenido.add(scroll);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 480, 120, 35);
        btnRegresar.addActionListener(e -> {
        new AdmAula(usuario).setVisible(true);
            dispose();});
        panelContenido.add(btnRegresar);

        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());
    }

    private void cargarDatos(Aula aula) {
        if (aula == null) return;
        this.idAulaActual = aula.getIdAula();
        lblAulaValor.setText(aula.getGrado() + "° " + aula.getSeccion());

        UsuarioBD usuarioBD = new UsuarioBD();

        comboTutor.removeAllItems();
        List<Usuario> docentes = usuarioBD.listarDocentes();
        for (Usuario d : docentes) comboTutor.addItem(d);

        Usuario docenteAsignado = usuarioBD.obtenerDocentePorAula(idAulaActual);
        if (docenteAsignado != null) comboTutor.setSelectedItem(docenteAsignado);

        comboEstudiantes.removeAllItems();
        List<Usuario> disponibles = usuarioBD.obtenerEstudiantesSinAula();
        for (Usuario est : disponibles) comboEstudiantes.addItem(est);

        DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
        model.setRowCount(0);
        List<Usuario> enAula = usuarioBD.obtenerEstudiantesPorAula(idAulaActual);
        for (Usuario est : enAula) {
            model.addRow(new Object[]{est.getIdUsuario(), est.getNombre(), est.getCorreo()});
        }
    }

    private void agregarEstudiante() {
        Usuario seleccionado = (Usuario) comboEstudiantes.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante.");
            return;
        }

        AulaBD aulaBD = new AulaBD();
        if (aulaBD.agregarEstudianteAlAula(seleccionado.getIdUsuario(), idAulaActual)) {
            DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
            model.addRow(new Object[]{seleccionado.getIdUsuario(), seleccionado.getNombre(), seleccionado.getCorreo()});
            JOptionPane.showMessageDialog(this, "Estudiante agregado al aula.");
            comboEstudiantes.removeItem(seleccionado); // eliminar del combo
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar en la base de datos.");
        }
    }

    private void eliminarEstudiante() {
        int fila = tablaEstudiantes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar estudiante del aula?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int idUsuario = (int) tablaEstudiantes.getValueAt(fila, 0);
        AulaBD aulaBD = new AulaBD();
        if (aulaBD.eliminarEstudianteDelAula(idUsuario, idAulaActual)) {
            DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
            String nombre = (String) model.getValueAt(fila, 1);
            String correo = (String) model.getValueAt(fila, 2);

            Usuario estudianteEliminado = new Usuario();
            estudianteEliminado.setIdUsuario(idUsuario);
            estudianteEliminado.setNombre(nombre);
            estudianteEliminado.setCorreo(correo);

            comboEstudiantes.addItem(estudianteEliminado);

            model.removeRow(fila);

            JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar de la base de datos.");
        }
    }
}