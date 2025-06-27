package Modelos;

public class Aula {
    private int idAula;
    private int grado;
    private String seccion;

    public Aula() {}

    public Aula(int idAula, int grado, String seccion) {
        this.idAula = idAula;
        this.grado = grado;
        this.seccion = seccion;
    }

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
        this.idAula = idAula;
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

    public String getGradoString() {
        switch (grado) {
            case 1: return "Primero";
            case 2: return "Segundo";
            case 3: return "Tercero";
            case 4: return "Cuarto";
            case 5: return "Quinto";
            case 6: return "Sexto";
            default: return "Desconocido";
        }
    }
    @Override
    public String toString() {
        return grado + "° " + seccion;
    }
}