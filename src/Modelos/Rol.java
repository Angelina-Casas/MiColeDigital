package Modelos;

public class Rol {
    private int idRol;
    private String nombreRol;

    public Rol (){}
    // Constructor
    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    // Getters y setters
    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    // Sobrescribir el método toString() para mostrar el nombre del rol
    @Override
    public String toString() {
        return nombreRol;  // Esto es lo que se mostrará en el JComboBox
    }
}
 