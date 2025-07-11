package Modelos;

/**
 * Representa un rol de usuario (por ejemplo: Docente, Estudiante).
 */
public class Rol {
    private int idRol;
    private String nombreRol;

    // Constructores
    public Rol() {}

    public Rol(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
    }

    // Getters
    public int getIdRol() {
        return idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    // Setters
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    @Override
    public String toString() {
        return nombreRol;  // Usado para representar el rol en combos o listas
    }
}
