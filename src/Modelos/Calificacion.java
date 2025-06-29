package Modelos;
public class Calificacion {
    private int idCalificacion;
    private Usuario estudiante;
    private Curso curso;
    private double nota;
    public Calificacion() {}
    public Calificacion(int idCalificacion, Usuario estudiante, Curso curso, double nota) {
        this.idCalificacion = idCalificacion;
        this.estudiante = estudiante;
        this.curso = curso;
        this.nota = nota;
    }
    public int getIdCalificacion() { return idCalificacion; }
    public void setIdCalificacion(int idCalificacion) {
        this.idCalificacion = idCalificacion; }
    
    public Usuario getEstudiante() { return estudiante; }
    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante; }
    
    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) {
        this.curso = curso; }

    public double getNota() { return nota; }
    public void setNota(double nota) { this.nota = nota; }
}