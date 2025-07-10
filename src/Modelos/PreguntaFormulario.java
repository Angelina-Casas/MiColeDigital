package Modelos;

public class PreguntaFormulario {
    private int nroPregunta;
    private String pregunta;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private String respuestaCorrecta;

    public PreguntaFormulario(int nroPregunta, String pregunta, String opcion1,
                               String opcion2, String opcion3, String opcion4,
                               String respuestaCorrecta) {
        this.nroPregunta = nroPregunta;
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getNroPregunta() {
        return nroPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    // Setters (opcionalmente puedes quitarlos si deseas solo lectura)
    public void setNroPregunta(int nroPregunta) {
        this.nroPregunta = nroPregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public String toString() {
        return "Pregunta " + nroPregunta + ": " + pregunta;
    }
}
