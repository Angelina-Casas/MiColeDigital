package Modelos;

public class Formulario {

    private int idFor;
    private String nombreFor;
    private String tema;
    private String videoUrl;
    private int idCurso;

    private int nroPregunta;
    private String pregunta;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private String respuestaCorrecta;

    public Formulario() {}

    public Formulario(String nombreFor, String tema, String videoUrl) {
        this.nombreFor = nombreFor;
        this.tema = tema;
        this.videoUrl = videoUrl;
    }

    public Formulario(String nombreFor, String tema, String videoUrl, int idCurso) {
        this(nombreFor, tema, videoUrl);
        this.idCurso = idCurso;
    }

    public Formulario(String nombreFor, String tema, String videoUrl,
                      int nroPregunta, String pregunta, String opcion1,
                      String opcion2, String opcion3, String opcion4,
                      String respuestaCorrecta) {
        this(nombreFor, tema, videoUrl);
        this.nroPregunta = nroPregunta;
        this.pregunta = pregunta;
        this.opcion1 = opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public int getIdFor() {
        return idFor;
    }

    public void setIdFor(int idFor) {
        this.idFor = idFor;
    }

    public String getNombreFor() {
        return nombreFor;
    }

    public void setNombreFor(String nombreFor) {
        this.nombreFor = nombreFor;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getNroPregunta() {
        return nroPregunta;
    }

    public void setNroPregunta(int nroPregunta) {
        this.nroPregunta = nroPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }
}
