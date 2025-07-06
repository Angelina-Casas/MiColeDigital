package Admin;

import Complementos.BaseFrame;
import Modelos.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdmUsuario extends BaseFrame {
    private final Usuario usuario;
    private final UsuarioBD usuarioBD = new UsuarioBD();
    private final RolBD rolBD = new RolBD();

    private JTextField txtNombre, txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<Rol> cbRol;
    private JTable tablaUsuarios;

    public AdmUsuario(Usuario usuario) {
        this.usuario = usuario;
        initContenido();
    }

    @Override
    protected void initContenido() {
        agregarLabelBienvenida();
        inicializarFormulario();
        inicializarBotones();
        inicializarTabla();
        cargarRoles();
        cargarUsuarios();
    }

    // ==== COMPONENTES UI ====

    private void agregarLabelBienvenida() {
        JLabel lblBienvenido = new JLabel("Bienvenido, " + usuario.getNombre());
        lblBienvenido.setFont(new Font("Serif", Font.BOLD, 16));
        lblBienvenido.setBounds(45, 20, 500, 25);
        panelContenido.add(lblBienvenido);
    }

    private void inicializarFormulario() {
        int x = 70, y = 80, hCampo = 30, wCampo = 220, sep = 60;

        txtNombre = agregarCampo("Nombre:", x, y, wCampo, hCampo);
        txtUsuario = agregarCampo("Usuario:", x, y + sep, wCampo, hCampo);
        txtContrasena = new JPasswordField();
        agregarLabel("Contraseña:", x, y + sep * 2);
        txtContrasena.setBounds(x, y + sep * 2 + 20, wCampo, hCampo);
        panelContenido.add(txtContrasena);

        agregarLabel("Rol:", x, y + sep * 3);
        cbRol = new JComboBox<>();
        cbRol.setBounds(x, y + sep * 3 + 20, wCampo, hCampo);
        panelContenido.add(cbRol);
    }

    private JTextField agregarCampo(String label, int x, int y, int w, int h) {
        agregarLabel(label, x, y);
        JTextField campo = new JTextField();
        campo.setBounds(x, y + 20, w, h);
        panelContenido.add(campo);
        return campo;
    }

    private void agregarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 100, 20);
        panelContenido.add(label);
    }

    private void inicializarBotones() {
        int x = 70, y = 350, w = 220, h = 35, gap = 45;

        agregarBoton("AGREGAR", x, y, w, h, new Color(144, 238, 144), e -> agregarUsuario());
        agregarBoton("EDITAR", x, y + gap, w, h, new Color(173, 216, 230), e -> editarUsuario());
        agregarBoton("ELIMINAR", x, y + 2 * gap, w, h, new Color(255, 102, 102), e -> eliminarUsuario());

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

    private void inicializarTabla() {
        tablaUsuarios = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Correo", "Contraseña", "Rol"}
        ));
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> mostrarDatosSeleccionados());

        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setBounds(380, 60, 740, 420);
        panelContenido.add(scroll);
    }

    // ==== CARGA DE DATOS ====

    private void cargarRoles() {
        cbRol.removeAllItems();
        for (Rol rol : rolBD.listarRoles()) {
            cbRol.addItem(rol);
        }
    }

    private void cargarUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tablaUsuarios.getModel();
        model.setRowCount(0);
        for (Usuario u : usuarioBD.listarUsuario()) {
            model.addRow(new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getPassword(),
                    u.getRol().getNombreRol()
            });
        }
    }

    // ==== ACCIONES ====

    private void agregarUsuario() {
        Usuario nuevo = obtenerUsuarioDesdeFormulario(-1);
        if (nuevo == null) return;

        if (usuarioBD.insertarUsuario(nuevo)) {
            mostrarMensaje("Usuario agregado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            mostrarMensaje("Error al agregar");
        }
    }

    private void editarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona un usuario");
            return;
        }

        int id = (int) tablaUsuarios.getValueAt(fila, 0);
        Usuario u = obtenerUsuarioDesdeFormulario(id);
        if (u == null) return;

        if (usuarioBD.actualizarUsuario(u)) {
            mostrarMensaje("Usuario actualizado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            mostrarMensaje("Error al actualizar");
        }
    }

    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Selecciona un usuario");
            return;
        }

        int id = (int) tablaUsuarios.getValueAt(fila, 0);
        if (usuarioBD.eliminarUsuario(id)) {
            mostrarMensaje("Usuario eliminado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            mostrarMensaje("Error al eliminar");
        }
    }

    private Usuario obtenerUsuarioDesdeFormulario(int id) {
        String nombre = txtNombre.getText();
        String correo = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        Rol rol = (Rol) cbRol.getSelectedItem();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {
            mostrarMensaje("Completa todos los campos");
            return null;
        }

        return new Usuario(id, nombre, correo, contrasena, rol);
    }

    private void mostrarDatosSeleccionados() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            txtNombre.setText((String) tablaUsuarios.getValueAt(fila, 1));
            txtUsuario.setText((String) tablaUsuarios.getValueAt(fila, 2));
            txtContrasena.setText((String) tablaUsuarios.getValueAt(fila, 3));

            String rolNombre = (String) tablaUsuarios.getValueAt(fila, 4);
            for (int i = 0; i < cbRol.getItemCount(); i++) {
                if (cbRol.getItemAt(i).getNombreRol().equals(rolNombre)) {
                    cbRol.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtUsuario.setText("");
        txtContrasena.setText("");
        cbRol.setSelectedIndex(-1);
        tablaUsuarios.clearSelection();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
