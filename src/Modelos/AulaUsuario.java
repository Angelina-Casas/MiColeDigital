package Modelos;

public class AulaUsuario {
    private int id;
    private Usuario usuario;
    private Aula aula;

    public AulaUsuario() {}

    public AulaUsuario(int id, Usuario usuario, Aula aula) {
        this.id = id;
        this.usuario = usuario;
        this.aula = aula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public String toString() {
        return "AulaUsuario { " +
                "id=" + id +
                ", usuario=" + (usuario != null ? usuario.getNombre() : "null") +
                ", aula=" + (aula != null ? aula.toString() : "null") +
                " }";
    }
}
