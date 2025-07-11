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

            // Descomenta solo UNA de las siguientes líneas si quieres probar directamente una pantalla específica:

            // Usuario simulado para pruebas de estudiante
            // Usuario usuarioEst = new Usuario(1, "Ana López", "ana@gmail.com", new Rol("estudiante"));
            // new PerfilEstudiante(usuarioEst).setVisible(true);

            // Usuario simulado para pruebas de docente
            // Usuario usuarioDoc = new Usuario(2, "Carlos Ruiz", "carlos@gmail.com", new Rol("docente"));
            // new PerfilDocente(usuarioDoc).setVisible(true);

            // Usuario simulado para pruebas del contenido docente
            // new ContenidoDocente(usuarioDoc).setVisible(true);

            // Usuario simulado para pruebas de administrador
            // Usuario usuarioAdm = new Usuario(3, "Admin", "admin@colegio.com", new Rol("administrador"));
            // new MenuAdm(usuarioAdm).setVisible(true);

            // Punto de entrada principal
            new LoginGeneral().setVisible(true);
        });
    }
}
