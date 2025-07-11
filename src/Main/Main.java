package Main;

import Admin.MenuAdm;
import Estudiante.PerfilEstudiante;
import Docente.PerfilDocente;
import Docente.ContenidoDocente;
import Modelos.Usuario;
import Modelos.Rol;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            
            new LoginGeneral().setVisible(true);
        });
    }
}
