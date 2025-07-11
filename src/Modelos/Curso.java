package Modelos;

public class Curso {
    private int idCurso;
    private String nombre;
    private Usuario docente;
    private Aula aula;

    private int grado;
    private String seccion;

    public Curso() {}

    public Curso(int idCurso, String nombre, Usuario docente, Aula aula) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.docente = docente;
        this.aula = aula;
    }

    // Getters y setters
    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getDocente() {
        return docente;
    }

    public void setDocente(Usuario docente) {
        this.docente = docente;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    @Override
    public String toString() {
        return nombre + " (" + grado + "° " + (seccion != null ? seccion : "") + ")";
    }
}
