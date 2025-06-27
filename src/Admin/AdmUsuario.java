package Admin;

import Complementos.BaseFrame;
import Modelos.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdmUsuario extends BaseFrame {
    private Usuario usuario;

    private JTextField txtNombre, txtUsuario;
    private JPasswordField txtContrasena;
    private JComboBox<Rol> cbRol;
    private JTable tablaUsuarios;
    private UsuarioBD usuarioBD;
    private RolBD rolBD;

    public AdmUsuario(Usuario usuario) {
        this.usuario= usuario;
        this.rolBD = new RolBD();
        this.usuarioBD = new UsuarioBD();
        initContenido();
    }

    @Override
    protected void initContenido() {
        int xForm = 40;

        JLabel lblNombre = new JLabel("Nombre");
        lblNombre.setBounds(xForm, 10, 100, 20);
        panelContenido.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(xForm, 30, 220, 30);
        panelContenido.add(txtNombre);

        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setBounds(xForm, 70, 100, 20);
        panelContenido.add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setBounds(xForm, 90, 220, 30);
        panelContenido.add(txtUsuario);

        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setBounds(xForm, 130, 100, 20);
        panelContenido.add(lblContrasena);

        txtContrasena = new JPasswordField();
        txtContrasena.setBounds(xForm, 150, 220, 30);
        panelContenido.add(txtContrasena);

        JLabel lblRol = new JLabel("Rol");
        lblRol.setBounds(xForm, 190, 100, 20);
        panelContenido.add(lblRol);

        cbRol = new JComboBox<>();
        cbRol.setBounds(xForm, 210, 220, 30);
        panelContenido.add(cbRol);
        cargarRoles();

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setBounds(40, 260, 140, 35);
        btnAgregar.setBackground(new Color(144, 238, 144));
        btnAgregar.addActionListener(e -> agregarUsuario());
        panelContenido.add(btnAgregar);

        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setBounds(40, 305, 140, 35);
        btnEditar.setBackground(new Color(255, 102, 102));
        btnEditar.addActionListener(e -> editarUsuario());
        panelContenido.add(btnEditar);

        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setBounds(40, 350, 140, 35);
        btnEliminar.setBackground(new Color(173, 216, 230));
        btnEliminar.addActionListener(e -> eliminarUsuario());
        panelContenido.add(btnEliminar);

        JButton btnRegresar = new JButton("REGRESAR");
        btnRegresar.setBounds(970, 510, 150, 40);
        btnRegresar.setBackground(new Color(255, 249, 200));
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 13));
        btnRegresar.addActionListener((var e) -> {
            new MenuAdm(usuario).setVisible(true);
            dispose();
        });
        panelContenido.add(btnRegresar);

        tablaUsuarios = new JTable(new DefaultTableModel(
            new Object[][] {},
            new String[] {"ID", "Nombre", "Correo", "Contraseña", "Rol"}
        ));
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> mostrarDatosSeleccionados());

        JScrollPane scrollLista = new JScrollPane(tablaUsuarios);
        scrollLista.setBounds(380, 40, 740, 420);
        panelContenido.add(scrollLista);

        cargarUsuarios();
    }

    private void cargarRoles() {
        System.out.println("ROLBD: " + rolBD);
        cbRol.removeAllItems();
        for (Rol rol : rolBD.listarRoles()) {
            cbRol.addItem(rol);
        }
    }

    private void cargarUsuarios() {
        DefaultTableModel model = (DefaultTableModel) tablaUsuarios.getModel();
        model.setRowCount(0);
        for (Usuario usuario : usuarioBD.listarUsuario()) {
            model.addRow(new Object[]{
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.getRol().getNombreRol()
            });
        }
    }

    private void agregarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        Rol rol = (Rol) cbRol.getSelectedItem();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || rol == null) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos");
            return;
        }

        Usuario u = new Usuario(-1, nombre, correo, contrasena, rol);
        if (usuarioBD.insertarUsuario(u)) {
            JOptionPane.showMessageDialog(this, "Usuario agregado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar");
        }
    }

    private void editarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
            return;
        }

        int id = (int) tablaUsuarios.getValueAt(fila, 0);
        String nombre = txtNombre.getText();
        String correo = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        Rol rol = (Rol) cbRol.getSelectedItem();

        Usuario u = new Usuario(id, nombre, correo, contrasena, rol);
        if (usuarioBD.actualizarUsuario(u)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar");
        }
    }

    private void eliminarUsuario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario");
            return;
        }

        int id = (int) tablaUsuarios.getValueAt(fila, 0);
        if (usuarioBD.eliminarUsuario(id)) {
            JOptionPane.showMessageDialog(this, "Usuario eliminado");
            limpiarCampos();
            cargarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Error al eliminar");
        }
    }

    private void mostrarDatosSeleccionados() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            txtNombre.setText((String) tablaUsuarios.getValueAt(fila, 1));
            txtUsuario.setText((String) tablaUsuarios.getValueAt(fila, 2));
            txtContrasena.setText((String) tablaUsuarios.getValueAt(fila, 3));

            String nombreRol = (String) tablaUsuarios.getValueAt(fila, 4);
            for (int i = 0; i < cbRol.getItemCount(); i++) {
                if (cbRol.getItemAt(i).getNombreRol().equals(nombreRol)) {
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
        tablaUsuarios.clearSelection();
    }
}
