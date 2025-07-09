package Complementos;

import Estudiante.CursoEstudiante;
import Estudiante.PerfilEstudiante;
import Modelos.Usuario;

import javax.swing.*;

public class ComplementosFrameEstudiante extends FrameConMenu {
    protected JButton btnCursos, btnEstudiantes;

    public ComplementosFrameEstudiante(Usuario usuario) {
        super(usuario);
    }

    protected JPanel crearPanelIzquierdo() {
        JPanel panel = crearPanelIzquierdoBase();

        btnCursos = crearBotonMenu("/Img/btnCursos.png", "/Img/btnCursos_hover.png", 220, () -> {
            resaltarBoton(btnCursos);
            new CursoEstudiante(usuario).setVisible(true);
            dispose();
        });

        btnEstudiantes = crearBotonMenu("/Img/btnEstudiantes.png", "/Img/btnEstudiantes_hover.png", 270, () -> {
            resaltarBoton(btnEstudiantes);
            new PerfilEstudiante(usuario).setVisible(true);
            dispose();
        });

        panel.add(btnCursos);
        panel.add(btnEstudiantes);
        return panel;
    }
}
