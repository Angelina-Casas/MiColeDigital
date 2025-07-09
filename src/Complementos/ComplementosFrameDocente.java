package Complementos;

import Docente.CursoDocente;
import Docente.PerfilDocente;
import Modelos.Usuario;

import javax.swing.*;

public class ComplementosFrameDocente extends FrameConMenu {
    protected JButton btnCursos, btnDocentes;

    public ComplementosFrameDocente(Usuario usuario) {
        super(usuario);
    }

    protected JPanel crearPanelIzquierdo() {
        JPanel panel = crearPanelIzquierdoBase();

        btnCursos = crearBotonMenu("/Img/btnCursos.png", "/Img/btnCursos_hover.png", 220, () -> {
            resaltarBoton(btnCursos);
            new CursoDocente(usuario).setVisible(true);
            dispose();
        });

        btnDocentes = crearBotonMenu("/Img/btnDocentes.png", "/Img/btnDocentes_hover.png", 270, () -> {
            resaltarBoton(btnDocentes);
            new PerfilDocente(usuario).setVisible(true);
            dispose();
        });

        panel.add(btnCursos);
        panel.add(btnDocentes);
        return panel;
    }
}
