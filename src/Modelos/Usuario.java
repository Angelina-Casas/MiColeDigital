package Modelos;

public class Usuario{
    private int idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private Rol rol;
    
    public Usuario() {}
    public Usuario(int idUsuario, String nombre, String correo, String password, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }
    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return String.format(
        "%-10s | %-40s | %-40s | %-15s | %-13s",
        idUsuario, nombre, correo, password, rol.getNombreRol()
        );
    }
}
