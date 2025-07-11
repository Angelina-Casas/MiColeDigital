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
    private JComboBox<Usuario> comboEstudiantes;
    private JTable tablaEstudiantes;
    private int idAulaActual;

    private final UsuarioBD usuarioBD = new UsuarioBD();
    private final AulaBD aulaBD = new AulaBD();

    public VerEstudiantes(Aula aula, Usuario usuario) {
        this.usuario = usuario;
        initContenido();
        cargarDatos(aula);
    }

    @Override
    protected void initContenido() {
        agregarLabels();
        agregarCombos();
        agregarBotones();
        agregarTabla();
        agregarBotonRegresar();
    }

    private void agregarLabels() {
        JLabel lblBienvenida = crearLabel("Bienvenido, " + usuario.getNombre(), 30, 20, 400, 25, new Font("Serif", Font.BOLD, 16));
        JLabel lblAula = crearLabel("AULA", 60, 70, 100, 30, new Font("Arial", Font.BOLD, 13));
        lblAulaValor = new JLabel();
        lblAulaValor.setBounds(60, 100, 290, 30);
        lblAulaValor.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelContenido.add(lblBienvenida);
        panelContenido.add(lblAula);
        panelContenido.add(lblAulaValor);

        panelContenido.add(crearLabel("TUTOR:", 60, 140, 180, 30, new Font("Arial", Font.BOLD, 13)));
        panelContenido.add(crearLabel("ESTUDIANTES:", 60, 210, 180, 30, new Font("Arial", Font.BOLD, 13)));
    }

    private void agregarCombos() {
        comboTutor = new JComboBox<>();
        comboTutor.setBounds(60, 170, 290, 30);
        panelContenido.add(comboTutor);

        comboEstudiantes = new JComboBox<>();
        comboEstudiantes.setBounds(60, 240, 290, 30);
        panelContenido.add(comboEstudiantes);
    }

    private void agregarBotones() {
        int xBtn = 115, startY = 320, gap = 40, btnWidth = 160, btnHeight = 30;

        JButton btnAgregar = crearBoton("AGREGAR", xBtn, startY + gap, btnWidth, btnHeight, new Color(144, 238, 144));
        JButton btnEliminar = crearBoton("ELIMINAR", xBtn, startY + 2 * gap, btnWidth, btnHeight, new Color(255, 102, 102));

        btnAgregar.addActionListener(e -> agregarEstudiante());
        btnEliminar.addActionListener(e -> eliminarEstudiante());

        panelContenido.add(btnAgregar);
        panelContenido.add(btnEliminar);
    }

    private void agregarTabla() {
        tablaEstudiantes = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nombre", "Correo"}));
        JScrollPane scroll = new JScrollPane(tablaEstudiantes);
        scroll.setBounds(420, 70, 700, 380);
        panelContenido.add(scroll);
    }

    private void agregarBotonRegresar() {
        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(1000, 500, 120, 35);
        btnRegresar.setBackground(new Color(39, 87, 117));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.addActionListener(e -> {
            new AdmAula(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);
    }

    private void cargarDatos(Aula aula) {
        if (aula == null) return;

        idAulaActual = aula.getIdAula();
        lblAulaValor.setText(aula.getGrado() + "° " + aula.getSeccion());

        cargarDocentes();
        cargarEstudiantesSinAula();
        cargarEstudiantesEnAula();
    }

    private void cargarDocentes() {
        comboTutor.removeAllItems();
        for (Usuario d : usuarioBD.listarDocentes()) comboTutor.addItem(d);

        Usuario asignado = usuarioBD.obtenerDocentePorAula(idAulaActual);
        if (asignado != null) comboTutor.setSelectedItem(asignado);
    }

    private void cargarEstudiantesSinAula() {
        comboEstudiantes.removeAllItems();
        for (Usuario est : usuarioBD.obtenerEstudiantesSinAula()) comboEstudiantes.addItem(est);
    }

    private void cargarEstudiantesEnAula() {
        DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
        model.setRowCount(0);
        for (Usuario est : usuarioBD.obtenerEstudiantesPorAula(idAulaActual)) {
            model.addRow(new Object[]{est.getIdUsuario(), est.getNombre(), est.getCorreo()});
        }
    }

    private void agregarEstudiante() {
        Usuario seleccionado = (Usuario) comboEstudiantes.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un estudiante.");
            return;
        }

        if (aulaBD.agregarEstudianteAlAula(seleccionado.getIdUsuario(), idAulaActual)) {
            ((DefaultTableModel) tablaEstudiantes.getModel()).addRow(new Object[]{
                seleccionado.getIdUsuario(), seleccionado.getNombre(), seleccionado.getCorreo()
            });
            comboEstudiantes.removeItem(seleccionado);
            JOptionPane.showMessageDialog(this, "Estudiante agregado al aula.");
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

        if (aulaBD.eliminarEstudianteDelAula(idUsuario, idAulaActual)) {
            DefaultTableModel model = (DefaultTableModel) tablaEstudiantes.getModel();
            String nombre = (String) model.getValueAt(fila, 1);
            String correo = (String) model.getValueAt(fila, 2);

            Usuario eliminado = new Usuario();
            eliminado.setIdUsuario(idUsuario);
            eliminado.setNombre(nombre);
            eliminado.setCorreo(correo);

            comboEstudiantes.addItem(eliminado);
            model.removeRow(fila);

            JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar de la base de datos.");
        }
    }

    private JLabel crearLabel(String texto, int x, int y, int w, int h, Font fuente) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(fuente);
        lbl.setBounds(x, y, w, h);
        return lbl;
    }

    private JButton crearBoton(String texto, int x, int y, int w, int h, Color color) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, w, h);
        btn.setBackground(color);
        return btn;
    }
}