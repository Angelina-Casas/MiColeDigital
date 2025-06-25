package Modelos;

import java.time.LocalDate;

public class Aula {
    private int idAula;
    private int grado;
    private String seccion;
    private LocalDate promocion;
    
    public Aula(){}
    public Aula(int idAula, int grado, String seccion, LocalDate promocion) {
        this.idAula = idAula;
        this.grado = grado;
        this.seccion = seccion;
        this.promocion = promocion;
    }
    public int getIdAula() { return idAula; }
    public void setIdAula(int idAula) { this.idAula = idAula; }
    
    public int getGrado() { return grado; }
    public void setGrado(int grado) { this.grado = grado; }
    
    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }
    
    public LocalDate getPromocion() { return promocion; }
    public void setPromocion(LocalDate promocion) 
    { this.promocion = promocion; }
    
    
    
    // Función para convertir el grado en texto
    public String getGradoString() {
        switch (grado) {
            case 1:
                return "Primero";
            case 2:
                return "Segundo";
            case 3:
                return "Tercero";
            case 4:
                return "Cuarto";
            case 5:
                return "Quinto";
            case 6:
                return "Sexto";
            default:
                return "Desconocido"; // Si el grado no es válido
        }
    }

    @Override
    public String toString() {
        return String.format(
            "%-10s | %-40s | %-40s | %-15s |",
            idAula, getGradoString(), seccion, promocion
        );
    }
    
    
    
    
}