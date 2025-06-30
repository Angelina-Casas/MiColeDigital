package Modelos;

public class PreguntaFormulario {
    private int nroPregunta;
    private String pregunta;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private String respuestaCorrecta;

    public PreguntaFormulario(int nroPregunta, String pregunta, String opcion1, String opcion2, String opcion3, String opcion4, String respuestaCorrecta) {
        this.nroPregunta = nroPregunta;
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getNroPregunta() { return nroPregunta; }
    public String getPregunta() { return pregunta; }
    public String getOpcion1() { return opcion1; }
    public String getOpcion2() { return opcion2; }
    public String getOpcion3() { return opcion3; }
    public String getOpcion4() { return opcion4; }
    public String getRespuestaCorrecta() { return respuestaCorrecta; }
}
